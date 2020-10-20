package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.exceptions.TaskListException;
import team.smartworld.academy.todolist.service.CheckParameterService;
import team.smartworld.academy.todolist.service.DatabaseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * Database Service object
     */
    private final DatabaseService dbService;

    /**
     * @param dbService DatabaseService object
     */
    @Autowired
    public TaskListController(DatabaseService dbService) {
        this.dbService = dbService;
    }

    /**
     * Method for deleting TaskList.
     *
     * @param idString is the id of the TaskList to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskList(@PathVariable("id") String idString) throws TaskListException {
        Long id = CheckParameterService.checkAndGetTaskListId(idString);
        dbService.deleteTaskList(id);
    }

    /**
     * Method for getting TaskList
     *
     * @param idString is the id for search TaskList
     * @return TaskList and status or error and status
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskList(@PathVariable("id") String idString) throws TaskListException {
        Long id = CheckParameterService.checkAndGetTaskListId(idString);
        TaskList taskList = dbService.getTaskList(id);
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    /**
     * Method for rename TaskList
     *
     * @param mapData  new name for TaskList
     * @param idString is the id for search TaskList
     * @return status or error and status
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> renameTaskList(@PathVariable("id") String idString,
                                            @RequestBody Map<String, String> mapData)
            throws TaskListException {
        Long id = CheckParameterService.checkAndGetTaskListId(idString);
        String name = CheckParameterService.checkAndGetName(mapData);
        TaskList taskList = dbService.getTaskList(id);
        taskList.setName(name);
        taskList.setDateChange(LocalDateTime.now());
        taskList = dbService.saveTaskList(taskList);
        return new ResponseEntity<>(taskList, HttpStatus.OK);

    }

    /**
     * Method for getting all TaskLists
     *
     * @param mapData Data sorted and filters
     * @return all TaskList and status or error and status
     */

    // Подумать... переделать.
    @GetMapping
    public ResponseEntity<?> getAllTaskLists(@RequestBody Map<String, String> mapData) throws TaskListException {
        // Проверки и прочее.
        // Пагинация
        long offset = 0L;
        if (mapData.containsKey("offset")) {
            offset = CheckParameterService.checkAndGetOffset(mapData);
        }
        int limit = 10;
        if (mapData.containsKey("limit")) {
            limit = CheckParameterService.checkAndGetLimit(mapData);
        }
        // сортировка
        boolean dateCreatedSort = false;
        if (mapData.containsKey("dateCreatedSort")) {
            dateCreatedSort = CheckParameterService.checkAndGetDateCreatedSort(mapData);
        }
        boolean dateChangeSort = false;
        if (mapData.containsKey("dateChangeSort")) {
            dateChangeSort = CheckParameterService.checkAndGetDateChangeSort(mapData);
        }
        boolean nameSort = false;
        if (mapData.containsKey("nameSort")) {
            nameSort = CheckParameterService.checkAndGetNameSort(mapData);
        }
        boolean isDoneSort = false;
        if (mapData.containsKey("isDoneSort")) {
            isDoneSort = CheckParameterService.checkAndGetIsDoneSort(mapData);
        }
        // фильтрация
        LocalDateTime dateCreatedFilter = null;
        if (mapData.containsKey("dateCreated")) {
            dateCreatedFilter = CheckParameterService.checkAndGetDateCreated(mapData);
        }
        LocalDateTime dateChangeFilter = null;
        if (mapData.containsKey("dateChange")) {
            dateChangeFilter = CheckParameterService.checkAndGetDateChange(mapData);
        }
        String nameFilter = null;
        if (mapData.containsKey("name")) {
            nameFilter = CheckParameterService.checkAndGetName(mapData);
        }
        Boolean isDoneFilter = null;
        if (mapData.containsKey("isDone")) {
            isDoneFilter = CheckParameterService.checkAndGetIsDone(mapData);
        }


        List<Map<String, String>> taskListDate = dbService.getAllTaskList(offset, limit,
                dateCreatedSort, dateChangeSort, nameSort, isDoneSort,
                dateCreatedFilter, dateChangeFilter, nameFilter, isDoneFilter);
        return new ResponseEntity<>(taskListDate, HttpStatus.OK);
    }

    /**
     * Method for create TaskList
     *
     * @param mapData new TaskList data
     * @return TaskList and status or error and status
     */
    @PostMapping
    public ResponseEntity<?> newTaskList(@RequestBody Map<String, String> mapData) throws TaskListException {
        String name = CheckParameterService.checkAndGetName(mapData);
        TaskList taskList = new TaskList();
        taskList.setName(name);
        taskList.setDateCreated(LocalDateTime.now());
        taskList.setDateChange(LocalDateTime.now());
        Map.Entry<String, Long> createdTaskListId = Map.entry("id", dbService.saveTaskList(taskList).getId());
        return new ResponseEntity<>(createdTaskListId, HttpStatus.CREATED);

    }

}
