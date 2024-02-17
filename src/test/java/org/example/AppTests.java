package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Selection;
import org.assertj.core.api.Assertions;
import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.JpaCriteriaInsertSelect;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.*;

public class AppTests {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void buildSessionFactory() {
        sessionFactory = PostgreSQLContainerRunner.buildSessionFactory();
    }

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
    }

    @Test
    void findUserWithCriteriaAPI() {

        var companyForSave = Company.builder()
                .name("MediaWar")
                .build();

        var userForSave = User.builder()
                .role(Role.USER)
                .username("userOne")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Dmytro")
                        .lastname("Bozhor")
                        .birthDate(new BirthDate(LocalDate.of(2000, 4, 25)))
                        .build())
                .company(companyForSave)
                .build();

        companyForSave.setUsers(Collections.singletonList(userForSave));

        session.beginTransaction();

        session.persist(companyForSave);

        var criteriaBuilder = session.getCriteriaBuilder();

        var criteria = criteriaBuilder.createQuery(User.class);

        var user = criteria.from(User.class);

        criteria.select(user).where(criteriaBuilder.equal(user.get("id"), 1));

        var userOptional = session.createQuery(criteria).uniqueResultOptional();

        assertThat(userOptional).isPresent();

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
