package ru.nik.kotlinkafkapr.dto

import org.springframework.lang.NonNull

data class RequestDto(@NonNull val topic: String = "",
                      @NonNull val userName: String = "",
                      @NonNull val message: String = "")