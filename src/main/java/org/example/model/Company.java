package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<User> users = new HashSet<>();

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Department> departments = new HashSet<>();

    public boolean addUser(User user) {
        user.setCompany(this);
        return users.add(user);
    }

    public boolean addDepartment(Department department) {
        department.setCompany(this);
        return departments.add(department);
    }

}
