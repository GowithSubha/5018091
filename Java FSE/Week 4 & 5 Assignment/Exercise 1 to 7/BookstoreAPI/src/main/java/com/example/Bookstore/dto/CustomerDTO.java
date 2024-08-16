package com.example.Bookstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;



@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
}
