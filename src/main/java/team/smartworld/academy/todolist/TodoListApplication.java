package team.smartworld.academy.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class TodoList Application
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@SpringBootApplication
public class TodoListApplication {

    /**
     * Main method from start server
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }
}
