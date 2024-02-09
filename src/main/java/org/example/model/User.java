package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    private String username;

    private String firstname;

    private String lastname;

//    @Convert(converter = BirthDateConverter.class)
    private BirthDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;
}
