package team.smartworld.academy.todolist.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.smartworld.academy.todolist.models.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        return greeting;
    }
/*
    @PostMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "text", defaultValue = "World") String text) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, text));
        return greeting;
    }
*/
}
