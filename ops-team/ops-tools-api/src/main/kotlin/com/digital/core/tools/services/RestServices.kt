package com.digital.core.tools.services

import com.digital.core.foundation.logger.EventLogger
import com.digital.core.tools.utils.ToolsEvents
import org.springframework.stereotype.Service

@Service
class RestServices ( private val eventLogger: EventLogger) {



    fun findMessages(akamaiurl: String?): List<String> {
        eventLogger.log(ToolsEvents.AKAMAI_REDIRECT_PRODUCTION,"findMessages","inside find messages");

        return arrayListOf(""+ akamaiurl)

    }
}