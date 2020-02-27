package ru.nik.kotlinkafkapr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinKafkaPrApplication

fun main(args: Array<String>) {
    runApplication<KotlinKafkaPrApplication>(*args)
}
