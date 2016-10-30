package com.cat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Role {

    private Long id;

    private String name;

//    private LocalDateTime createTime = LocalDateTime.now();
//
//    private LocalDateTime updateTime = LocalDateTime.now();
}
