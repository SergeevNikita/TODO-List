package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.Task;
import team.smartworld.academy.todolist.exceptions.*;
import team.smartworld.academy.todolist.service.*;

import java.util.*;

/**
 * Task controller - класс обработки REST запросов пользователя
 * Позволяет создавать, изменять, удалять, и получать обьект Task.
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/todo/api/taskList")
@Api(value = "Task Controller", consumes = "application/json", produces = "application/json")
public class TaskController {

    /**
     * Обьекты сервисов работы с базой данных
     */
    private final TaskDatabaseService dbServiceTask;

    private final TaskListDatabaseService dbServiceTaskList;

    /**
     * Конструктор
     *
     * @param dbServiceTask     принемает обьект сервиса работы с базой данных Task
     * @param dbServiceTaskList принемает обьект сервиса работы с базой данных TaskList
     */
    @Autowired
    public TaskController(TaskDatabaseService dbServiceTask, TaskListDatabaseService dbServiceTaskList) {
        this.dbServiceTask = dbServiceTask;
        this.dbServiceTaskList = dbServiceTaskList;
    }

    /**
     * Метод для удаления обьекта Task в TaskLis по ID.
     *
     * @param taskListIdString принемает ID обьекта TaskList
     * @param taskIdString     принемает ID обьекта Task
     */
    @ApiOperation(value = "Deleting Task",
            notes = "Deleting Task by taskListId and id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Database not available")
    })
    @DeleteMapping("/{taskListIdString}/task/{taskIdString}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("taskIdString") String taskIdString
    )
            throws TaskListException {
        UUID taskListId = ParseParameterService.parseTaskListId(taskListIdString);
        UUID taskId = ParseParameterService.parseTaskId(taskIdString);
        dbServiceTask.deleteTask(taskListId, taskId);
    }

    /**
     * Метод для изменения поля 'done' обькта Task по ID
     *
     * @param taskListIdString принемает ID обьекта TaskList
     * @param taskIdString     принемает ID обьекта Task
     * @param mapData          получает значение в поле 'done' в формате json
     * @return возвращает изменённый обьект Task
     */
    @ApiOperation(value = "Mark is done Task",
            notes = "Marking is done Task by taskListId and id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Database not available")
    })
    @PatchMapping("/{taskListIdString}/task/{taskIdString}")
    public ResponseEntity<?> markDoneTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("taskIdString") String taskIdString,
            @ApiParam(value = "Json done data", required = true, example = "{\n\t\"done\":\"true\"\n}")
            @RequestBody Map<String, String> mapData
    )
            throws TaskListException {

        UUID taskListId = ParseParameterService.parseTaskListId(taskListIdString);
        UUID id = ParseParameterService.parseTaskId(taskIdString);
        if (!mapData.containsKey("done")
                || mapData.get("done").isEmpty()) {
            throw new BadParameterException(BadParameterException.ExceptionType.DONE);
        }
        Boolean done = ParseParameterService.isDone(mapData.get("done"));
        Task task = dbServiceTask.updateTask(taskListId, id, null, null, done, null);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Метод для изменения обьекта Task по ID
     *
     * @param taskListIdString принемает ID обьекта TaskList
     * @param taskIdString     принемает ID обьекта Task
     * @param mapData          получает значения в полях 'name', 'title','priority' и 'done' в формате json
     * @return возвращает изменённый обьект Task
     */
    @ApiOperation(value = "Change Task",
            notes = "Change Task by taskListId and id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Database not available")
    })
    @PutMapping("/{taskListIdString}/task/{taskIdString}")
    public ResponseEntity<?> changeTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("taskIdString") String taskIdString,
            @ApiParam(value = "Json done data", required = true,
                    example = "{\n\t\"done\":\"true\"," +
                            "\n\t\"name\":\"name task\"," +
                            "\n\t\"title\":\"eny title\"," +
                            "\n\t\"priority\":\"3\"\n}")
            @RequestBody Map<String, String> mapData
    )
            throws TaskListException {
        UUID taskListId = ParseParameterService.parseTaskListId(taskListIdString);
        UUID taskId = ParseParameterService.parseTaskId(taskIdString);
        String name = null;
        if (mapData.containsKey("name") && !mapData.get("name").isEmpty()) {
            name = ParseParameterService.parseName(mapData.get("name"));
        }
        String title = null;
        if (mapData.containsKey("title")) {
            title = ParseParameterService.parseTitle(mapData.get("title"));
        }
        Boolean done = null;
        if (mapData.containsKey("done")) {
            done = ParseParameterService.isDone(mapData.get("done"));
        }
        Byte priority = null;
        if (mapData.containsKey("priority")) {
            priority = ParseParameterService.parsePriority(mapData.get("priority"));
        }
        Task task = dbServiceTask.updateTask(taskListId, taskId, name, title, done, priority);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Метод для создания обьекта Task
     *
     * @param taskListIdString принемает ID обьекта TaskList
     * @param mapData          получает значения в полях 'name', 'title','priority' и 'done' в формате json
     * @return возвращает созданный обьект Task
     */
    @ApiOperation(value = "Create Task",
            notes = "Creating Task by taskListId and id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Database not available")
    })
    @PostMapping("/{taskListIdString}/task")
    public ResponseEntity<?> newTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString,
            @ApiParam(value = "Json data", required = true,
                    example = "{\n\t\"name\":\"name task\"," +
                            "\n\t\"title\":\"eny title\"," +
                            "\n\t\"priority\":\"3\"\n}")
            @RequestBody Map<String, String> mapData
    ) throws TaskListException {
        UUID taskListId = ParseParameterService.parseTaskListId(taskListIdString);

        String name;
        if (mapData.containsKey("name") && !mapData.get("name").isEmpty()) {
            name = ParseParameterService.parseName(mapData.get("name"));
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.NAME);
        }
        String title;
        if (mapData.containsKey("title")) {
            title = ParseParameterService.parseTitle(mapData.get("title"));
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.TITLE);
        }
        byte priority;
        if (mapData.containsKey("priority") && !mapData.get("priority").isEmpty()) {
            priority = ParseParameterService.parsePriority(mapData.get("priority"));
        } else {
            throw new BadParameterException(BadParameterException.ExceptionType.PRIORITY);
        }
        Task task = dbServiceTask.createTask(taskListId, name, title, priority);
        Map.Entry<String, UUID> createdTaskId = Map.entry("id", task.getId());
        return new ResponseEntity<>(createdTaskId, HttpStatus.CREATED);
    }

    /**
     * Метод для получения бьекта Task по ID
     *
     * @param taskListIdString принемает ID обьекта TaskList
     * @param taskIdString     принемает ID обьекта Task
     * @return возвращает обьект Task
     */
    @ApiOperation(value = "Get Task",
            notes = "Getting Task by taskListId and id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Database not available")
    })
    @GetMapping("/{taskListIdString}/task/{taskIdString}")
    public ResponseEntity<?> getTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString,
            @ApiParam(value = "Id task", required = true)
            @PathVariable("taskIdString") String taskIdString
    )
            throws TaskListException {

        UUID taskListId = ParseParameterService.parseTaskListId(taskListIdString);
        UUID taskId = ParseParameterService.parseTaskId(taskIdString);
        Task task = dbServiceTask.getTask(taskListId, taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
