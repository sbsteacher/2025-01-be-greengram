package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass //이 클래스를 상속하면 createdAt 컬럼을 가지게 된다. 상속받을 수 있도록 하는 애노테이션
@EntityListeners(AuditingEntityListener.class) //이벤트 연결, insert가 될 때 현재일시값 넣을 수 있도록 감시한다.
public class UpdatedAt extends CreatedAt {
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
