package com.banking.accounts.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// We can skip the name parameter in the column if the columnname matches with the fieldname;

@MappedSuperclass
@Getter @Setter @ToString
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false, name = "createdBy")
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}
