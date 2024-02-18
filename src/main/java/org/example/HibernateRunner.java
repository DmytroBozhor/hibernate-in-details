package org.example;

import jakarta.persistence.Tuple;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.example.model.*;
import org.example.model.other.BirthDate;
import org.example.model.other.PersonalInfo;
import org.example.model.other.Role;
import org.example.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;


@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {

        var configuration = HibernateUtil.getConfiguration();

//        var users = getUsers();
//        var departments = getDepartments();
//        var companies = getCompany(users, departments);

        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var query = "SELECT c " +
                "FROM Company c " +
                "JOIN FETCH c.users " +
                "JOIN FETCH c.departments";

        var companies = session.createQuery(query, Company.class).list();

        companies.forEach(company -> {
            var departments = company.getDepartments();
            var users = company.getUsers();

            System.out.println(departments);
            System.out.println(users);
        });

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
        var company = Company.builder()
                .name("Google")
                .build();

        users.forEach(company::addUser);
        departments.forEach(company::addDepartment);

        return company;
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
