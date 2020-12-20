package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void whenFindByFirstName_thenReturnPerson() {
        // given
        Person joe = new Person(
                "joe",
                "",
                null,
                "Joe",
                "Smith",
                "joe@smith.com");

        entityManager.persist(joe);
        entityManager.flush();

        // when
        Person found = personRepository.findByFirstName(joe.getFirstName());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo(joe.getFirstName());
        assertThat(found).isEqualTo(joe);
    }

    @Test
    public void whenFindByUsername_thenReturnPerson() {
        // given
        Person joe = new Person(
                "joe",
                "",
                null,
                "Joe",
                "Smith",
                "joe@smith.com");

        entityManager.persist(joe);
        entityManager.flush();

        // when
        Person found = personRepository.findByUsername(joe.getUsername());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(joe.getUsername());
        assertThat(found).isEqualTo(joe);
    }
}
