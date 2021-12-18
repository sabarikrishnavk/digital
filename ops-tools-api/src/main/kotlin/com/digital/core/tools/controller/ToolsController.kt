package com.digital.core.tools.controller

import com.digital.core.foundation.logger.EventLogger
import com.digital.core.tools.services.RestServices
import com.digital.core.tools.utils.EnvironmentConfig
import io.swagger.v3.oas.annotations.tags.Tag
//import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//@RefreshScope
@RestController
@RequestMapping("tools")
@Tag(name = "Tools controller ", description = "API documentation for Tools controller")
class ToolsController (private val akamaiServices: RestServices,
                       private val eventLogger: EventLogger,
                       private val environmentConfig: EnvironmentConfig
) {


    @GetMapping("/v1/status", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun test ():  ResponseEntity<Any> {
        val entities = arrayListOf(environmentConfig.message, environmentConfig.environment)
        return ResponseEntity<Any>(entities, HttpStatus.OK)
    }

    @GetMapping("/v1/api", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun index(): ResponseEntity<Any> {

        val entities = akamaiServices.findMessages(environmentConfig.message +" "+ environmentConfig.environment);
        return ResponseEntity<Any>(entities, HttpStatus.OK)
    }

    @GetMapping("/v2/api", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun v2index(): ResponseEntity<Any> {

        val entities = akamaiServices.findMessages(environmentConfig.message);
        return ResponseEntity<Any>(entities, HttpStatus.OK)
    }

//    @PostMapping("/v1/api")
//    fun post(@RequestBody message: Message) {
//        akamaiServices.post(message)
//    }
}