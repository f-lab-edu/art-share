package com.flab.artshare.config.security

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
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
        extractAuthToken(request)?.let { token ->
            try {
                val decodedToken = firebaseAuth.verifyIdToken(token)
                val authentication = UsernamePasswordAuthenticationToken(decodedToken.uid, null, null)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: FirebaseAuthException) {
                logger.error("Firebase authentication failed: ${e.message}")
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractAuthToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.substring(7)
}
