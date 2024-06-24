package com.sample.spring_mvc.repository

import com.sample.spring_mvc.domain.entity.Keyword

interface KeywordSupport {

  fun findAllKeywordWithPagination(
    limit: Long,
    offset: Long,
  ): List<Keyword>
}