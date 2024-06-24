package com.sample.spring_mvc.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PageResponseDto<T>(
  @JsonProperty("limit")
  val limit: Long?,

  @JsonProperty("offset")
  val offset: Long?,

  @JsonProperty("results")
  var results: List<T>?
)