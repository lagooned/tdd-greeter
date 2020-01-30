
package com.example;

import static com.example.TimeOfDay.EVENING;
import static com.example.TimeOfDay.MORNING;
import static com.example.TimeOfDay.NIGHT;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class TimeOfDayService {

    static final LocalTime MORNING_START = LocalTime.of(6, 0);
    static final LocalTime MORNING_END = LocalTime.of(12, 0);
    static final LocalTime EVENING_START = LocalTime.of(18, 0);
    static final LocalTime EVENING_END = LocalTime.of(21, 59, 59, 999_999_999);
    static final LocalTime NIGHT_START = LocalTime.of(22, 0);
    static final LocalTime NIGHT_END = LocalTime.of(5, 59, 59, 999_999_999);

    private final ClockProvider clockProvider;

    public TimeOfDayService(ClockProvider clockProvider) {
        this.clockProvider = clockProvider;
    }

    public TimeOfDay getTimeOfDay() {
        if (isNowInRange(MORNING_START, MORNING_END))
            return MORNING;
        else if (isNowInRange(EVENING_START, EVENING_END))
            return EVENING;
        else if (isNowInRange(NIGHT_START, LocalTime.MAX)
                 || isNowInRange(LocalTime.MIN, NIGHT_END))
            return NIGHT;
        else
            return null;
    }

    boolean isNowInRange(final LocalTime a, final LocalTime b) {
        return isInRange(
            Instant.now(clockProvider.getClock()),
            Instant.from(
                ZonedDateTime.of(
                    LocalDate.now(
                        clockProvider.getClock()), a, clockProvider.getClockTimeZone())),
            Instant.from(
                ZonedDateTime.of(
                    LocalDate.now(
                        clockProvider.getClock()), b, clockProvider.getClockTimeZone())));
    }

    boolean isInRange(final Instant a, final Instant b, final Instant c) {
        return (a.isAfter(b) || a.equals(b)) && (a.isBefore(c) || a.equals(c));
    }

}
