package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author Naoki Nakao on 7/29/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "password_reset_request")
public class PasswordResetRequest {

    @Id
    private Long id;
    private String email;
    private String token;
    private LocalDateTime expirationDate;

}
