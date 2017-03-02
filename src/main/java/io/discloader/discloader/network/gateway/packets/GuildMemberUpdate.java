package io.discloader.discloader.network.gateway.packets;

import io.discloader.discloader.common.DiscLoader;
import io.discloader.discloader.common.event.GuildMemberUpdateEvent;
import io.discloader.discloader.common.event.IEventListener;
import io.discloader.discloader.entity.Guild;
import io.discloader.discloader.entity.GuildMember;
import io.discloader.discloader.network.gateway.DiscSocket;
import io.discloader.discloader.network.json.MemberJSON;
import io.discloader.discloader.util.Constants.Status;

/**
 * @author Perry Berman
 *
 */
public class GuildMemberUpdate extends DLPacket {

	public GuildMemberUpdate(DiscSocket socket) {
		super(socket);
	}

	public void handle(SocketPacket packet) {
		String d = this.gson.toJson(packet);
		MemberJSON data = this.gson.fromJson(d, MemberJSON.class);
		Guild guild = this.loader.guilds.get(data.guild_id);
		GuildMember member = guild.members.get(data.user.id);
		if (member == null) {
			member = guild.addMember(data);
		}
		GuildMember oldMember = new GuildMember(member);
		if (this.loader.ready && this.socket.status == Status.READY) {
			GuildMemberUpdateEvent event = new GuildMemberUpdateEvent(member, oldMember, guild);
			for (IEventListener e : DiscLoader.handlers.values()) {
				e.GuildMemberUpdate(event);
			}
		}
	}
}