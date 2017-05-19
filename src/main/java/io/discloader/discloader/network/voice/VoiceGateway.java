package io.discloader.discloader.network.voice;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import io.discloader.discloader.client.logger.DLLogger;
import io.discloader.discloader.common.event.voice.VoiceConnectionReadyEvent;
import io.discloader.discloader.entity.util.SnowflakeUtil;
import io.discloader.discloader.entity.voice.VoiceConnect;
import io.discloader.discloader.network.gateway.packets.SocketPacket;
import io.discloader.discloader.network.voice.payloads.SessionDescription;
import io.discloader.discloader.network.voice.payloads.VoiceData;
import io.discloader.discloader.network.voice.payloads.VoiceIdentify;
import io.discloader.discloader.network.voice.payloads.VoicePacket;
import io.discloader.discloader.network.voice.payloads.VoiceReady;
import io.discloader.discloader.network.voice.payloads.VoiceUDPBegin;

public class VoiceGateway extends WebSocketAdapter {

	// VoiceConnection OP codes
	public static final int IDENTIFY = 0;
	public static final int SELECT_PROTOCOL = 1;
	public static final int READY = 2;
	public static final int HEARTBEAT = 3;
	public static final int SESSION_DESCRIPTION = 4;
	public static final int SPEAKING = 5;
	public static final int HELLO = 8;
	public String sessionID;
	private int sequence = 0;
	private int[] secretKey;

	private WebSocket ws;

	private VoiceConnect connection;

	private Gson gson;

	private Thread heartbeatThread = null;

	private final Logger logger;

	public VoiceGateway(VoiceConnect connection) {
		this.connection = connection;
		logger = new DLLogger("VoiceGateway" + (connection.getGuild() == null ? " - Channel: " + connection.getChannel().getID() : " - Guild: " + connection.getGuild().getID())).getLogger();
		gson = new Gson();
	}

	public void connect(String gateway, String token) throws IOException, WebSocketException {
		gateway = "wss://" + gateway;
		WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(15000);
		ws = factory.createSocket(gateway).addListener(this);
		ws.connect();
	}

	public void onConnected(WebSocket ws, Map<String, List<String>> arg1) throws Exception {
		this.sendIdentify();
	}

	public void onDisconnected(WebSocket ws, WebSocketFrame frame, WebSocketFrame frame2, boolean isServer) throws Exception {
		logger.severe(String.format("Reason: %s, Code: %d, isServer: %b", frame.getCloseReason(), frame.getCloseCode(), isServer));
		// this.connection.disconnected(frame.getCloseReason());
	}

	public void onTextMessage(WebSocket ws, String text) throws Exception {
		SocketPacket packet = gson.fromJson(text, SocketPacket.class);
		if (packet.op == HELLO) {
			System.out.println("HELLO?!?!?!?");
			// HelloJSON hello = gson.fromJson(gson.toJson(packet.d),
			// HelloJSON.class);
		}

		// if (packet.op == HEARTBEAT) {
		// VoicePacket vpacket = new VoicePacket(HEARTBEAT, sequence);
		// // System.out.println(gson.toJson(vpacket));
		// send(gson.toJson(vpacket));
		// return;
		// }
		logger.info(text);
		setSequence(packet.s);
		switch (packet.op) {
		case READY:
			VoiceReady data = gson.fromJson(gson.toJson(packet.d), VoiceReady.class);
			connection.setSSRC(data.ssrc);
			connection.connectUDP(data);
			break;
		case SESSION_DESCRIPTION:
			SessionDescription data2 = gson.fromJson(gson.toJson(packet.d), SessionDescription.class);
			secretKey = data2.secret_key;
			connection.getSendHandler().setSecretKey(secretKey);
			connection.fireEvent(new VoiceConnectionReadyEvent(connection));
			break;
		case SPEAKING:
			break;
		}

	}

	public void send(String payload) {
		System.out.println(payload);
		ws.sendText(payload);
	}

	public void sendIdentify() {
		// System.out.println(connection.getToken());
		VoiceIdentify payload = new VoiceIdentify(SnowflakeUtil.asString(connection.getGuild()), SnowflakeUtil.asString(connection.getLoader().user), sessionID, connection.getToken());
		VoicePacket packet = new VoicePacket(IDENTIFY, payload);
		send(gson.toJson(packet));
	}

	public void selectProtocol(String ip, int port) {
		String payload = gson.toJson(new VoicePacket(SELECT_PROTOCOL, new VoiceUDPBegin(new VoiceData(ip, port))));
		send(payload);
	}

	public void setSequence(int s) {
		if (s > sequence) sequence = s;
	}

	public void setSpeaking(boolean isSpeaking) {
		send(gson.toJson(new VoicePacket(SPEAKING, new Speaking(isSpeaking, 0))));
	}

	public void startHeartbeat(long interval) {
		// System.out.println(interval);
		// System.out.println(connection.getSSRC());
		if (heartbeatThread != null) return;
		heartbeatThread = new Thread(logger.getName()) {

			@Override
			public void run() {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
				}

				while (ws.isOpen() && !connection.getUDPClient().udpSocket.isClosed()) {
					VoicePacket packet = new VoicePacket(HEARTBEAT, sequence);
					send(gson.toJson(packet));
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
					}
				}
				logger.info("Ended: ws.isOpen(): " + ws.isOpen() + ", udp.isClosed(): " + connection.getUDPClient().udpSocket.isClosed());
			}
		};
		heartbeatThread.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
		heartbeatThread.setDaemon(true);
		heartbeatThread.start();
	}

	public CompletableFuture<VoiceConnect> disconnect() {
		return new CompletableFuture<>();
	}

}
