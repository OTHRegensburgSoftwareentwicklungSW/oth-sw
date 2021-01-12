package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.UserAlreadyExistsException;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;

import java.util.Collection;

public interface IFUserService {
    /**
     * Returns User with given username.
     *
     * @param username string identifying user
     * @return user with given username
     */
    User getUserByUsername(String username);

    /**
     * Returns Person with given username.
     *
     * @param username string identifying person
     * @return person with given username
     */
    Person getPersonByUsername(String username);

    /**
     * Returns Bot with given username.
     *
     * @param username string identifying bot
     * @return bot with given username
     */
    Bot getBotByUsername(String username);

    /**
     * Returns Persons with given first name.
     *
     * @param firstName first name indentifying persons
     * @return persons with given first name
     */
    Collection<Person> getPersonByFirstName(String firstName);

    /**
     * Returns Persons with given last name.
     *
     * @param lastName first name indentifying persons
     * @return persons with given first name
     */
    Collection<Person> getPersonByLastName(String lastName);

    /**
     * Saves given person
     *
     * @param person person to save
     * @return saved person
     * @throws UserAlreadyExistsException if person already is present
     */
    Person createPerson(Person person) throws UserAlreadyExistsException;

    /**
     * Saves given bot
     *
     * @param bot bot to save
     * @return saved bot
     * @throws UserAlreadyExistsException if bot already is present
     */
    Bot createBot(Bot bot) throws UserAlreadyExistsException;

    /**
     * Returns all person and bot users
     *
     * @return all stored users
     */
    Collection<User> getAllUsers();

    /**
     * Returns all Persons.
     *
     * @return all person users
     */
    Collection<Person> getAllPersons();

    /**
     * Returns all Bots.
     *
     * @return all bot users.
     */
    Collection<Bot> getAllBots();

    /**
     * Deletes user with given username.
     *
     * @param username sting identifying user
     */
    void deleteUserByUsername(String username);

    /**
     * Deletes person with given username.
     *
     * @param username sting identifying person
     */
    void deletePersonByUsername(String username);

    /**
     * Deletes bot with given username.
     *
     * @param username sting identifying bot
     */
    void deleteBotByUsername(String username);
}
