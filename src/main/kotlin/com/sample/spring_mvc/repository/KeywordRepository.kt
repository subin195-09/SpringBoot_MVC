package com.sample.spring_mvc.repository

import com.sample.spring_mvc.domain.entity.Keyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface KeywordRepository : JpaRepository<Keyword, UUID>, KeywordSupport {
}