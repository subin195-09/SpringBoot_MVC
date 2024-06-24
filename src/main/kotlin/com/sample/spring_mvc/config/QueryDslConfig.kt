package com.sample.spring_mvc.config

/**
 * QueryDSL 설정 클래스
 *
 * - `jpaQueryFactory` 빈을 통해 `JPAQueryFactory` 인스턴스를 생성합니다.
 *    이를 통해 JPA 엔티티 매니저를 사용하여 QueryDSL을 활용할 수 있습니다.
 */
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDslConfig {

  @PersistenceContext
  lateinit var em: EntityManager

  @Bean
  fun jpaQueryFactory(): JPAQueryFactory {
    return JPAQueryFactory(em)
  }
}
