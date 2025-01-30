package com.mitocode.clientqueryservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "tb_client")
@NoArgsConstructor
@AllArgsConstructor
public class ClientPostgresEntity {

    @Id
    private String DNI;
    private String name;
    private String lastname;
    private String email;
    private LocalDate birthday;
}
