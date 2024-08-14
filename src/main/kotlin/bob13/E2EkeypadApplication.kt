package com.example.e2ekeypad

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
@EnableRedisRepositories(basePackages = ["bob13.domain.repository"])
@ComponentScan(basePackages = ["bob13.presentation.controller", "bob13.domain.service", "bob13.domain.repository"])
class E2EkeypadApplication

fun main(args: Array<String>) {
	runApplication<E2EkeypadApplication>(*args)
}
