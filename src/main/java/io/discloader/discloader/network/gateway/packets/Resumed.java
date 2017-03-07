package io.discloader.discloader.network.gateway.packets;

import io.discloader.discloader.network.gateway.DiscSocket;

/**
 * @author Perry Berman
 *
 */
public class Resumed extends DLPacket {

    public Resumed(DiscSocket socket) {
        super(socket);
    }

    public void handle() {
        loader.checkReady();
    }

}
