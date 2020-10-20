package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
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
     * DatabaseService
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
    @DeleteMapping("/{taskListId}/task/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("taskListId") String taskListIdString,
                           @PathVariable("id") String idString)
            throws TaskListException {
        Long taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        Long id = CheckParameterService.checkAndGetTaskId(idString);
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


    @PatchMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> markDoneTask(@PathVariable("taskListId") String taskListIdString,
                                          @PathVariable("id") String idString,
                                          @RequestBody Map<String, String> mapData)
            throws TaskListException {

        Long taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        Long id = CheckParameterService.checkAndGetTaskId(idString);
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

    // Переделать всё
    @PutMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> changeTask(@PathVariable("taskListId") String taskListIdString,
                                        @PathVariable("id") String idString,
                                        @RequestBody Map<String, String> mapData)
            throws TaskListException {
        Long taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        Long id = CheckParameterService.checkAndGetTaskId(idString);
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
    @PostMapping("/{taskListId}/task")
    public ResponseEntity<?> newTask(@PathVariable("taskListId") String taskListIdString,
                                     @RequestBody Map<String, String> mapData) throws TaskListException {
        Long taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
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

        Map.Entry<String, Long> createdTaskId = Map.entry("id", dbService.saveTask(task).getId());
        return new ResponseEntity<>(createdTaskId, HttpStatus.CREATED);

    }

    /**
     * Method for getting Task
     *
     * @param taskListIdString TaskList id
     * @param idString         is the id for search Task in DB
     * @return Task and status or error and status
     */
    @GetMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> getTask(@PathVariable("taskListId") String taskListIdString,
                                     @PathVariable("id") String idString)
            throws TaskListException {

        Long taskListId = CheckParameterService.checkAndGetTaskListId(taskListIdString);
        Long id = CheckParameterService.checkAndGetTaskId(idString);
        Task task = dbService.getTask(taskListId, id);
//        Task task = dbService.getTaskNew(taskListId, id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
