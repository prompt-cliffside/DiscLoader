package io.discloader.discloader.entity.channels;

import java.util.concurrent.CompletableFuture;

import io.discloader.discloader.entity.Guild;
import io.discloader.discloader.entity.impl.IGuildChannel;
import io.discloader.discloader.entity.impl.IVoiceChannel;
import io.discloader.discloader.entity.voice.VoiceConnection;
import io.discloader.discloader.network.json.ChannelJSON;
import io.discloader.discloader.util.Constants.ChannelType;

/**
 * @author Perry Berman
 *
 */
public class VoiceChannel extends GuildChannel implements IGuildChannel, IVoiceChannel {

	public int bitrate;

	public int userLimit;

	/**
	 * @param guild
	 * @param data
	 */
	public VoiceChannel(Guild guild, ChannelJSON data) {
		super(guild, data);

		this.type = ChannelType.VOICE;
		
		this.name = data.name;
	}

	public void setup(ChannelJSON data) {
		super.setup(data);

		this.bitrate = data.bitrate;

		this.userLimit = data.user_limit;
	}

	@Override
	public CompletableFuture<VoiceConnection> join() {
		CompletableFuture<VoiceConnection> future = new CompletableFuture<VoiceConnection>();
		VoiceConnection connection = new VoiceConnection(this, future);
		this.loader.voiceConnections.put(this.guild.id, connection);
		return future;
	}

	@Override
	public CompletableFuture<VoiceConnection> leave() {
		return null;
	}

	@Override
	public boolean isPrivate() {
		return false;
	}

	@Override
	public CompletableFuture<VoiceChannel> setName(String name) {
		return null;
	}

	@Override
	public CompletableFuture<IGuildChannel> setPosition(int position) {
		return null;
	}

	@Override
	public CompletableFuture<IGuildChannel> setPermissions(int allow, int deny, String type) {
		return null;
	}
}