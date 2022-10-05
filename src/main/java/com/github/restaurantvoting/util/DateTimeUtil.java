package com.github.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
@Slf4j
public final class DateTimeUtil {

    public static final LocalTime END_OF_VOTING_UPDATE = LocalTime.of(11, 0, 0, 0);

    private static final AtomicReference<Clock> CLOCK_REFERENCE = new AtomicReference<>(Clock.systemDefaultZone());

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static LocalDate dayOrMin(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE;
    }

    public static LocalDate dayOrMax(LocalDate localDate) {
        return localDate != null ? localDate : MAX_DATE;
    }

    @NonNull
    public static Clock getClock() {
        return CLOCK_REFERENCE.get();
    }

    /**
     * Atomically sets the value to {@code newClock} and returns the old value.
     *
     * @param newClock the new value
     * @return the previous value of clock
     */
    @NonNull
    public static Clock setClock(@NonNull final Clock newClock) {
        Objects.requireNonNull(newClock, "newClock cannot be null");
        final Clock oldClock = CLOCK_REFERENCE.getAndSet(newClock);
        log.info("Set new clock {}. Old clock is {}", newClock, oldClock);
        return oldClock;
    }
}