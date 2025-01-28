package com.mitocode.clientservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "client")
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    private String DNI;
    private String name;
    private String lastname;
    private String email;
    private LocalDate birthday;
}
