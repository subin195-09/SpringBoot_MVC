package com.sample.spring_mvc.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sample.spring_mvc.domain.entity.Keyword
import com.sample.spring_mvc.domain.entity.QKeyword.keyword
import org.springframework.stereotype.Repository

@Repository
class KeywordSupportImpl(
  private val jpaQueryFactory: JPAQueryFactory,
) : KeywordSupport {
  override fun findAllKeywordWithPagination(limit: Long, offset: Long): List<Keyword> {
    return jpaQueryFactory.selectFrom(keyword)
      .offset(offset).limit(limit)
      .fetch()
  }
}