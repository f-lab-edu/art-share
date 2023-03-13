package com.flab.art_share.config.security

import com.flab.art_share.config.firebase.FirebaseConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(private val firebaseConfig: FirebaseConfig) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(FirebaseAuthFilter(firebaseConfig.firebaseAuth()), UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}