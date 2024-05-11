package com.example.libraryserver.dtos;

import com.example.libraryserver.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    //private Long version;
    private String login;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private List<LoanDTO> loans;
}
