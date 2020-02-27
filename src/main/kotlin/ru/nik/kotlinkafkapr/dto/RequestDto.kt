package ru.nik.kotlinkafkapr.dto

import org.springframework.lang.NonNull

data class RequestDto(@NonNull val user: String ="",@NonNull  val message: String="") {
}