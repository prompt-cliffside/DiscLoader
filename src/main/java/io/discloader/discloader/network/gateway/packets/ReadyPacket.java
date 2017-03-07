package io.discloader.discloader.network.gateway.packets;

import com.google.gson.Gson;

import io.discloader.discloader.entity.DLUser;
import io.discloader.discloader.network.gateway.DiscSocket;
import io.discloader.discloader.network.json.ChannelJSON;
import io.discloader.discloader.network.json.GuildJSON;
import io.discloader.discloader.network.json.Ready;

public class ReadyPacket extends DLPacket {
    public ReadyPacket(DiscSocket socket) {
        super(socket);
    }

    @Override
    public void handle(SocketPacket packet) {
        Gson gson = new Gson();
        String d = gson.toJson(packet.d);
        Ready ready = gson.fromJson(d, Ready.class);

        // set session id first just incase some screws up
        this.socket.sessionID = ready.session_id;

        // send first heartbeat in response to ready packet
        // this.socket.sendHeartbeat(false);

        // setup the Loaders user object
        this.socket.loader.user = new DLUser(this.loader.addUser(ready.user));
        if (this.socket.loader.user.bot == true) {
            this.socket.loader.token = "Bot " + this.socket.loader.token;
        }

        GuildJSON[] guilds = ready.guilds;
        for (GuildJSON guild : ready.guilds) {
            this.socket.loader.addGuild(guild);
        }

        for (ChannelJSON data : ready.private_channels) {
            this.socket.loader.addChannel(data);
        }

        // check if the loader is ready to rock & roll
        this.socket.loader.checkReady();
    }
}
