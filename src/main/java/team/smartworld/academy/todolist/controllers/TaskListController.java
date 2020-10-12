package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Task List controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/todo/api/todolist")
@Api(value = "Task List Controller", consumes = "json", produces = "json")
public class TaskListController {

    /**
     * Method for deleting TaskList.
     *
     * @param id is the id of the TaskList to delete in DB
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskList(@PathVariable("id") Long id) {
        //добавить проверки ID
        //  repository.deleteById(id);
    }

    /**
     * Method for getting TaskList
     *
     * @param id is the id for search TaskList in DB
     * @return TaskList and status or error and status
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskList(@PathVariable("id") Long id) {
        // Проверки
        // Поиск в БД
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for rename TaskList
     *
     * @param newName new name for TaskList
     * @param id      is the id for search TaskList in DB
     * @return status or error and status
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> renameTaskList(@PathVariable("id") Long id,
                                            @RequestBody Map<String, String> newName) {
        // Проверки
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for create TaskList
     *
     * @param newNameTaskList new TaskList date
     * @return TaskList and status or error and status
     */
    @PostMapping
    public ResponseEntity<?> newTaskList(@RequestBody Map<String, String> newNameTaskList) {
        // Проверки
        // Создание и сохранение в БД
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method for getting all TaskLists
     *
     * @param startId             start id search
     * @param limit               limit getting TaskList
     * @param sortAndFilterParams params sorted and filters
     * @return all TaskList and status or error and status
     */

    // Подумать... переделать.
    @GetMapping("all")
    public ResponseEntity<?> getAllTaskLists(@RequestParam(value = "startId", defaultValue = "1") String startId,
                                             @RequestParam(value = "limit", defaultValue = "10") String limit,
                                             @RequestBody Map<String, String> sortAndFilterParams) {
        // Проверки и прочее.
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
