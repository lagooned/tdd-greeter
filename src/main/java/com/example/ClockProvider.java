
package com.example;

import java.time.Clock;
import java.time.ZoneId;

public class ClockProvider {

    public ZoneId getClockTimeZone() {
        return ZoneId.systemDefault();
    }

    public Clock getClock() {
        return Clock.system(getClockTimeZone());
    }

}
