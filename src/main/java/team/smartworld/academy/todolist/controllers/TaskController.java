package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.Task;

/**
 * Task controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/todo/api/taskList")
@Api(value = "Task Controller", consumes = "json", produces = "json")
public class TaskController {

    /**
     *
     */
    //private final TaskListRepository repository;

    /**
     * @param repository TaskList repository
     */
//    @Autowired
//    public TaskController(TaskListRepository repository) {
//        this.repository = repository;
//    }

    /**
     * Method for deleting Task in TodoList.
     *
     * @param id is the id of the Task to delete in DB
     */
    @DeleteMapping("/{taskListId}/task/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("taskListId") Long taskListId,
                           @PathVariable("id") Long id) {
        // добавить проверки ID
        //repository.deleteById(id);
    }

    /**
     * Method for mark done in Task.
     *
     * @param id is the id of the Task to mark done in DB
     *           (uri type mark_done_task/?id={number}, json body is empty)
     * @return status or error type end status
     */

    // Разобраться со статусами http ответов для всех методов...
    @PatchMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> markDoneTask(@PathVariable("taskListId") Long taskListId,
                                          @PathVariable("id") Long id) {
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
    @PutMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> changeTask(@PathVariable("taskListId") Long taskListId,
                                        @PathVariable("id") Long id,
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
    @PostMapping("/{taskListId}/task")
    public ResponseEntity<?> newTask(@PathVariable("taskListId") Long taskListId,
                                     @RequestBody Task newTask) {
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
    @GetMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> getTask(@PathVariable("taskListId") Long taskListId,
                                     @PathVariable("id") Long id) {
        // Проверить id
        // Найти в БД
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
