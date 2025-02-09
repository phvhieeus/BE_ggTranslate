package com.translate.domain.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUpdateUserDTO {
    private long id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private String address;
}