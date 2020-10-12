package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.models.Task;

/**
 * Task controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/todo/api/task")
@Api(value = "Task Controller", consumes = "json", produces = "json")
public class TaskController {

    /**
     * Method for deleting Task in TodoList.
     *
     * @param id is the id of the Task to delete in DB
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("id") Long id) {
        // добавить проверки ID
        //  repository.deleteById(id);
    }

    /**
     * Method for mark done in Task.
     *
     * @param id is the id of the Task to mark done in DB
     *           (uri type mark_done_task/?id={number}, json body is empty)
     * @return status or error type end status
     */

    // Разобраться со статусами http ответов для всех методов...
    @PatchMapping("/{id}")
    public ResponseEntity<?> markDoneTask(@PathVariable("id") Long id) {
//        try {
//                поиск в базе
//        }
//        catch() { return ошибка не число и статус http}
//        catch() { return ошибка не найдено в базе и статус http}
//        установка isDone(true);
        return new ResponseEntity<>(HttpStatus.OK); //
    }

//    public ResponseEntity<?> markDoneTask(@RequestBody Map<String, String> map) {

    /**
     * Method for change in Task.
     *
     * @param id         is the id of the Task to change in DB
     * @param changeTask changed Task date
     * @return status or error type end status
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> changeTask(@PathVariable("id") Long id,
                                        @RequestBody Task changeTask) {
        // Проверки
        // изменение
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for create Task
     *
     * @param newTask new Task Date
     * @return Task and status or error and status
     */
    @PostMapping
    public ResponseEntity<?> newTask(@RequestBody Task newTask) {
        // Проверки
        // создание и сохранение в БД
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method for getting Task
     *
     * @param id is the id for search Task in DB
     * @return Task and status or error and status
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable("id") Long id) {
        // Проверить id
        // Найти в БД
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
