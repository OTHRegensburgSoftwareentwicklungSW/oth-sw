package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Message;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface IFMessageRestControllerV1 {
    /**
     * Returns messages
     *
     * @param chatId   the id of the chat whose messages to receive
     * @param dateTime the optional date time since when messages to receive
     * @return messages matching parameter constraint
     */
    ResponseEntity<Collection<Message>> getMessages(Long chatId, String dateTime);

    /**
     * Saves given message.
     *
     * @param message the message to save
     * @return saved message
     */
    ResponseEntity<Message> postMessage(Message message);
}
