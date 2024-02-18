package org.example;

import org.example.model.*;
import org.example.model.other.BirthDate;
import org.example.model.other.PersonalInfo;
import org.example.model.other.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;

public class AppTests {

    private static SessionFactory sessionFactory;
    private Session session;
    private Company company;
    private User user;
    private Department department;

    @BeforeAll
    static void buildSessionFactory() {
        sessionFactory = PostgreSQLContainerRunner.buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        company = Company.builder()
                .name("MediaWar")
                .build();
        user = User.builder()
                .role(Role.USER)
                .username("userOne")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Dmytro")
                        .lastname("Bozhor")
                        .birthDate(new BirthDate(LocalDate.of(2000, 4, 25)))
                        .build())
                .company(company)
                .build();
        department = Department.builder()
                .name("First Department")
                .capacity(25)
                .build();

        company.addUser(user);
        company.addDepartment(department);

        session = sessionFactory.openSession();
    }

    @Test
    void findUserWithCriteriaAPI() {
        session.beginTransaction();

        session.persist(company);

        var criteriaBuilder = session.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria
                .select(user)
                .where(criteriaBuilder.equal(user.get("id"), 1));

        var userOptional = session.createQuery(criteria).uniqueResultOptional();

        assertThat(userOptional).isPresent();
        assertThat(userOptional.get()).isEqualTo(this.user);

        session.getTransaction().commit();
    }

    @AfterEach
    void closeSession() {
        session.close();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    private void inTransaction(Consumer<Session> consumer) {
        session.beginTransaction();
        consumer.accept(session);
        session.getTransaction().commit();
    }

}
