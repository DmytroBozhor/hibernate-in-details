package org.example;

import org.example.converter.BirthDateConverter;
import org.example.model.BirthDate;
import org.example.model.Role;
import org.example.model.User;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;


public class HibernateRunner {

    public static void main(String[] args) {

        var configuration = new Configuration()
                .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
                .addAttributeConverter(BirthDateConverter.class)
                .addAnnotatedClass(User.class);

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            var transaction = session.beginTransaction();
            try {
                var user = User
                        .builder()
                        .username("zen")
                        .firstname("Ban")
                        .lastname("Robinzon")
                        .birthDate(new BirthDate(LocalDate.of(2001, 4, 25)))
                        .role(Role.ADMIN)
                        .build();
                session.persist(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }

        }
    }
}
