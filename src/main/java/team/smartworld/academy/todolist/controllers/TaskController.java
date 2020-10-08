package team.smartworld.academy.todolist.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class TaskController {

    /**
     * Method for deleting Task in TodoList.
     *
     * @param id is the id of the Task to delete in DB
     * @return status or error type end status
     */
    @RequestMapping(value = "/delete_task/",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exception> deleteTask(@RequestParam String id) {
        // добавить проверки ID
        //  repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method for mark done in Task.
     *
     * @param id is the id of the Task to mark done in DB
     *           (uri type mark_done_task/?id={number}, json body is empty)
     * @return status or error type end status
     */

    // TODO метод запроса PUT или PATCH? Разобраться со статусами http ответов для всех  методов...
    @RequestMapping(value = "/mark_done_task/",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exception> markDoneTask(@RequestParam String id) {
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //
    }

//    public ResponseEntity<?> markDoneTask(@RequestBody Map<String, String> map) {

    /**
     * Method for change in Task.
     *
     * @param id         is the id of the Task to change in DB
     * @param changeTask changed Task date
     * @return status or error type end status
     */
    @RequestMapping(value = "/change_task/",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeTask(@PathVariable String id, @RequestBody Task changeTask) {
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
    @RequestMapping(value = "/new_task/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newTask(@RequestBody Task newTask) {
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
    @RequestMapping(value = "/get_task/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTask(@RequestParam String id) {
        // Проверить id
        // Найти в БД
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
