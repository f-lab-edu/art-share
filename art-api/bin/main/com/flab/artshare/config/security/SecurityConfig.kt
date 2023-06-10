package com.flab.artshare.config.security

import com.flab.artshare.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authService: AuthService,
    private val unauthorizedEntryPoint: UnauthorizedEntryPoint,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                // Swagger 관련 경로 인증 허용
                authorize("/swagger-ui.html", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/actuator/**", permitAll)

                // 나머지 경로에 대한 인증 요구
                authorize(anyRequest, authenticated)
            }
            httpBasic {
                disable()
            }
            cors {
                disable()
            }
            csrf {
                disable()
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                JwtAuthFilter(authService),
            )
            exceptionHandling {
                authenticationEntryPoint = unauthorizedEntryPoint
            }
        }
        return http.build()
    }
}
