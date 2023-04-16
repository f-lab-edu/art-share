package com.flab.artshare.config.security

import com.flab.artshare.AuthService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthFilter(private val authService: AuthService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        extractAuthToken(request)?.let {
            updateAuthentication(it)
        }
        filterChain.doFilter(request, response)
    }

    private fun updateAuthentication(token: String) {
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(this.authService.getUserId(token), null, null)
    }

    private fun extractAuthToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.substring(7)
}
