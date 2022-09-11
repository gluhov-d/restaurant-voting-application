package com.github.restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class DateTimeUtil {

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static LocalDate dayOrMin(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE;
    }

    public static LocalDate dayOrMax(LocalDate localDate) {
        return localDate != null ? localDate : MAX_DATE;
    }
}