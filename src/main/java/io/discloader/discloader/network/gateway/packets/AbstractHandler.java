package io.discloader.discloader.network.gateway.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.discloader.discloader.common.DiscLoader;
import io.discloader.discloader.network.gateway.Gateway;
import io.discloader.discloader.util.DLUtil.Status;

/**
 * @author Perry Berman
 */
public abstract class AbstractHandler {

	public Gateway socket;
	public DiscLoader loader;
	public Gson gson;

	public AbstractHandler(Gateway socket) {
		this.socket = socket;
		this.loader = this.socket.loader;
		this.gson = new GsonBuilder().serializeNulls().create();
	}

	public void handle(SocketPacket packet) {

	}

	public boolean shouldEmit() {
		return loader.ready && socket.status == Status.READY;
	}

}
