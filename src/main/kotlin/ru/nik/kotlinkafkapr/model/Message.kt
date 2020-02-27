package ru.nik.kotlinkafkapr.model

import java.time.LocalDateTime

class Message(val name: String, val body: String) {
    val dateTime: LocalDateTime = LocalDateTime.now()
}