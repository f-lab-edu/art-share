package com.flab.artshare.e2e

import com.flab.artshare.dto.CreateProfileRes
import com.flab.artshare.firebase.FirebaseApi
import com.flab.artshare.naverCloud.NaverCloudApi
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.*
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.LinkedMultiValueMap

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
},) {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    private lateinit var port: Integer

    @MockkBean
    private lateinit var firebaseApi: FirebaseApi

    @MockkBean
    private lateinit var naverCloudApi: NaverCloudApi

    val bytes = "test".toByteArray()
    val multipartFile = MockMultipartFile("file", "test.jpg", "text/plain", bytes)
    val fileResource: ByteArrayResource = object : ByteArrayResource(multipartFile.bytes) {
        override fun getFilename(): String {
            return multipartFile.originalFilename ?: ""
        }
    }

    init {
        beforeTest() {
            restTemplate.restTemplate.messageConverters.add(FormHttpMessageConverter())
        }

        this.describe("/api/profile/me") {
            context("200") {
                it("success") {
                    // given
                    val headers = HttpHeaders()
                    headers.contentType = MediaType.MULTIPART_FORM_DATA
                    headers.set("Authorization", "Bearer dummy_token")

                    val body = LinkedMultiValueMap<String, Any>()
                    body.add("displayName", "seonjin kim")
                    body.add("about", "Hi hello bro")
                    body.add("profileImg", fileResource)

                    val requestBody = HttpEntity(body, headers)
                    val url = "http://localhost:$port/api/profile/me"

                    every { firebaseApi.verifyToken(any()) } returns "test_user_id"
                    every { naverCloudApi.uploadFile(any(), any()) } answers { }
                    every { naverCloudApi.getUrl(any()) } returns "test_img_path_url"

                    // when
                    val response = restTemplate.postForEntity(
                        url,
                        requestBody,
                        CreateProfileRes::class.java,
                    )

                    // then
                    response.statusCode shouldBe HttpStatus.CREATED
                    response.body?.displayName shouldBe "seonjin kim"
                    response.body?.about shouldBe "Hi hello bro"
                    response.body?.imgPath shouldBe "test_img_path_url"
                }
            }

            context("400") {
                it("success") {
                    // given
                    val headers = HttpHeaders()
                    headers.contentType = MediaType.MULTIPART_FORM_DATA
                    headers.set("Authorization", "Bearer dummy_token")

                    val body = LinkedMultiValueMap<String, Any>()
                    body.add("displayName", "seonjin kim this is very long")
                    body.add("about", "")
                    body.add("profileImg", fileResource)

                    val requestBody = HttpEntity(body, headers)
                    val url = "http://localhost:$port/api/profile/me"

                    every { firebaseApi.verifyToken(any()) } returns "test_user_id"

                    // when
                    val response = restTemplate.postForEntity(url, requestBody, String::class.java)
                    // then
                    response.statusCode shouldBe HttpStatus.BAD_REQUEST
                }
            }

            context("500") {
                it("fail to upload img") {
                    // given
                    val headers = HttpHeaders()
                    headers.contentType = MediaType.MULTIPART_FORM_DATA
                    headers.set("Authorization", "Bearer dummy_token")

                    val body = LinkedMultiValueMap<String, Any>()
                    body.add("displayName", "seonjin kim")
                    body.add("about", null)
                    body.add("profileImg", fileResource)

                    val requestBody = HttpEntity(body, headers)
                    val url = "http://localhost:$port/api/profile/me"

                    every { firebaseApi.verifyToken(any()) } returns "test_user_id"
                    every { naverCloudApi.uploadFile(any(), any()) } throws RuntimeException()
                    every { naverCloudApi.getUrl(any()) } returns "test_img_path_url"

                    // when
                    val response = restTemplate.postForEntity(
                        url,
                        requestBody,
                        CreateProfileRes::class.java,
                    )

                    // then
                    response.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR
                }
            }
        }
    }
}
