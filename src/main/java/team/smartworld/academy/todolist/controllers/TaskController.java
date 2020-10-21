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
import team.smartworld.academy.todolist.service.TaskListDatabaseService;
import team.smartworld.academy.todolist.service.TaskListParseParameterService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

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
     * Обьект сервиса работы с базой данных
     */
    private final TaskListDatabaseService dbService;

    /**
     * Конструктор
     *
     * @param dbService принемает обьект сервиса работы с базой данных
     */
    @Autowired
    public TaskController(TaskListDatabaseService dbService) {
        this.dbService = dbService;
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
            @PathVariable("taskIdString") String taskIdString)
            throws TaskListException {
        UUID taskListId = TaskListParseParameterService.parseTaskListId(taskListIdString);
        UUID taskId = TaskListParseParameterService.parseTaskId(taskIdString);
        Task task = dbService.getTask(taskListId, taskId);
        dbService.deleteTask(task);
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
            @RequestBody Map<String, String> mapData)
            throws TaskListException {

        UUID taskListId = TaskListParseParameterService.parseTaskListId(taskListIdString);
        UUID id = TaskListParseParameterService.parseTaskId(taskIdString);
        boolean done = TaskListParseParameterService.getDone(mapData);
        Task task = dbService.getTask(taskListId, id);
        task.setDone(done);
        task = dbService.saveTask(task);
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
            @RequestBody Map<String, String> mapData)
            throws TaskListException {
        UUID taskListId = TaskListParseParameterService.parseTaskListId(taskListIdString);
        UUID taskId = TaskListParseParameterService.parseTaskId(taskIdString);
        Task task = dbService.getTask(taskListId, taskId);
        if (mapData.containsKey("name")) {
            task.setName(TaskListParseParameterService.getName(mapData));
        }
        if (mapData.containsKey("title")) {
            task.setTitle(TaskListParseParameterService.checkAndGetTitle(mapData));
        }
        if (mapData.containsKey("done")) {
            task.setDone(TaskListParseParameterService.getDone(mapData));
        }
        if (mapData.containsKey("priority")) {
            task.setPriority(TaskListParseParameterService.checkAndGetPriority(mapData));
        }
        task = dbService.saveTask(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Метод для создания обьекта Task
     *
     * @param taskListIdString принемает ID обьекта TaskList
     * @param mapData получает значения в полях 'name', 'title','priority' и 'done' в формате json
     * @return возвращает созданный обьект Task
     */
    @ApiOperation(value = "Create Task",
            notes = "Creating Task by taskListId and id")
    @PostMapping("/{taskListIdString}/task")
    public ResponseEntity<?> newTask(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString,
            @ApiParam(value = "Json data", required = true,
                    example = "{\n\t\"done\":\"true\"," +
                            "\n\t\"name\":\"name task\"," +
                            "\n\t\"title\":\"eny title\"," +
                            "\n\t\"priority\":\"3\"\n}")
            @RequestBody Map<String, String> mapData) throws TaskListException {
        UUID taskListId = TaskListParseParameterService.parseTaskListId(taskListIdString);
        String name = TaskListParseParameterService.getName(mapData);
        String title = TaskListParseParameterService.checkAndGetTitle(mapData);
        byte priority = TaskListParseParameterService.checkAndGetPriority(mapData);
        TaskList taskList = dbService.getTaskList(taskListId);

        Task task = new Task();
        task.setTaskList(taskList);
        task.setName(name);
        task.setTitle(title);
        task.setPriority(priority);
        task.setDateCreated(LocalDateTime.now());
        task.setDateChange(LocalDateTime.now());

//        Map.Entry<String, UUID> createdTaskId = Map.entry("id", dbService.saveTask(task).getId());
//        return new ResponseEntity<>(createdTaskId, HttpStatus.CREATED);
        return new ResponseEntity<>("не реализовано", HttpStatus.CREATED);


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
            @PathVariable("taskIdString") String taskIdString)
            throws TaskListException {

        UUID taskListId = TaskListParseParameterService.parseTaskListId(taskListIdString);
        UUID taskId = TaskListParseParameterService.parseTaskId(taskIdString);
        Task task = dbService.getTask(taskListId, taskId);
//        Task task = dbService.getTaskNew(taskListId, taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
