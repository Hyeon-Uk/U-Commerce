package com.example.commerce.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)//변화를 감지하는 리스너중, 엔티티 객체가 생성/변경되는 것을 감지
@Getter
@MappedSuperclass//이 어노테이션이 적용된 클래스는 테이블로 생성되지 않음,상속된 클래스만 테이블 생성
abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)//변경되는것을 막음
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}
