/**
 * 
 */
package io.discloader.discloader.network.gateway.packets;

import io.discloader.discloader.entity.voice.VoiceConnection;
import io.discloader.discloader.network.gateway.DiscSocket;
import io.discloader.discloader.network.json.VoiceServerUpdateJSON;

/**
 * @author Perry Berman
 *
 */
public class VoiceServerUpdate extends DLPacket {

	/**
	 * @param socket
	 */
	public VoiceServerUpdate(DiscSocket socket) {
		super(socket);
	}

	public void handle(SocketPacket packet) {
		String d = this.gson.toJson(packet.d);
		VoiceServerUpdateJSON data = this.gson.fromJson(d, VoiceServerUpdateJSON.class);
		VoiceConnection connection = this.loader.voiceConnections.get(data.guild_id);
//		System.out.println(data.endpoint);
		connection.endpointReceived(data.endpoint, data.token);
	}
	
}
