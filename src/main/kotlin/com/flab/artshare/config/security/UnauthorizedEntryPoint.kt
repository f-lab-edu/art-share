package com.flab.artshare.config.security

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.fasterxml.jackson.databind.ObjectMapper
@Component
class UnauthorizedEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val errorMsg = mapOf("code" to HttpStatus.UNAUTHORIZED.value(), "msg" to "Unauthorized")
        response?.contentType = "application/json;charset=UTF-8"
        response?.status = HttpStatus.UNAUTHORIZED.value()
        response?.writer?.print(objectMapper.writeValueAsString(errorMsg))
    }
}

