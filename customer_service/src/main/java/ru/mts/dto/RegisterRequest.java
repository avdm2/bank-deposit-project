package ru.mts.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RegisterRequest {

    private String username;
    private String password;
    private String phoneNumber;
}
