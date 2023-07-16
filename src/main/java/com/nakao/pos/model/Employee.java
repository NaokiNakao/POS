package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "employee")
public class Employee extends Person {

    @Id
    private String id;
    @Email(message = "Not valid email")
    private String email;
    private String password;
    private String role;

    public static final String ID_PATTERN = "EMP######";

    @Builder
    public Employee(String firstName, String lastName, String phone, String id, String email, String password, String role) {
        super(firstName, lastName, phone);
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
