package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.repository.TaskListRepository;

import java.time.LocalDateTime;
import java.util.*;

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
        Optional<TaskList> oTaskList = repository.findById(id);
        if (oTaskList.isPresent()) {
            return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
            taskList.setName(newName.get("name").replaceAll("[^A-Za-zА-Яа-я0-9 ]", ""));
            taskList.setDateChange(LocalDateTime.now());
            return new ResponseEntity<>(repository.save(taskList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Method for getting all TaskLists
     *
     * @param params params sorted and filters
     * @return all TaskList and status or error and status
     */

    // Подумать... переделать.
    @GetMapping
    public ResponseEntity<?> getAllTaskLists(@RequestBody Map<String, String> params) {
        // Проверки и прочее.
        long startId = 1L;
        if (params.containsKey("startId")) {
            long parseStartId = Long.parseLong(params.get("startId"));
            if (parseStartId > 0L) {
                startId = parseStartId;
            }
        }
        int limit = 10;
        if (params.containsKey("limit")) {
            int parseLimit = Integer.parseInt(params.get("limit"));
            if (parseLimit > 0 && parseLimit < 100) {
                limit = parseLimit;
            }
        }

        Iterable<TaskList> oTaskList = repository.findAll();
        Iterator<TaskList> listIterator = oTaskList.iterator();
        List<Map<String, String>> taskListDate = new ArrayList<>();

        while (listIterator.hasNext() && limit > 0) {
            TaskList taskList = listIterator.next();
            if (taskList.getId() >= startId) {
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("id", Long.toString(taskList.getId()));
                dataMap.put("name", taskList.getName());
                dataMap.put("dateCreated", taskList.getDateCreated().toString());
                dataMap.put("dateChange", taskList.getDateChange().toString());
                dataMap.put("isDone", Boolean.toString(taskList.isDone()));
                taskListDate.add(dataMap);
                limit--;
            }
        }
        return new ResponseEntity<>(taskListDate, HttpStatus.OK);
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
        if (newNameTaskList.containsKey("name")) {
            TaskList taskList = new TaskList();
            taskList.setName(newNameTaskList.get("name").replaceAll("[^A-Za-zА-Яа-я0-9]", ""));
            taskList.setDateCreated(LocalDateTime.now());
            taskList.setDateChange(LocalDateTime.now());
            String createdTaskListId = "{ \"id\": \"" + repository.save(taskList).getId() + "\" }";
            return new ResponseEntity<>(createdTaskListId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
