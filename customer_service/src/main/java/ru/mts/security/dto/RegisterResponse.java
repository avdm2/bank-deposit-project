package ru.mts.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RegisterResponse {

    private Integer userId;
    private String username;
}
