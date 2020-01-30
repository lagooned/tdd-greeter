
package com.example;

import static com.example.TimeOfDay.EVENING;
import static com.example.TimeOfDay.MORNING;
import static com.example.TimeOfDay.NIGHT;
import static com.example.TimeOfDayService.EVENING_END;
import static com.example.TimeOfDayService.EVENING_START;
import static com.example.TimeOfDayService.MORNING_END;
import static com.example.TimeOfDayService.MORNING_START;
import static com.example.TimeOfDayService.NIGHT_END;
import static com.example.TimeOfDayService.NIGHT_START;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimeOfDayServiceTest {

    private static final ZoneId DEFAULT_ZONE = ZoneOffset.UTC;

    private TimeOfDayService timeOfDayService;

    @Mock ClockProvider clockProvider;

    @Before
    public void setup() {
        timeOfDayService = new TimeOfDayService(clockProvider);
        doReturn(DEFAULT_ZONE).when(clockProvider).getClockTimeZone();
    }

    @Test
    public void testisInRange_insideRange() {
        Instant now = Instant.now();
        assertEquals(true, timeOfDayService.isInRange(now, now.minusSeconds(1), now.plusSeconds(1)));
    }

    @Test
    public void testisInRange_nowBeforeRange() {
        Instant now = Instant.now();
        assertEquals(false, timeOfDayService.isInRange(now, now.plusSeconds(1), now.plusSeconds(2)));
    }

    @Test
    public void testisInRange_nowAfterRange() {
        Instant now = Instant.now();
        assertEquals(false, timeOfDayService.isInRange(now, now.minusSeconds(2), now.minusSeconds(1)));
    }

    @Test
    public void testisInRange_onBeforeEdgeOfRange() {
        Instant now = Instant.now();
        assertEquals(true, timeOfDayService.isInRange(now, now, now.plusSeconds(1)));
    }

    @Test
    public void testisInRange_onAfterEdgeOfRange() {
        Instant now = Instant.now();
        assertEquals(true, timeOfDayService.isInRange(now, now.minusSeconds(1), now));
    }

    @Test
    public void testGetTimeOfDay_MorningBeginning() {
        Clock clock = getClockWithLocalTime(MORNING_START);
        doReturn(clock).when(clockProvider).getClock();
        assertEquals(MORNING, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_MorningMiddle() {
        doReturn(getClockWithLocalTime(MORNING_START.plusSeconds(1))).when(clockProvider).getClock();
        assertEquals(MORNING, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_MorningEnd() {
        doReturn(getClockWithLocalTime(MORNING_END)).when(clockProvider).getClock();
        assertEquals(MORNING, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_EveningBeginning() {
        doReturn(getClockWithLocalTime(EVENING_START)).when(clockProvider).getClock();
        assertEquals(EVENING, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_EveningMiddle() {
        doReturn(getClockWithLocalTime(EVENING_START.plusSeconds(1))).when(clockProvider).getClock();
        assertEquals(EVENING, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_EveningEnd() {
        doReturn(getClockWithLocalTime(EVENING_END)).when(clockProvider).getClock();
        assertEquals(EVENING, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_NightBeginning() {
        doReturn(getClockWithLocalTime(NIGHT_START)).when(clockProvider).getClock();
        assertEquals(NIGHT, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_NightMiddle() {
        doReturn(getClockWithLocalTime(NIGHT_START.plusMinutes(1))).when(clockProvider).getClock();
        assertEquals(NIGHT, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_NightDayBoundaryNight() {
        doReturn(getClockWithLocalTime(LocalTime.MAX)).when(clockProvider).getClock();
        assertEquals(NIGHT, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_NightDayBoundaryDay() {
        doReturn(getClockWithLocalTime(LocalTime.MIN)).when(clockProvider).getClock();
        assertEquals(NIGHT, timeOfDayService.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDay_NightEnd() {
        doReturn(getClockWithLocalTime(NIGHT_END)).when(clockProvider).getClock();
        assertEquals(NIGHT, timeOfDayService.getTimeOfDay());
    }

    private Clock getClockWithLocalTime(LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(DEFAULT_ZONE), localTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, DEFAULT_ZONE);
        Instant clockInstant = Instant.from(zonedDateTime);
        return Clock.fixed(clockInstant, DEFAULT_ZONE);
    }

}
