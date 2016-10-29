package com.cat.kit;

import lombok.Getter;

@Getter
public class Page {
    private final int pageNo;
    private final int pageSize;

    private Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public static Page of(int pageNo, int pageSize) {
        return new Page(pageNo, pageSize);
    }
}
