package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.repository.TaskListRepository;

import java.util.Map;
import java.util.Optional;

/**
 * Task List controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/todo/api/taskList")
@Api(value = "Task List Controller", consumes = "json", produces = "json")
public class TaskListController {

    /**
     *
     */
    private final TaskListRepository repository;

    /**
     * @param repository TaskList repository
     */
    @Autowired
    public TaskListController(TaskListRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for deleting TaskList.
     *
     * @param id is the id of the TaskList to delete in DB
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskList(@PathVariable("id") Long id) {
        //добавить проверки ID
          repository.deleteById(id);
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
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
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
        Optional<TaskList> list = repository.findById(id);
        if (list.isPresent()) {
            TaskList taskList = list.get();
            taskList.setName(newName.get("name"));
            return new ResponseEntity<>(repository.save(taskList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        TaskList list = new TaskList();
        list.setName(newNameTaskList.get("name"));
        // Больше данных !!!
        return new ResponseEntity<>(repository.save(list), HttpStatus.CREATED);
    }

    /**
     * Method for getting all TaskLists
     *
     * @param sortAndFilterParams params sorted and filters
     * @return all TaskList and status or error and status
     */

    // Подумать... переделать.
    @GetMapping
    public ResponseEntity<?> getAllTaskLists(@RequestBody Map<String, String> sortAndFilterParams) {
        // Проверки и прочее.
        Long startId = 1L;
        startId = Long.parseLong(sortAndFilterParams.get("startId"));
        int limit = Integer.parseInt(sortAndFilterParams.get("limit"));
        if (limit > 100 || limit < 1) {
            limit = 10;
        }
        // Реализовать!
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
