package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.Task;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.exceptions.TaskListException;
import team.smartworld.academy.todolist.service.CheckParameterService;
import team.smartworld.academy.todolist.service.DatabaseService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Task controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/todo/api/taskList")
@Api(value = "Task Controller", consumes = "application/json", produces = "application/json")
public class TaskController {

    /**
     * Database Service object
     */
    private final DatabaseService dbService;

    /**
     * Constructor
     *
     * @param dbService service for working with a database
     */
    @Autowired
    public TaskController(DatabaseService dbService) {
        this.dbService = dbService;
    }

    /**
     * Method for deleting Task in TodoList.
     *
     * @param taskListIdString TaskList id
     * @param idString         is the id of the Task to delete in DB
     */
    @ApiOperation(value = "Deleting Task",
            notes = "Deleting Task by taskListId and id")
    @DeleteMapping("/{taskListId}/task/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListId") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("id") String idString)
            throws TaskListException {
        UUID taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        UUID id = CheckParameterService.checkAndGetTaskId(idString);
        Task task = dbService.getTask(taskListId, id);
        dbService.deleteTask(task);
    }

    /**
     * Method for mark done in Task.
     *
     * @param taskListIdString TaskList id
     * @param idString         is the id of the Task to mark done in DB
     * @return status or error type end status
     */

    @ApiOperation(value = "Mark is done Task",
            notes = "Marking is done Task by taskListId and id")
    @PatchMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> markDoneTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListId") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("id") String idString,
            @ApiParam(value = "Json isDone data", required = true, example = "{\"isDone\":\"true\"}")
            @RequestBody Map<String, String> mapData)
            throws TaskListException {

        UUID taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        UUID id = CheckParameterService.checkAndGetTaskId(idString);
        boolean isDone = CheckParameterService.checkAndGetIsDone(mapData);
        Task task = dbService.getTask(taskListId, id);
        task.setDone(isDone);
        task = dbService.saveTask(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    /**
     * Method for change in Task.
     *
     * @param taskListIdString TaskList id
     * @param idString         is the id of the Task to change in DB
     * @param mapData          changed Task date
     * @return status or error type end status
     */

    @ApiOperation(value = "Change Task",
            notes = "Change Task by taskListId and id")
    @PutMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> changeTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListId") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("id") String idString,
            @ApiParam(value = "Json isDone data", required = true, example = "{\"isDone\":\"true\"}")
            @RequestBody Map<String, String> mapData)
            throws TaskListException {
        UUID taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        UUID id = CheckParameterService.checkAndGetTaskId(idString);
        Task task = dbService.getTask(taskListId, id);
        if (mapData.containsKey("name")) {
            task.setName(CheckParameterService.checkAndGetName(mapData));
        }
        if (mapData.containsKey("title")) {
            task.setTitle(CheckParameterService.checkAndGetTitle(mapData));
        }
        if (mapData.containsKey("isDone")) {
            task.setDone(CheckParameterService.checkAndGetIsDone(mapData));
        }
        if (mapData.containsKey("priority")) {
            task.setPriority(CheckParameterService.checkAndGetPriority(mapData));
        }
        task = dbService.saveTask(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Method for create Task
     *
     * @param mapData new Task Data
     * @return Task and status or error and status
     */
    @ApiOperation(value = "Create Task",
            notes = "Creating Task by taskListId and id")
    @PostMapping("/{taskListId}/task")
    public ResponseEntity<?> newTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListId") String taskListIdString,
            @ApiParam(value = "Json isDone data", required = true, example = "{\"isDone\":\"true\"}")
            @RequestBody Map<String, String> mapData) throws TaskListException {
        UUID taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        String name = CheckParameterService.checkAndGetName(mapData);
        String title = CheckParameterService.checkAndGetTitle(mapData);
        byte priority = CheckParameterService.checkAndGetPriority(mapData);
        TaskList taskList = dbService.getTaskList(taskListId);

        Task task = new Task();
        task.setTaskList(taskList);
        task.setName(name);
        task.setTitle(title);
        task.setPriority(priority);
        task.setDateCreated(LocalDateTime.now());
        task.setDateChange(LocalDateTime.now());

        Map.Entry<String, UUID> createdTaskId = Map.entry("id", dbService.saveTask(task).getId());
        return new ResponseEntity<>(createdTaskId, HttpStatus.CREATED);

    }

    /**
     * Method for getting Task
     *
     * @param taskListIdString TaskList id
     * @param idString         is the id for search Task in DB
     * @return Task and status or error and status
     */
    @ApiOperation(value = "Get Task",
            notes = "Getting Task by taskListId and id")
    @GetMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> getTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListId") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("id") String idString)
            throws TaskListException {

        UUID taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        UUID id = CheckParameterService.checkAndGetTaskId(idString);
        Task task = dbService.getTask(taskListId, id);
//        Task task = dbService.getTaskNew(taskListId, id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
