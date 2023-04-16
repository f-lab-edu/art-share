package com.flab.artshare.config.security

import com.flab.artshare.firebase.FirebaseService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.config.web.servlet.invoke

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val firbaseService: FirebaseService,
    private val unauthorizedEntryPoint: UnauthorizedEntryPoint,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
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
                JwtAuthFilter(firbaseService),
            )
            exceptionHandling {
                authenticationEntryPoint = unauthorizedEntryPoint
            }
        }
        return http.build()
    }
}
