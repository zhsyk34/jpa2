package com.cat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private Set<Build> builds;
}
