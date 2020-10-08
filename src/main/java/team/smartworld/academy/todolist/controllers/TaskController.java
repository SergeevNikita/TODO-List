package team.smartworld.academy.todolist.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Task controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/todo/api/v1.0/task")
public class TaskController {

    /**
     * Method for deleting Task in TodoList.
     *
     * @param id is the id of the Task to delete in DB
     * @return status or error type end status
     */
    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam String id) {
        // добавить проверки ID
        //  repository.deleteById(id);
        // return new ResponseEntity<>("DeleteTask", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for mark done in Task.
     *
     * @param id is the id of the Task to mark done in DB
     *           (uri type mark_done_task/?id={number}, json body is empty)
     * @return status or error type end status
     */

    // Разобраться со статусами http ответов для всех методов...
    @PatchMapping
    public ResponseEntity<?> markDoneTask(@RequestParam String id) {
        // добавить проверки ID
//        Long idL;
//        try {
//
//                id = Long.getLong(map.get("id"));
//                поиск в базе
//        }
//        catch() { return ошибка не число и статус http}
//        catch() { return ошибка не найдено в базе и статус http}
//        установка isComplete(true);
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
    @PutMapping
    public ResponseEntity<?> changeTask(@RequestParam String id,
                                        @RequestBody Map<String, String> changeTask) {
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
    public ResponseEntity<?> newTask(@RequestBody Map<String, String> newTask) {
        // Проверки
        // создание и сохранение в БД
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for getting Task
     *
     * @param id is the id for search Task in DB
     * @return Task and status or error and status
     */
    @GetMapping
    public ResponseEntity<?> getTask(@RequestParam String id) {
        // Проверить id
        // Найти в БД
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
