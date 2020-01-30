
package com.example;

import static com.example.TimeOfDay.EVENING;
import static com.example.TimeOfDay.MORNING;
import static com.example.TimeOfDay.NIGHT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GreeterTest {

    private Greeter greeter;

    @Mock TimeOfDayService timeOfDayService;

    @Before
    public void setup() {
        greeter = new Greeter(timeOfDayService);
        doReturn(null).when(timeOfDayService).getTimeOfDay();
    }

    @Test
    public void testGreeter() {
        assertEquals("Hello Rob", greeter.greet("rob"));
    }

    @Test
    public void testGreeter_trimsInput() {
        assertEquals("Hello Rob", greeter.greet("  rob  "));
    }

    @Test
    public void testGreeter_upcaseInput() {
        assertEquals("Hello Rob", greeter.greet("rob"));
    }

    @Test
    public void testGreeter_isMorning() {
        doReturn(MORNING).when(timeOfDayService).getTimeOfDay();
        assertEquals("Good morning Rob", greeter.greet("rob"));
    }

    @Test
    public void testGreeter_isEvening() {
        doReturn(EVENING).when(timeOfDayService).getTimeOfDay();
        assertEquals("Good evening Rob", greeter.greet("rob"));
    }

    @Test
    public void testGreeter_isNight() {
        doReturn(NIGHT).when(timeOfDayService).getTimeOfDay();
        assertEquals("Good night Rob", greeter.greet("rob"));
    }
}
