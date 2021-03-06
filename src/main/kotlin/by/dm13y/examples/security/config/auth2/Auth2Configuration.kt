package by.dm13y.examples.security.config.auth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

@Profile("auth2-test")
@Configuration
class Auth2CommonConfig {
    @Bean
    fun tokenStore(): TokenStore {
        return InMemoryTokenStore()
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("some_signing_key")
        return converter
    }
}
