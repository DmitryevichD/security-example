package by.dm13y.examples.security

import by.dm13y.examples.security.config.Roles
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@ActiveProfiles("basic-test")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@DirtiesContext
class JwtAuthTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Jwt token authentication with filter`() {
        val token = generateJwtToken()
        mockMvc.get("/api/v1/jwt/info") {
            accept = MediaType.APPLICATION_JSON
            header("Authorization", "Bearer $token")
        }.andExpect { status { isOk } }
    }

    fun generateJwtToken(): String
    {
        val claims = mapOf(
            "exp" to Date().time / 1000 + 100,
            "user_name" to "admin",
            "authorities" to arrayOf("ROLE_${Roles.JWT}")
        )

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, "some_signing_key".toByteArray())
            .compact()
    }
}
