package com.green.greengram.entity;

import com.green.greengram.configuration.enumcode.model.EnumUserRole;
import com.green.greengram.configuration.security.SignInProviderType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = { "uid", "provider_type" })
)
public class User extends UpdatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100)
    private String accessToken;

    @Column(nullable = false, length = 2)
    private SignInProviderType providerType;

    @Column(nullable = false, length = 50)
    private String uid;

    @Column(nullable = false, length = 100)
    private String upw;

    @Column(length = 30)
    private String nickName; //nick_name

    @Column(length = 100)
    private String pic;

    //cascade는 자식과 나랑 모든 연결 (내가 영속성되면 자식도 영속성되고, 내가 삭제되면 자식도 삭제 된다. 등등)
    //ohphanRemoval은 userRoles에서 자식을 하나 제거함. 그러면 DB에도 뺀 자식은 삭제처리가 된다.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>(1);

    public void addUserRoles(List<EnumUserRole> enumUserRole) {
        for(EnumUserRole e : enumUserRole) {
            UserRoleIds ids = new UserRoleIds(this.userId, e);
            UserRole userRole = new UserRole(ids, this);

            this.userRoles.add(userRole);
        }
    }
}
