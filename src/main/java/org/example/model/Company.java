package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"users", "departments"})
@EqualsAndHashCode(exclude = {"users", "departments"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<User> users = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<Department> departments = new ArrayList<>();

    public boolean addUser(User user) {
        user.setCompany(this);
        return users.add(user);
    }

    public boolean addDepartment(Department department) {
        department.setCompany(this);
        return departments.add(department);
    }

}
