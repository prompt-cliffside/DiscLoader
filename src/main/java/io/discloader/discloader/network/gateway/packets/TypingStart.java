/**
 * 
 */
package io.discloader.discloader.network.gateway.packets;

import io.discloader.discloader.common.registry.EntityRegistry;
import io.discloader.discloader.entity.channel.ITextChannel;
import io.discloader.discloader.entity.user.IUser;
import io.discloader.discloader.network.gateway.Gateway;
import io.discloader.discloader.network.json.TypingStartJSON;

/**
 * @author Perry Berman
 */
public class TypingStart extends AbstractHandler {

	public TypingStart(Gateway socket) {
		super(socket);
	}

	@Override
	public void handle(SocketPacket packet) {
		String d = gson.toJson(packet.d);
		TypingStartJSON data = gson.fromJson(d, TypingStartJSON.class);
		ITextChannel channel = EntityRegistry.getTextChannelByID(data.channel_id);
		if (channel == null) channel = EntityRegistry.getPrivateChannelByID(data.channel_id);
		if (channel == null) return;
		IUser user = EntityRegistry.getUserByID(data.user_id);
		if (user == null || channel.getTyping() == null) return;

		channel.getTyping().put(user.getID(), user);

		// loader.emit("");
	}
}
