package com.sample.spring_mvc.mapper

import com.sample.spring_mvc.domain.dto.KeywordCreateRequestDto
import com.sample.spring_mvc.domain.dto.KeywordReadResponseDto
import com.sample.spring_mvc.domain.dto.KeywordUpdateRequestDto
import com.sample.spring_mvc.domain.dto.KeywordUpdateResponseDto
import com.sample.spring_mvc.domain.entity.Keyword
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
interface KeywordMapper {
  fun keywordToKeywordReadResponseDto(
    keyword: Keyword
  ): KeywordReadResponseDto


  fun keywordCreateRequestDtoToKeyword(
    keywordCreateRequestDto: KeywordCreateRequestDto
  ): Keyword

  fun updateKeyword(
    @MappingTarget keyword: Keyword,
    keywordUpdateRequestDto: KeywordUpdateRequestDto
  ): Keyword

  fun keywordToKeywordUpdateResponseDto(
    keyword: Keyword
  ): KeywordUpdateResponseDto
}