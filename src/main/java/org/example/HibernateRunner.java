package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.BirthDate;
import org.example.model.PersonalInfo;
import org.example.model.Role;
import org.example.model.User;
import org.example.util.HibernateUtil;

import java.time.LocalDate;


@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        var configuration = HibernateUtil.getConfiguration();
        log.info("Configuration has been initialized: {}", configuration);

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            log.debug("Session has been opened: {}", session);
            var transaction = session.beginTransaction();

            var user = User.builder()
                    .username("sien")
                    .personalInfo(PersonalInfo.builder()
                            .firstname("Lichigo")
                            .lastname("Damenson")
                            .birthDate(new BirthDate(LocalDate.of(2000, 7, 17)))
                            .build())
                    .role(Role.USER)
                    .build();

            session.persist(user);
            log.info("User is in a persistent state: {}", user);

            transaction.commit();
        } catch (Exception e) {
            log.error("Exception has been encountered", e);
            throw new RuntimeException(e);
        }
    }
}
