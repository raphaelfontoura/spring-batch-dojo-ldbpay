package com.ldbpay.dojo.springbatch

import kotlin.system.exitProcess
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class SpringbatchApplication

fun main(args: Array<String>) {
	runApplication<SpringbatchApplication>(*args)
//	exitProcess(SpringApplication.exit(runApplication<SpringbatchApplication>(*args)))
}
