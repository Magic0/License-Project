package com.mitocode.licenseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReportDTO {
    private String licenseCategory;
    private Long totalLicenses;
}