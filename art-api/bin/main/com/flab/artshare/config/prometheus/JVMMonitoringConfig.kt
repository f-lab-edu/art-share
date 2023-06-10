package com.flab.artshare.config.prometheus

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean

class JVMMonitoringConfig {
    @Bean
    fun configurer(
        @Value("\${spring.application.name}") applicationName: String?,
    ): MeterRegistryCustomizer<MeterRegistry>? {
        return MeterRegistryCustomizer { registry: MeterRegistry ->
            registry.config().commonTags("application", applicationName)
        }
    }
}
