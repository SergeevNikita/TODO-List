package team.smartworld.academy.todolist.controllers;

import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.models.Task;

/**
 * Task controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@RestController
public class TaskController {

    /**
     * Method for deleting Task in TodoList.
     *
     * @param id is the id of the Task to delete in DB
     */
    @DeleteMapping("/delete_deed/{id}")
    public void deleteTask(@PathVariable String id) {
        // добавить проверки ID
        //  repository.deleteById(id);
    }


    /**
     * Method for mark done in Task.
     *
     * @param id is the id of the Task to mark done in DB
     * @return mark done Task
     */
    @PutMapping("/mark_done_task/{id}")
    public Task markDoneTask(@PathVariable String id) {
        // добавить проверки ID
        return null;
    }

    /**
     * Method for change in Task.
     *
     * @param changeTask changed Task date
     * @param id         is the id of the Task to change in DB
     * @return Task
     */
    // TODO найти больше инфы и доработать!!!
    @PutMapping("/change_task/{id}")
    public Task changeTask(@RequestBody Task changeTask, @PathVariable String id) {
        return null;
    }

    /**
     * Method for create Task
     *
     * @param newTask new Task Date
     * @return Task
     */
    @PostMapping("/new_task")
    public Task newTask(@RequestBody Task newTask) {
        return null;
    }

    /**
     * Method for getting Task
     *
     * @param id is the id for search Task in DB
     * @return Task
     */
    @GetMapping("/get_task/{id}")
    public Task getTask(@PathVariable String id) {
        return null;
    }
}
