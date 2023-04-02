package com.flab.artshare.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UnauthorizedEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {
    private val errorMsg = mapOf("code" to HttpStatus.UNAUTHORIZED.value(), "msg" to "Unauthorized")

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.apply {
            contentType = MediaType.APPLICATION_JSON_VALUE
            status = HttpStatus.UNAUTHORIZED.value()
            writer.use { writer ->
                objectMapper.writeValue(writer, errorMsg)
            }
        }
    }
}
