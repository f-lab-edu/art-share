package com.flab.artshare

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ArtShareApplication

fun main(args: Array<String>) {
    runApplication<ArtShareApplication>(*args)
}
