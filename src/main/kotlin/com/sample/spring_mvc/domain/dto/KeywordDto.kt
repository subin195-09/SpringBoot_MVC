package com.sample.spring_mvc.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.UUID

data class KeywordReadResponseDto(
  @JsonProperty("keyword_id")
  val id: UUID,

  @JsonProperty("keyword_name")
  val name: String?,

  @JsonProperty("keyword_created_datetime")
  val createdDatetime: Instant
)

data class KeywordCreateRequestDto(
  @JsonProperty("keyword_name")
  val name: String?
)

data class KeywordUpdateRequestDto(
  @JsonProperty("keyword_name")
  val name: String?
)

data class KeywordUpdateResponseDto(
  @JsonProperty("keyword_id")
  val id: UUID,

  @JsonProperty("keyword_name")
  val name: String?
)



