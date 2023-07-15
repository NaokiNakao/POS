package com.nakao.pos.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Person {

    protected String firstName;
    protected String lastName;
    protected String phone;

}
