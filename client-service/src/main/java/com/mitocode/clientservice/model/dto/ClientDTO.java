package com.mitocode.clientservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private String DNI;
    private String name;
    private String lastname;
    private String email;
    private LocalDate birthday;
    private Integer port;
}
