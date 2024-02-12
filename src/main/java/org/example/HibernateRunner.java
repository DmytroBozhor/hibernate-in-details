package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.example.util.HibernateUtil;

import java.time.LocalDate;


@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        var configuration = HibernateUtil.getConfiguration();
        log.info("Configuration has been initialized: {}", configuration);

        var company = Company.builder()
//                .id(1L)
                .name("Google60")
                .build();
        var user = User.builder()
                .username("zen70")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Lichigo")
                        .lastname("Damenson70")
                        .birthDate(new BirthDate(LocalDate.of(2000, 7, 17)))
                        .build())
                .role(Role.USER)
                .company(company)
                .build();

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            log.debug("Session has been opened: {}", session);
            var transaction = session.beginTransaction();

            session.persist(company);

            transaction.commit();
        } catch (Exception e) {
            log.error("Exception has been encountered", e);
            throw new RuntimeException(e);
        }
    }
}
