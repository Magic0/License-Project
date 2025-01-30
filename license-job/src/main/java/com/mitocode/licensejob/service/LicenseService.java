package com.mitocode.licensejob.service;

import com.mitocode.licensejob.model.entity.LicenseEntity;
import com.mitocode.licensejob.service.repository.LicenseCustomRepository;
import com.mitocode.licensejob.service.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final LicenseCustomRepository licenseCustomRepository;

    public void updateExpiredLicenses() {
        LocalDate today = LocalDate.now();
        log.info("Ejecutando job para desactivar licencias expiradas...");

        List<LicenseEntity> expiredLicenses = licenseCustomRepository.getAllByExpirationDateBeforeAndIsActive(today, true);


        for (LicenseEntity license : expiredLicenses) {
            license.setIsActive(false);
            licenseRepository.save(license);
            log.info("Licencia {} ha sido desactivada", license.getIdLicense());
        }
    }

    public void deleteOldInactiveLicenses() {
        LocalDate thirtyDaysAgo  = LocalDate.now().minusDays(30);
        log.info("Ejecutando job para eliminar licencias inactivas con más de 30 días de expiradas...");

        List<LicenseEntity> expiredLicenses = licenseCustomRepository.getAllByExpirationDateBeforeAndIsActive(thirtyDaysAgo, false);

        for (LicenseEntity license : expiredLicenses) {
            licenseRepository.delete(license);
            log.info("Licencia {} ha sido eliminada por haber expirado hace más de 30 días", license.getIdLicense());
        }
    }
}
