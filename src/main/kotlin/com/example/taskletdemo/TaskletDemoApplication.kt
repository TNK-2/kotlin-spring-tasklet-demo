package com.example.taskletdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskletDemoApplication

fun main(args: Array<String>) {
	runApplication<TaskletDemoApplication>(*args)
}
