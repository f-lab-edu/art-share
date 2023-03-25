package com.flab.artshare.config.security

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FirebaseAuthFilter(private val firebaseAuth: FirebaseAuth) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractAuthToken(request)
        val verifiedToken = verifyToken(token)
        updateAuthentication(verifiedToken)
        filterChain.doFilter(request, response)
    }

    private fun updateAuthentication(verifiedToken: FirebaseToken) {
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(verifiedToken.uid, null, null)
    }

    private fun verifyToken(token: String): FirebaseToken =
        runCatching { firebaseAuth.verifyIdToken(token) }
            .getOrElse { throw IllegalArgumentException("Invalid token") }

    private fun extractAuthToken(request: HttpServletRequest): String {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7)
        } else {
            throw IllegalArgumentException("Missing or invalid Authorization header")
        }
    }
}