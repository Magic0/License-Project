package com.mitocode.licenseservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDTO {

    private String idLicense;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private Boolean isActive;
    private String licenseType;
    private String licenseCategory;
    private ClientDTO client;
    private Integer port;
}
