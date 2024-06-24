package com.sample.spring_mvc.controller

import com.sample.spring_mvc.domain.dto.*
import com.sample.spring_mvc.service.KeywordService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Keyword", description = "Keyword - Basic CRUD Sample")
@RestController
@RequestMapping("/keyword")
class KeywordController(
  private val keywordService: KeywordService
) {
  @Operation(summary = "키워드 조회", description = "키워드를 조회합니다. (생성일자 기준)")
  @ApiResponse(responseCode = "200", description = "키워드 조회 성공")
  @GetMapping("")
  fun handleGetKeywordList(
    @RequestParam(required = false, name = "limit", defaultValue = "10") limit: Long,
    @RequestParam(required = false, name = "offset", defaultValue = "0") offset: Long,
  ): ResponseEntity<PageResponseDto<KeywordReadResponseDto>> {
    return ResponseEntity(
      keywordService.getKeywordList(limit, offset),
      HttpStatus.OK
    )
  }

  @Operation(summary = "특정 키워드 조회", description = "ID로 키워드를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "키워드 조회 성공")
  @ApiResponse(responseCode = "404", description = "존재하지 않는 키워드 조회")
  @GetMapping("/{keywordId}")
  fun handleGetKeywordById(
    @PathVariable keywordId: UUID
  ): ResponseEntity<KeywordReadResponseDto> {
    return ResponseEntity(
      keywordService.getKeywordById(keywordId),
      HttpStatus.OK
    )
  }

  @Operation(summary = "키워드 생성", description = "키워드를 생성합니다.")
  @ApiResponse(responseCode = "201", description = "키워드 생성 성공")
  @PostMapping("")
  fun handleCreateKeyword(
    @RequestBody keywordCreateRequestDto: KeywordCreateRequestDto
  ): ResponseEntity<UUID> {
    return ResponseEntity(
      keywordService.createKeyword(keywordCreateRequestDto),
      HttpStatus.CREATED
    )
  }

  @Operation(summary = "키워드 수정", description = "키워드를 수정합니다.")
  @ApiResponse(responseCode = "200", description = "키워드 수정 성공")
  @ApiResponse(responseCode = "404", description = "존재하지 않는 키워드 조회")
  @PatchMapping("/{keywordId}")
  fun handleUpdateKeyword(
    @PathVariable keywordId: UUID,
    @RequestBody keywordUpdateRequestDto: KeywordUpdateRequestDto
  ): ResponseEntity<KeywordUpdateResponseDto> {
    return ResponseEntity(
      keywordService.updateKeyword(keywordId, keywordUpdateRequestDto),
      HttpStatus.OK
    )
  }

  @Operation(summary = "키워드 삭제", description = " 키워드 삭제.")
  @ApiResponse(responseCode = "200", description = "키워드 삭제 성공")
  @DeleteMapping("/{keywordId}")
  fun handleDeleteKeyword(
    @PathVariable keywordId: UUID
  ): ResponseEntity<Unit> {
    return ResponseEntity(
      keywordService.deleteKeyword(keywordId),
      HttpStatus.OK
    )
  }
}