package com.mitocode.licensejob.quartz;

import com.mitocode.licensejob.service.LicenseJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LicenseJobs {
    private final LicenseJobService licenseJobService;

    @Scheduled(cron = "0 * * * * ?")
    public void checkAndUpdateExpiredLicenses() {
        licenseJobService.updateExpiredLicenses();
        log.info("Licencias expiradas actualizadas.");
    }

    @Scheduled(cron = "0 */2 * * * *")
    public void checkAndDeleteOldInactiveLicenses() {
        licenseJobService.deleteOldInactiveLicenses();
        log.info("Licencias inactivas eliminadas.");
    }
}
