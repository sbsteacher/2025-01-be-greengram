package com.green.greengram.entity;

import com.green.greengram.config.enumcode.model.EnumUserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class UserRoleIds implements Serializable {
    private Long userId;
    @Column(length = 2)
    private EnumUserRole roleCode;
}
