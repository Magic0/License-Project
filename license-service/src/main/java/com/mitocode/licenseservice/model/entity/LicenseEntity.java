package com.mitocode.licenseservice.model.entity;

import com.mitocode.common_models.model.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "license")
@NoArgsConstructor
@AllArgsConstructor
public class LicenseEntity {

    @Id
    private String idLicense;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private Boolean isActive;
    private String licenseType;
    private String licenseCategory;
    private ClientEntity client;
}
