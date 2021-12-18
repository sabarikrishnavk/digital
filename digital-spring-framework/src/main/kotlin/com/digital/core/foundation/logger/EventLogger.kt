package com.digital.core.foundation.logger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.stereotype.Component
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@Component
class EventLogger {

    private val LOGGER: Logger = Logger.getLogger(EventLogger::class.java.getName())

    val gson: Gson = GsonBuilder().setExclusionStrategies(BlackListLogExclusion()  ).create()

    fun log(level: Level, message:String?, eventType: EventType, vararg args: Any?){
        val logmap: MutableMap<String, Any> = HashMap()

        if(message !=null) logmap["message"] = message
        if(message !=null) logmap["event"] = eventType.toString()

        for (obj in args){
            logmap["" + obj?.javaClass?.name] = gson.toJson(obj)
        }
        LOGGER.log(level,gson?.toJson(logmap)?.toString()?:"")
    }
    fun log(eventType: EventType, message:String?, vararg args: Any?) {
        log(Level.INFO,message,eventType,*args)
    }
    fun logMessage(logEntry: LogEntry){
       log(logEntry.eventType,logEntry.message, *logEntry.params )
    }
}
