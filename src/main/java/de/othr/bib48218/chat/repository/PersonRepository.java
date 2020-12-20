package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;

public interface PersonRepository extends UserRepository<Person> {
    Person findByFirstName(String firstName);
    Person findByUsername(String username);
}
