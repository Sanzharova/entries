package com.example.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {
    String email;
    String firstName;
    String lastName;
    String middleName;
    String password;
}