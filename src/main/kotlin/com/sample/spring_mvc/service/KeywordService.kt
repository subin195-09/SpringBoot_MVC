package com.sample.spring_mvc.service

import com.sample.spring_mvc.domain.dto.*
import com.sample.spring_mvc.mapper.KeywordMapper
import com.sample.spring_mvc.repository.KeywordRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.lang.RuntimeException
import java.util.*

@Service
class KeywordService(
  private val keywordRepository: KeywordRepository,
  private val keywordMapper: KeywordMapper,
) {

  fun getKeywordList(
    offset: Long,
    limit: Long
  ): PageResponseDto<KeywordReadResponseDto> {
    val dtoList = keywordRepository.findAllKeywordWithPagination(
      offset, limit
    ).map {
      keywordMapper.keywordToKeywordReadResponseDto(it)
    }

    return PageResponseDto(
      limit = limit, offset = offset, results = dtoList
    )
  }

  @Transactional(readOnly = true)
  fun getKeywordById(
    keywordId: UUID
  ): KeywordReadResponseDto {
    return keywordMapper.keywordToKeywordReadResponseDto(
      keywordRepository.findById(keywordId).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 키워드입니다.")
      }
    )
  }

  @Transactional
  fun createKeyword(
    keywordCreateRequestDto: KeywordCreateRequestDto
  ): UUID {
    val createdKeyword = keywordRepository.save(
      keywordMapper.keywordCreateRequestDtoToKeyword(keywordCreateRequestDto)
    )

    return createdKeyword.id
  }

  @Transactional
  fun updateKeyword(
    keywordId: UUID,
    keywordUpdateRequestDto: KeywordUpdateRequestDto
  ): KeywordUpdateResponseDto {
    val updatedKeyword = keywordMapper.updateKeyword(
      keywordRepository.findById(keywordId).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 키워드입니다.")
      }, keywordUpdateRequestDto
    )

    return keywordMapper.keywordToKeywordUpdateResponseDto(updatedKeyword)
  }

  @Transactional
  fun deleteKeyword(
    keywordId: UUID
  ) {
    keywordRepository.deleteById(keywordId)
  }
}
