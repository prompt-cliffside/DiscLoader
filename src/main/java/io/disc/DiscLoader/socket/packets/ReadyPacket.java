package io.disc.DiscLoader.socket.packets;

import com.google.gson.Gson;

import io.disc.DiscLoader.objects.gateway.GuildGateway;
import io.disc.DiscLoader.objects.gateway.Ready;
import io.disc.DiscLoader.objects.structures.User;
import io.disc.DiscLoader.socket.DiscSocket;

public class ReadyPacket extends DiscPacket {
	public ReadyPacket(DiscSocket socket) {
		super(socket);
	}

	public void handle(SocketPacket packet) {
		Gson gson = new Gson();
		packet.d = gson.fromJson(gson.toJson(packet.d), Ready.class);
		this.socket.loader.user = new User(((Ready)packet.d).user);
		GuildGateway[] guilds = ((Ready)packet.d).guilds;
		for (int i = 0; i < guilds.length; i++) {
			this.socket.loader.addGuild(guilds[i]);
		}
		
		this.socket.sendHeartbeat(false);
	}
}