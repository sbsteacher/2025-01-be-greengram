package com.green.greengram.entity;

import com.green.greengram.configuration.enumcode.model.EnumUserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class UserRoleIds implements Serializable {
    private Long userId;
    @Column(length = 2)
    private EnumUserRole roleCode;
}
