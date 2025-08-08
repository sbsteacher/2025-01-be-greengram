package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Feed extends UpdatedAt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "writer_user_id")
    private User writerUser;

    @Column(length = 30)
    private String location;

    @Column(length = 1_000)
    private String contents;
}
