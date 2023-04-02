package com.flab.artshare.config.security

import com.flab.artshare.config.firebase.FirebaseConfig
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
    private val firebaseConfig: FirebaseConfig,
    private val unauthorizedEntryPoint: UnauthorizedEntryPoint
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
            formLogin {
                permitAll()
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
                FirebaseAuthFilter(firebaseConfig.firebaseAuth())
            )
            exceptionHandling {
                authenticationEntryPoint = unauthorizedEntryPoint
            }
        }
        return http.build()
    }
}
