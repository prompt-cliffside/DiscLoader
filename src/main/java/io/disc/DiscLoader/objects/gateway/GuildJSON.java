package io.disc.DiscLoader.objects.gateway;

import java.util.List;

/**
 * @author perryberman
 *
 */
public class GuildJSON {
	public String id;
	public String name;
	public String icon;
	public String splash;
	public String owner_id;
	public String embed_channel_id;
	public List<String> features;
	
	public int verification_level;
	public int default_message_notifications;
	
	public boolean large;
	public boolean embed_enabled;
	public boolean unavailable;
	
	public List<MemberJSON> members;
	public List<ChannelJSON> channels;
	public List<RoleJSON> roles;
	public List<PresenceJSON> presences;
	
}