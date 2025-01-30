package com.mitocode.licenseservice.expose.web;


import com.mitocode.licenseservice.model.dto.LicenseDTO;
import com.mitocode.licenseservice.service.LicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/v0/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @PostMapping("/emitir")
    public ResponseEntity<LicenseDTO> createLicense(@RequestBody LicenseDTO licenseDTO) {
        LicenseDTO savedLicense = licenseService.saveLicense(licenseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLicense);
    }

    @GetMapping
    public ResponseEntity<List<LicenseDTO>> getAllLicenses() {
        List<LicenseDTO> licenses = licenseService.getAllLicense();
        return ResponseEntity.ok(licenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseDTO> getLicenseById(@PathVariable String id) {
        LicenseDTO license = licenseService.getLicenseById(id);
        return ResponseEntity.ok(license);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LicenseDTO> updateLicense(@PathVariable String id, @RequestBody LicenseDTO licenseDTO) {
        LicenseDTO updatedLicense = licenseService.updateLicense(id, licenseDTO);
        return ResponseEntity.ok(updatedLicense);
    }

    @PatchMapping("/{id}/renovar")
    public ResponseEntity<LicenseDTO> updateLicense(@PathVariable String id) {
        LicenseDTO updatedLicense = licenseService.updateLicense(id);
        return ResponseEntity.ok(updatedLicense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable String id) {
        licenseService.deleteLicense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<LicenseDTO>> getAllByLicenseCategory(@RequestParam(name = "licenseCategory") String licenseCategory) {
        List<LicenseDTO> licenses = licenseService.getAllByLicenseCategory(licenseCategory);
        return ResponseEntity.ok(licenses);
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<LicenseDTO>> getAllByLicenseType(@RequestParam(name = "licenseType") String licenseType) {
        List<LicenseDTO> licenses = licenseService.getAllByLicenseType(licenseType);
        return ResponseEntity.ok(licenses);
    }

    @GetMapping("/by-active")
    public ResponseEntity<List<LicenseDTO>> getAllByActive(@RequestParam boolean active) {
        List<LicenseDTO> licenses = licenseService.getAllByActive(active);
        return ResponseEntity.ok(licenses);
    }

    @GetMapping("/slow/{flag}")
    public ResponseEntity<List<LicenseDTO>> getAllLicensesWithFlagForSlow(@PathVariable("flag") boolean flag) throws InterruptedException {
        if (flag) {
            log.info("Product Service slow");
            TimeUnit.MILLISECONDS.sleep(2400);
        }

        List<LicenseDTO> licenses = licenseService.getAllLicense();
        return ResponseEntity.ok(licenses);
    }

    @GetMapping("/error/flag")
    public ResponseEntity<List<LicenseDTO>> getAllLicensesWithFlag(@PathVariable("flag") boolean flag) throws InterruptedException {
        if (flag) {
            TimeUnit.MILLISECONDS.sleep(795);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        List<LicenseDTO> licenses = licenseService.getAllLicense();
        return ResponseEntity.ok(licenses);
    }

    @GetMapping("/api/mitocode/user")
    public ResponseEntity<String> testPrefix() {
        return ResponseEntity.ok("Response Ok");
    }

    @GetMapping(value = "/report-by-category-csv", produces = "text/csv")
    public ResponseEntity<byte[]> getReportByCategoryCsv(){

        byte[] report = licenseService.generateCsvReport();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=licenses_report.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }
}
