package com.digital.core.tools.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component

@RefreshScope
@Component
class EnvironmentConfig {
    @Value("\${bean.message}")
    public val message: String? = null
    @Value("\${platform.environment}")
    public val environment: String? = null

}