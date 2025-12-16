package com.freasq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FreasqApplication

fun main(args: Array<String>) {
	runApplication<FreasqApplication>(*args)
}
