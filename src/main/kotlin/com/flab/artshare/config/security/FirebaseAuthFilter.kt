package com.flab.artshare.config.security

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

data class FirebaseAuthFilter(private val firebaseAuth: FirebaseAuth) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        extractAuthToken(request)?.let {
            val token = verifyToken(it)
            updateAuthentication(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun updateAuthentication(verifiedToken: FirebaseToken) {
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(verifiedToken.uid, null, null)
    }

    private fun verifyToken(token: String): FirebaseToken =
        runCatching { firebaseAuth.verifyIdToken(token) }
            .getOrElse { throw IllegalArgumentException("Invalid token") }

    private fun extractAuthToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.substring(7)
}
