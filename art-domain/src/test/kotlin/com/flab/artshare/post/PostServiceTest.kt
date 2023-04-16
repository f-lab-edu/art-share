package com.flab.artshare.post

import com.flab.artshare.common.dto.CreatePostReq
import com.flab.artshare.common.dto.UpdatePostReq
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.util.*

class PostServiceTest : DescribeSpec(
    {

        val postRepository = mockk<PostRepository>()
        val postService = PostService(postRepository)

        beforeTest {
            // Clear mocks before each test
            clearMocks(postRepository)
        }

        describe("PostService 테스트") {
            context("게시글 저장") {
                it("게시물을 저장하고 반환한다.") {
                    // Arrange
                    val request = CreatePostReq("Title1", "Content1")
                    val post = request.toEntity()
                    every { postRepository.save(any()) } returns post

                    // Act
                    val result = postService.savePost(request)

                    // Assert
                    result.title shouldBe "Title1"
                    result.content shouldBe "Content1"

                    verify { postRepository.save(any()) }
                }
            }

            context("모든 게시글 조회") {
                it("모든 게시글을 조회한다.") {
                    // Arrange
                    val postList = listOf(
                        Post(1L, "Title1", "Content1"),
                        Post(2L, "Title2", "Content2"),
                    )

                    every { postRepository.findAll() } returns postList

                    // Act
                    val result = postService.getAllPosts()

                    // Assert
                    result.size shouldBe 2
                    result.map { it.title } shouldContainExactlyInAnyOrder listOf("Title1", "Title2")
                    result.map { it.content } shouldContainExactlyInAnyOrder listOf("Content1", "Content2")

                    verify { postRepository.findAll() }
                }
            }

            context("특정 게시글 조회") {
                it("특정 게시글을 ID를 통해 조회한다.") {
                    // Arrange
                    val postId = 1L
                    val post = Post(postId, "Title1", "Content1")
                    every { postRepository.findById(postId) } returns Optional.of(post)

                    // Act
                    val result = postService.getPostById(postId)

                    // Assert
                    result.title shouldBe "Title1"
                    result.content shouldBe "Content1"

                    verify { postRepository.findById(postId) }
                }
            }

            context("게시글 수정하기") {
                it("게시글의 제목과 내용을 수정한 후 반환한다.") {
                    // Arrange
                    val postId = 1L
                    val post = Post(postId, "Title1", "Content1")
                    val updateRequest = UpdatePostReq("Updated Title", "Updated Content")
                    every { postRepository.findById(postId) } returns Optional.of(post)
                    every { postRepository.save(any()) } returns post.apply {
                        updatePost(
                            updateRequest.title,
                            updateRequest.content,
                        )
                    }

                    // Act
                    val result = postService.updatePost(postId, updateRequest)

                    // Assert
                    result.title shouldBe "Updated Title"
                    result.content shouldBe "Updated Content"

                    verify { postRepository.findById(postId) }
                    verify { postRepository.save(any()) }
                }

                context("게시글 삭제") {
                    it("특정 게시글을 ID를 통해 삭제한다.") {
                        // Arrange
                        val postId = 1L
                        every { postRepository.deleteById(postId) } just Runs

                        // Act
                        postService.deletePost(postId)

                        // Assert
                        verify { postRepository.deleteById(postId) }
                    }
                }
            }
        }
    },
)
