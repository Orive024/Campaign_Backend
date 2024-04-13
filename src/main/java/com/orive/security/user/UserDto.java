package com.orive.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private String mobilenumber;
    private String username;
}
