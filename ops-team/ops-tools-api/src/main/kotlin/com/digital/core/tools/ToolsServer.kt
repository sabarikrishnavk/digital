package com.digital.core.tools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@ComponentScan(basePackages =["com.digital.core"])
@SpringBootApplication
class ToolsServer

fun main(args: Array<String>) {

	runApplication<ToolsServer>(*args)
}


