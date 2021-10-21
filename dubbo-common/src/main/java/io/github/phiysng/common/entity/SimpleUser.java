package io.github.phiysng.common.entity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SimpleUser {

    private int id;

    private String name;

    private String address;

    private boolean gender;
}
