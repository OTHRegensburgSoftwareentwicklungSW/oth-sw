package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.GroupVisibility;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.GroupChatRepository;
import de.othr.bib48218.chat.repository.PeerChatRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService implements IFChatService {

    @Autowired
    private GroupChatRepository groupRepository;

    @Autowired
    private PeerChatRepository peerRepository;

    @Override
    @Transactional
    public Optional<? extends Chat> getChatById(Long id) {
        return getGroupChatById(id).map(gc -> (Chat) gc).or(() -> getPeerChatById(id));
    }

    @Override
    @Transactional
    public Optional<GroupChat> getGroupChatById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<PeerChat> getPeerChatById(Long id) {
        return peerRepository.findById(id);
    }

    @Override
    @Transactional
    public Collection<Chat> getChatsByUser(User user) {
        return Stream.concat(
            groupRepository.findByMembershipsUser(user).stream(),
            peerRepository.findByMembershipsUser(user).stream()
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<Chat> getAllChats() {
        return Stream.concat(
            getAllPeerChats().stream(),
            getAllGroupChats().stream()
        ).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<GroupChat> getAllGroupChats() {
        return StreamSupport.stream(groupRepository.findAll().spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Collection<GroupChat> getAllPublicGroupChats() {
        return groupRepository.findByVisibilityIs(GroupVisibility.PUBLIC);
    }

    @Override
    @Transactional
    public Collection<PeerChat> getAllPeerChats() {
        return StreamSupport.stream(peerRepository.findAll().spliterator(), false)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addUserToChat(User user, Chat chat) {
        addOrUpdateChatMembership(user, chat, ChatMemberStatus.MEMBER);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addOrUpdateChatMembership(User user, Chat chat, ChatMemberStatus status) {
        chat.getMemberships().stream()
            .filter(m -> m.getUser().equals(user) && m.getChat().equals(chat))
            .findAny()
            .ifPresentOrElse(
                m -> m.setStatus(status),
                () -> chat.getMemberships().add(new ChatMembership(chat, status, user))
            );
    }

    @Override
    @Transactional
    public GroupChat createGroupChat(User creator, GroupVisibility visibility) {
        return saveChat(creator, new GroupChat(visibility));
    }

    @Override
    @Transactional
    public GroupChat saveChat(User creator, GroupChat chat) {
        chat.setMemberships(
            Set.of(new ChatMembership(chat, ChatMemberStatus.ADMINISTRATOR, creator))
        );
        return groupRepository.save(chat);
    }

    @Override
    @Transactional
    public PeerChat saveChat(User creator, PeerChat chat) {
        chat.setMemberships(
            Set.of(new ChatMembership(chat, ChatMemberStatus.ADMINISTRATOR, creator))
        );
        return peerRepository.save(chat);
    }

    @Override
    @Transactional
    public PeerChat getOrCreatePeerChatOf(User user, User otherUser) {
        Collection<PeerChat> chatsOfUser = peerRepository.findByMembershipsUser(user);
        Collection<PeerChat> chatsOfOtherUser = peerRepository.findByMembershipsUser(otherUser);

        return chatsOfUser.stream().distinct()
            .filter(chatsOfOtherUser::contains).findAny()
            .orElseGet(() -> {
                PeerChat peerChat = new PeerChat();
                peerChat = peerRepository.save(peerChat);
                addUserToChat(user, peerChat);
                addUserToChat(otherUser, peerChat);

                return peerChat;
            });
    }

    @Override
    @Transactional
    public void deleteChat(@NonNull Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
        } else if (peerRepository.existsById(id)) {
            peerRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void deleteChat(@NonNull Chat chat) {
        if (chat instanceof PeerChat) {
            peerRepository.delete((PeerChat) chat);
        } else if (chat instanceof GroupChat) {
            groupRepository.delete((GroupChat) chat);
        }
    }

    @Override
    @Transactional
    public Optional<ChatMemberStatus> getChatMembership(Chat chat, User user) {
        return chat.getMemberships().stream()
            .filter(m -> m.getUser().equals(user))
            .map(ChatMembership::getStatus)
            .findFirst();
    }

    @Override
    @Transactional
    public boolean isUserMember(User user, Chat chat) {
        return chat.getMemberships().stream()
            .anyMatch(m -> m.getUser() == user);
    }

    @Override
    @Transactional
    public void deleteChatMembership(User user, Long chatId) {
        getChatById(chatId)
            .ifPresent(chat -> chat.getMemberships().removeIf(m -> m.getUser().equals(user)));
    }

    @Override
    @Transactional
    public void editGroupChat(Long id, GroupChat chat) {
        groupRepository.findById(id).ifPresent(c -> {
            c.setVisibility(chat.getVisibility());
            c.getProfile().setDescription(chat.getProfile().getDescription());
            c.getProfile().setName(chat.getProfile().getName());
            c.getProfile().setImagePath(chat.getProfile().getImagePath());
        });
    }
}
