package io.discloader.discloader.common.structures.channels;

import java.util.HashMap;

import io.discloader.discloader.common.structures.Guild;
import io.discloader.discloader.common.structures.GuildMember;
import io.discloader.discloader.common.structures.Overwrite;
import io.discloader.discloader.common.structures.Permission;
import io.discloader.discloader.common.structures.Role;
import io.discloader.discloader.network.gateway.json.ChannelJSON;
import io.discloader.discloader.network.gateway.json.OverwriteJSON;
import io.discloader.discloader.util.Constant;
import io.discloader.discloader.util.Constant.Permissions;

@Deprecated
public class GuildChannel extends Channel {
	public final Guild guild;

	public Permissions permission;

	public int position;
	public HashMap<String, Overwrite> overwrites;

	public GuildChannel(Guild guild, ChannelJSON channel) {
		super(guild.loader, channel);

		this.guild = guild;

		this.overwrites = new HashMap<String, Overwrite>();
	}

	public void setup(ChannelJSON data) {
		super.setup(data);

		this.name = data.name;

		this.position = data.position;

		if (data.permission_overwrites != null) {
			for (OverwriteJSON overwrite : data.permission_overwrites) {
				// this.overwrites.put(overwrite.id, new Overwrite(overwrite));
			}
		}
	}

	public HashMap<String, GuildMember> getMembers() {
		HashMap<String, GuildMember> members = new HashMap<String, GuildMember>();
		for (GuildMember member : this.guild.members.values()) {
			if (this.permissionsFor(member).hasPermission(Constant.PermissionFlags.READ_MESSAGES, false))
				members.put(member.id, member);
		}
		return members;
	}

	public Permission permissionsFor(GuildMember member) {
		int raw = 0;
		if (member.id == this.guild.ownerID)
			return new Permission(member, this, 2146958463);
		for (Role role : member.getRoleList().values())
			raw |= role.permissions;
		for (Overwrite overwrite : this.overwritesOf(member).values()) {
			raw |= overwrite.allow;
			raw &= ~overwrite.deny;
		}
		return new Permission(member, this, raw);
	}

	public HashMap<String, Overwrite> overwritesOf(GuildMember member) {
		HashMap<String, Overwrite> Overwrites = new HashMap<String, Overwrite>();
		for (Role role : member.getRoleList().values()) {
			if (this.overwrites.get(role.id) != null)
				Overwrites.put(role.id, this.overwrites.get(role.id));
		}
		if (this.overwrites.get(member.id) != null)
			Overwrites.put(member.id, this.overwrites.get(member.id));
		return Overwrites;
	}

}
