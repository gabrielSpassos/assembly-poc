package com.gabrielspassos.poc.scheduler;

import com.gabrielspassos.poc.service.AssemblyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ExpireAssemblyScheduler {

    private final AssemblyService assemblyService;

    @Scheduled(fixedDelayString = "${assembly.expire-delay-milliseconds-time}")
    @SchedulerLock(
            name = "ExpireAssemblies",
            lockAtMostFor = "${assembly.expire-delay-milliseconds-time}",
            lockAtLeastFor = "${assembly.expire-delay-milliseconds-time}"
    )
    public void expireAssemblies() {
        LockAssert.assertLocked();
        log.info("Realizando expiração de assembleias");
        assemblyService.expireAssemblies()
                .doOnComplete(() -> log.info("Expirado as assembleias"))
                .subscribe();
    }
}
