package com.cat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60/*, nullable = false*/)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updateTime = LocalDateTime.now();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", nullable = false, foreignKey = @ForeignKey(name = "community_build"))
    @JsonIgnore
    private Community community;
}
