package com.example.loginsystem.registration;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistrationRequest {

    private final String name;
    private final String email;
    private final String password;
    private final String username;
}
