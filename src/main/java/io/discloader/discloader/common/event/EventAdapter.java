package io.discloader.discloader.common.event;

import java.util.HashMap;

import io.discloader.discloader.common.DiscLoader;
import io.discloader.discloader.entity.GuildMember;
import io.discloader.discloader.entity.User;

/**
 * Default Implementation of the {@link IEventAdapter}
 * 
 * @author perryberman
 * @see IEventAdapter
 */
public abstract class EventAdapter implements IEventAdapter {

	@Override
	public void raw(String raw) {
	}

	@Override
	public void PreInit(DLPreInitEvent preInitEvent) {
	}

	@Override
	public void PhaseChange() {

	}

	@Override
	public void Ready(DiscLoader loader) {
	}

	@Override
	public void GuildCreate(GuildCreateEvent e) {
	}

	@Override
	public void GuildDelete(GuildDeleteEvent e) {
	}

	@Override
	public void GuildUpdate(GuildUpdateEvent e) {
	}

	@Override
	public void GuildMembersChunk(HashMap<String, GuildMember> members) {
	}

	@Override
	public void GuildMemberAdd(GuildMember member) {
	}

	@Override
	public void GuildMemberRemove(GuildMember member) {
	}

	@Override
	public void GuildMemberUpdate(GuildMemberUpdateEvent e) {
	}

	@Override
	public void GuildMemberAvailable(GuildMember member) {
	}

	@Override
	public void GuildBanAdd(GuildMember member) {
	}

	@Override
	public void GuildBanRemove(User user) {
	}

	@Override
	public void GuildRoleCreate(GuildRoleCreateEvent e) {
	}

	@Override
	public void GuildRoleDelete(GuildRoleDeleteEvent e) {
	}

	@Override
	public void GuildRoleUpdate(GuildRoleUpdateEvent e) {
	}

	@Override
	public void GuildEmojisUpdate() {
	}

	@Override
	public void ChannelCreate(ChannelCreateEvent e) {
	}

	@Override
	public void ChannelDelete(ChannelDeleteEvent e) {
	}

	@Override
	public void ChannelUpdate(ChannelUpdateEvent e) {
	}

	@Override
	public void ChannelPinsUpdate() {
	}

	@Override
	public void MessageCreate(MessageCreateEvent e) {
	}

	@Override
	public void MessageDelete(MessageDeleteEvent e) {
	}

	@Override
	public void MessageUpdate(MessageUpdateEvent e) {
	}

	@Override
	public void PrivateMessageCreate(MessageCreateEvent e) {
	}

	@Override
	public void PrivateMessageDelete(MessageDeleteEvent e) {
	}

	@Override
	public void PrivateMessageUpdate(MessageUpdateEvent e) {
	}

	@Override
	public void UserUpdate(UserUpdateEvent e) {
	}

	@Override
	public void PresenceUpdate() {
	}

}
