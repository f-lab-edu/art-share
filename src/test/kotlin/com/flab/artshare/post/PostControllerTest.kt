package com.flab.artshare.post

import com.flab.artshare.common.dto.CreatePostReq
import com.flab.artshare.common.dto.PostRes
import com.flab.artshare.common.dto.UpdatePostReq
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest : FunSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    private val port: Int = 0

    init {

        test("Create Post") {
            // Arrange
            val requestBody = CreatePostReq("Test Title", "Test Content")
            val url = "http://localhost:$port/api/post"

            // Act
            val response = restTemplate.postForEntity(url, requestBody, Void::class.java)

            // Assert
            response.statusCode shouldBe HttpStatus.CREATED
        }

        test("Get Post by ID") {
            // Arrange
            val postId = 1L
            val url = "http://localhost:$port/api/post/$postId"

            // Act
            val response = restTemplate.getForEntity(url, Post::class.java)

            // Assert
            response.statusCode shouldBe HttpStatus.OK
            response.body?.id shouldBe postId
        }

        test("Get All Posts") {
            // Arrange
            val url = "http://localhost:$port/api/post"

            // Act
            val response: ResponseEntity<List<PostRes>> = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
            )

            // Assert
            response.statusCode shouldBe HttpStatus.OK
            response.body?.shouldNotBeEmpty()
        }

        test("Update Post") {
            // Arrange
            val postId = 1L
            val requestBody = UpdatePostReq("Updated Title", "Updated Content")
            val url = "http://localhost:$port/api/post/$postId"

            // Act
            val response: ResponseEntity<PostRes> = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                HttpEntity(requestBody),
            )

            // Assert
            response.statusCode shouldBe HttpStatus.OK
        }

        test("Delete Post") {
            // Arrange
            val postId = 1L
            val url = "http://localhost:$port/api/post/$postId"

            // Act
            val response: ResponseEntity<Void> = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
            )

            // Assert
            response.statusCode shouldBe HttpStatus.NO_CONTENT
        }
    }
}
