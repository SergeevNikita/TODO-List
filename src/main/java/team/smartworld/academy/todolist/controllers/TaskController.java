package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.*;
import team.smartworld.academy.todolist.exceptions.TaskListException;
import team.smartworld.academy.todolist.service.*;

import java.time.LocalDateTime;
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
        Task task = dbServiceTask.getTask(taskListId, taskId);
        dbServiceTask.deleteTask(task);
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
        boolean done = ParseParameterService.getDone(mapData);
        Task task = dbServiceTask.getTask(taskListId, id);
        task.setDone(done);
        task = dbServiceTask.saveTask(task);
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
        Task task = dbServiceTask.getTask(taskListId, taskId);
        if (mapData.containsKey("name")) {
            task.setName(ParseParameterService.getName(mapData));
        }
        if (mapData.containsKey("title")) {
            task.setTitle(ParseParameterService.getTitle(mapData));
        }
        if (mapData.containsKey("done")) {
            task.setDone(ParseParameterService.getDone(mapData));
        }
        if (mapData.containsKey("priority")) {
            task.setPriority(ParseParameterService.getPriority(mapData));
        }
        task = dbServiceTask.saveTask(task);
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
        String name = ParseParameterService.getName(mapData);
        String title = ParseParameterService.getTitle(mapData);
        byte priority = ParseParameterService.getPriority(mapData);
        TaskList taskList = dbServiceTaskList.getTaskList(taskListId);

        Task task = new Task();
        task.setTaskList(taskList);
        task.setName(name);
        task.setTitle(title);
        task.setPriority(priority);
        task.setDateCreated(LocalDateTime.now());
        task.setDateChange(LocalDateTime.now());

        Map.Entry<String, UUID> createdTaskId = Map.entry("id", dbServiceTask.saveTask(task).getId());
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
