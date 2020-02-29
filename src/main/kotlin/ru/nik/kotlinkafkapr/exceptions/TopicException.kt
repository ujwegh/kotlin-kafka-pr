package ru.nik.kotlinkafkapr.exceptions

import java.lang.RuntimeException

class TopicException(message: String?) : RuntimeException(message)