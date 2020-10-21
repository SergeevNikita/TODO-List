package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.UUID;

/**
 * Task List controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/todo/api/taskList")
@Api(value = "Task List Controller", consumes = "application/json", produces = "application/json")
public class TaskListController {

    /**
     * Database Service object
     */
    private final DatabaseService dbService;

    /**
     * Constructor
     *
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
    @ApiOperation(value = "Deleting Task List",
            notes = "Deleting Task List by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskList(
            @ApiParam(value = "Id task", required = true)
            @PathVariable("id") String idString) throws TaskListException {
        UUID id = CheckParameterService.checkAndGetTaskListId(idString);
        dbService.deleteTaskList(id);
    }

    /**
     * Method for getting TaskList
     *
     * @param idString is the id for search TaskList
     * @return TaskList and status or error and status
     */
    @ApiOperation(value = "Get Task List",
            notes = "Getting Task List by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskList(
            @ApiParam(value = "Id task", required = true)
            @PathVariable("id") String idString) throws TaskListException {
        UUID id = CheckParameterService.checkAndGetTaskListId(idString);
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
    @ApiOperation(value = "Rename Task List",
            notes = "Rename Task List by id")
    public ResponseEntity<?> renameTaskList(
            @ApiParam(value = "Id task", required = true)
            @PathVariable("id") String idString,
            @ApiParam(value = "Json name data", required = true, example = "{\n\t\"name\":\"name task list\"\n}")
            @RequestBody Map<String, String> mapData)
            throws TaskListException {
        UUID id = CheckParameterService.checkAndGetTaskListId(idString);
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

    @GetMapping
    @ApiOperation(value = "Get all Task List",
            notes = "Getting all Task List")
    public ResponseEntity<?> getAllTaskLists(
            @ApiParam(value = "Json pagination, sorting and filtering data", required = true)
            @RequestBody Map<String, String> mapData) throws TaskListException {
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
    @ApiOperation(value = "Create Task List",
            notes = "Creating Task List")
    public ResponseEntity<?> newTaskList(
            @ApiParam(value = "Json name data", required = true, example = "{\n\t\"name\":\"name task list\"\n}")
            @RequestBody Map<String, String> mapData) throws TaskListException {
        String name = CheckParameterService.checkAndGetName(mapData);
        TaskList taskList = new TaskList();
        taskList.setName(name);
        taskList.setDateCreated(LocalDateTime.now());
        taskList.setDateChange(LocalDateTime.now());
        Map.Entry<String, UUID> createdTaskListId = Map.entry("id", dbService.saveTaskList(taskList).getId());
        return new ResponseEntity<>(createdTaskListId, HttpStatus.CREATED);

    }

}
