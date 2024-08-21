package com.example.bookstoreapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerDTO {

    private int id;

    @JsonProperty("customer_name")
    private String name;

    @JsonIgnore
    private String email;

    @JsonProperty("customer_address")
    private String address;

}
