package com.bankoperations.test.dto.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageDto<D> {

    private List<D> objects;

    private int totalPages;

    private long totalElements;

}
