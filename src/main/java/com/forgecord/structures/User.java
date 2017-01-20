package main.java.com.forgecord.structures;

import org.json.JSONObject;

import main.java.com.forgecord.client.Client;
import main.java.com.forgecord.util.constants;

public class User {

	public String id;
	public String username;
	public String discriminator;
	public String avatar;
	public boolean bot;
	public int lastMessageID;
	public Client client;

	/**
	 * Represents a user on the API
	 * @param client The client that cached the user
	 * @param data
	 */
	public User(Client client, JSONObject data) {
		this.client = client;
		this.setup(data);
	}

	/**
	 * Sets up the user's information
	 * @param data The user's data
	 */
	public void setup(JSONObject data) {
		this.id = data.getString("id");

		this.username = data.getString("username");

		this.discriminator = data.getString("discriminator");

		this.avatar = data.getString("avatar");

		this.bot = data.getBoolean("bot");
	}

	/**
	 * Gets the link to the user's avatar.
	 * @returns 
	 */
	public String avatarURL() {
		return constants.Endpoints.avatar(this.id, this.avatar);
	}
}
