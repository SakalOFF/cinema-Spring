package com.company.cinema.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Getter
@AllArgsConstructor
enum Sorting{
    BY_START_TIME(Sort.by(Sort.Direction.ASC, "startTime"), "start_time"),
    BY_TITLE(Sort.by(Sort.Direction.ASC, "film_title"), "title"),
    BY_SEATS_COUNT(Sort.by(Sort.Direction.ASC, "seatsCounter"), "seats_count");

    public static final Sorting DEFAULT_SORTING = BY_START_TIME;

    private final Sort sort;
    private final String value;

    public static Sort getSorting(String sorting_value){
        if (sorting_value == null || "".equals(sorting_value)){
            return Sorting.DEFAULT_SORTING.getSort();
        }
        return Arrays.stream(Sorting.values())
                .filter((s) -> s.getValue().equals(sorting_value))
                .findFirst()
                .orElse(Sorting.DEFAULT_SORTING)
                .getSort();
    }
}
