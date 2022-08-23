package com.kcsb.requests;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    String username;
    String password;
    String email;
    String firstname;
    String lastname;
    List<String> clientRoles;
}