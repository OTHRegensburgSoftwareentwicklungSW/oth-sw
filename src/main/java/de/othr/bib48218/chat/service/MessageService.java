package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.repository.MessageRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class MessageService implements IFMessageService {
    @Autowired
    private MessageRepository repository;

    @Autowired
    private PersonRepository userRepository;

    @Override
    public Collection<Message> getAllMessagesByChat(Chat chat) {
        return repository.findByChat(chat);
    }

    @Override
    public Collection<Message> getMessagesByChatFrom(Chat chat, LocalDateTime time) {
        return repository.findByChatAndTimestampBefore(chat, time);
    }

    @Override
    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    @Override
    public void deleteMessageById(Long id) {

    }

    @Override
    public void deleteMessage(Message message) {

    }

    @Override
    public Optional<User> findUserByUsername(String username, String serviceToken) {
        // TODO: Check serviceToken
        return userRepository.findById(username).map((person) -> person);
    }

    @Override
    public Boolean sendMessage(Message message, String serviceToken) {
        // TODO: Check serviceToken
        try {
            repository.save(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Collection<Message> pullMessages(String serviceToken) {
        // TODO: Check serviceToken
        User serviceUser = null;
        // TODO: Find all messages of all chats
        // return repository.findByUser(serviceUser);
        return null;
    }
}
