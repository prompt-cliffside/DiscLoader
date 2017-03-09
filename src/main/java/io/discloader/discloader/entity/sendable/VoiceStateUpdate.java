package io.discloader.discloader.entity.sendable;

import io.discloader.discloader.entity.guild.Guild;
import io.discloader.discloader.entity.impl.IVoiceChannel;

/**
 * @author Perry Berman
 *
 */
public class VoiceStateUpdate {
	public String guild_id;
	public String channel_id;

	public boolean self_mute;
	public boolean self_deaf;

	public VoiceStateUpdate(Guild guild, IVoiceChannel channel, boolean mute, boolean deaf) {
		this.guild_id = guild.id;
		this.channel_id = channel != null ? channel.getID() : null;
	}

}
