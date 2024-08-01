package com.wrathenn.gateway.service.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    val jwtConverter: RsoiJwtConverter,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers(
                    AntPathRequestMatcher("/swagger-ui/**"),
                    AntPathRequestMatcher("/v3/api-docs/**"),
                    AntPathRequestMatcher("/health"),
                ).permitAll().anyRequest().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt {
                    it.jwtAuthenticationConverter(jwtConverter)
                }
            }

        return http.build()
    }
}
