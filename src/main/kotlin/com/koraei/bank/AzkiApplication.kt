package com.koraei.bank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.koraei.bank.repository"])
class AzkiApplication

fun main(args: Array<String>) {
    runApplication<AzkiApplication>(*args)
}
