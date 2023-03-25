package com.flab.artshare.config.security

import com.flab.artshare.config.firebase.FirebaseConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val firebaseConfig: FirebaseConfig,
    private val unauthorizedEntryPoint: UnauthorizedEntryPoint
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeRequests()
            .antMatchers("/swagger*/**", "/api-docs/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(
                FirebaseAuthFilter(firebaseConfig.firebaseAuth()),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedEntryPoint)
            .and()
            .build()
    }
}
