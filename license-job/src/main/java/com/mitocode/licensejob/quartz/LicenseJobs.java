package com.mitocode.licensejob.quartz;

import com.mitocode.licensejob.service.LicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LicenseJobs {
    private final LicenseService licenseService;

    @Scheduled(cron = "0 * * * * ?")
    public void checkAndUpdateExpiredLicenses() {
        licenseService.updateExpiredLicenses();
        log.info("Licencias expiradas actualizadas.");
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void checkAndDeleteOldInactiveLicenses() {
        licenseService.deleteOldInactiveLicenses();
        log.info("Licencias inactivas eliminadas.");
    }
}
