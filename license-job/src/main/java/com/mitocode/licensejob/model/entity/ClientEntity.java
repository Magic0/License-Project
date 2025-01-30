package com.mitocode.licensejob.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    private String DNI;
    private String name;
    private String lastname;
    private String email;
    private LocalDate birthday;
}
