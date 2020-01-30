
package com.example;

import static com.example.TimeOfDay.EVENING;
import static com.example.TimeOfDay.MORNING;
import static com.example.TimeOfDay.NIGHT;

public class Greeter {

    private final TimeOfDayService timeOfDayService;

    public Greeter(TimeOfDayService timeOfDayService) {
        this.timeOfDayService = timeOfDayService;
    }

    public String greet(String greetee) {

        String trimmedGreetee = greetee.trim();

        TimeOfDay timeOfDay = timeOfDayService.getTimeOfDay();

        if (MORNING == timeOfDay)
            return "Good morning " + upcase(trimmedGreetee);
        else if (EVENING == timeOfDay)
            return "Good evening " + upcase(trimmedGreetee);
        else if (NIGHT == timeOfDay)
            return "Good night " + upcase(trimmedGreetee);
        else
            return "Hello " + upcase(trimmedGreetee);

    }

    private String upcase(String name) {
        return name.substring(0, 1).toUpperCase()
            + name.substring(1, name.length());
    }

}
