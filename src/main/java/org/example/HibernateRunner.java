package org.example;

import jakarta.persistence.EntityManager;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.example.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;


@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        var configuration = HibernateUtil.getConfiguration();

        var users = getUsers();
        var departments = getDepartments();
        var company = getCompany(users, departments);

        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        

        session.getTransaction().commit();
    }

    private static List<Department> getDepartments() {

        var departmentOne = Department.builder()
                .name("First Department")
                .capacity(30)
                .build();

        var departmentTwo = Department.builder()
                .name("Second Department")
                .capacity(45)
                .build();

        return List.of(departmentOne, departmentTwo);
    }

    private static Company getCompany(List<User> users, List<Department> departments) {
        return Company.builder()
                .name("Google")
                .users(users)
                .departments(departments)
                .build();
    }

    private static List<User> getUsers() {

        var userOne = User.builder()
                .username("user1")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Lichigo")
                        .lastname("Damenson")
                        .birthDate(new BirthDate(LocalDate.of(2000, 7, 17)))
                        .build())
                .role(Role.USER)
                .build();

        var userTwo = User.builder()
                .username("user2")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Lichigo")
                        .lastname("Damenson")
                        .birthDate(new BirthDate(LocalDate.of(2000, 7, 17)))
                        .build())
                .role(Role.USER)
                .build();

        return List.of(userOne, userTwo);
    }
}
