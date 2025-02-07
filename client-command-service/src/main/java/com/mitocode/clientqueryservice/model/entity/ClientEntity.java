package com.mitocode.clientqueryservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "client")
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @Indexed(unique = true)
    private String DNI;
    private String name;
    private String lastname;
    private String email;
    private LocalDate birthday;
}
