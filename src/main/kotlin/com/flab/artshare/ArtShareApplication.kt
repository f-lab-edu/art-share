package com.flab.artshare

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArtShareApplication

fun main(args: Array<String>) {
    runApplication<ArtShareApplication>(*args)
}
