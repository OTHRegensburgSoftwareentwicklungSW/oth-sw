package de.othr.bib48218.chat.repository;

import antlr.debug.MessageAdapter;
import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Collection<Message> findByAuthor(User author);

    Collection<Message> findByAuthor_Username(String username);

    Collection<Message> findByChat(Chat chat);

    Collection<Message> findByChatAndTimestampBefore(Chat chat, LocalDateTime timestamp);

    Collection<Message> findByChatAndTimestampBeforeAndAuthor_Username(Chat chat, LocalDateTime timestamp, String username);

    Collection<Message> findByChatAndAuthor_Username(Chat chat, String username);
}
