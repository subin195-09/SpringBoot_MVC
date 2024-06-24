package com.sample.spring_mvc.controller

import com.sample.spring_mvc.domain.dto.KeywordReadResponseDto
import com.sample.spring_mvc.domain.dto.PageResponseDto
import com.sample.spring_mvc.service.KeywordService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.time.Instant
import java.util.UUID

@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class KeywordControllerTest {

  private lateinit var mockMvc: MockMvc

  @MockBean
  private lateinit var keywordService: KeywordService

  @BeforeEach
  internal fun setUp(
    webApplicationContext: WebApplicationContext,
    restDocumentationContextProvider: RestDocumentationContextProvider
  ) {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
      .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
      .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
      .apply<DefaultMockMvcBuilder>(
        MockMvcRestDocumentation.documentationConfiguration(
          restDocumentationContextProvider
        ).operationPreprocessors()
          .withRequestDefaults(Preprocessors.prettyPrint())
          .withResponseDefaults(Preprocessors.prettyPrint())
      )
      .build()
  }

  @Test
  fun `전체 Keyword 조회`() {
    // Given
    val keywordReadResponseDto1 = KeywordReadResponseDto(
      id = UUID.randomUUID(),
      name = "sample keyword 1",
      createdDatetime = Instant.now()
    )

    val keywordReadResponseDto2 = KeywordReadResponseDto(
      id = UUID.randomUUID(),
      name = "sample keyword 2",
      createdDatetime = Instant.now()
    )

    // When, Then
    val pageResponseDto = PageResponseDto(
      limit = 10, offset = 0,
      results = listOf(
        keywordReadResponseDto1, keywordReadResponseDto2
      )
    )

    `when`(
      keywordService.getKeywordList(10, 0)
    ).thenReturn(pageResponseDto)

    mockMvc.perform(
      RestDocumentationRequestBuilders.get("/keyword")
        .param("limit", 10L.toString())
        .param("offset", 0L.toString())
    )
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.limit").value(pageResponseDto.limit))
      .andExpect(jsonPath("$.offset").value(pageResponseDto.offset))
      .andExpect(jsonPath("$.results").isArray)
      .andExpect(jsonPath("$.results[0].keyword_id").value(pageResponseDto.results?.get(0)?.id.toString()))
      .andExpect(jsonPath("$.results[0].keyword_name").value(pageResponseDto.results?.get(0)?.name))
      .andExpect(jsonPath("$.results[0].keyword_created_datetime").value(pageResponseDto.results?.get(0)?.createdDatetime.toString()))
      .andDo(
        document(
          "get-keyword-list",
          preprocessRequest(),
          preprocessResponse(),
          queryParameters(
            RequestDocumentation.parameterWithName("offset").description("The page offset you want"),
            RequestDocumentation.parameterWithName("limit").description("The page limit you want"),
          ),
          responseFields(
            fieldWithPath("limit").description("The page limit"),
            fieldWithPath("offset").description("The page offset"),
            fieldWithPath("results[]").description("List of point history"),
            fieldWithPath("results[].keyword_id").description("The unique identifier of the keyword."),
            fieldWithPath("results[].keyword_name").description("The name of the keyword."),
            fieldWithPath("results[].keyword_created_datetime").description("The datetime when the keyword was created.")
          )
        )
      )
  }
}