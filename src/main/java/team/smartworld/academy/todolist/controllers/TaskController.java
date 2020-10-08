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
     * @return is 204 NO CONTENT or Error type end status
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
     * @return error status or marked done Task ID and status 204 NO CONTENT ???
     */

    // TODO метод запроса PUT или PATCH? Разобраться со статусами http ответа...
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
    // TODO найти больше инфы и доработать!!!
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
