package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.exceptions.TaskListException;
import team.smartworld.academy.todolist.service.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Task List controller - класс обработки REST запросов пользователя.
 * Позволяет создавать, переименовывать, удалять, и получать обьект TaskList.
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/todo/api/taskList")
@Api(value = "Task List Controller", consumes = "application/json", produces = "application/json")
public class TaskListController {

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
    public TaskListController(TaskListDatabaseService dbService) {
        this.dbService = dbService;
    }

    /**
     * Метод для удаления обьекта TaskList по ID.
     *
     * @param taskListIdString принемает ID обьекта TaskList
     */
    @ApiOperation(value = "Deleting Task List",
            notes = "Deleting Task List by id")
    @DeleteMapping("/{taskListIdString}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(code = 500, message = "Database not available")
    public void deleteTaskList(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString
    ) throws TaskListException {
        UUID id = TaskListParseParameterService.parseTaskListId(taskListIdString);
        dbService.deleteTaskList(id);
    }

    /**
     * Метод для получения обьекта TaskList по ID
     *
     * @param taskListIdString принемает ID обьекта TaskList
     * @return возвращает обьект TaskList
     */
    @ApiOperation(value = "Get Task List",
            notes = "Getting Task List by id")
    @GetMapping("/{taskListIdString}")
    public ResponseEntity<?> getTaskList(
            @ApiParam(value = "Id task list", required = true)
            @PathVariable("taskListIdString") String taskListIdString
    ) throws TaskListException {
        UUID id = TaskListParseParameterService.parseTaskListId(taskListIdString);
        TaskList taskList = dbService.getTaskList(id);
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    /**
     * Метод для изменения поля name обьекта TaskList по ID
     *
     * @param mapData          получает новое имя в поле 'name' в формате json
     * @param taskListIdString получает ID обьекта TaskList
     * @return возвращает изменённый обьект TaskList
     */
    @PatchMapping("/{taskListIdString}")
    @ApiOperation(value = "Rename Task List",
            notes = "Rename Task List by id")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Not Found or Empty name value"),
            @ApiResponse(code = 500, message = "Database not available")
    })
    public ResponseEntity<?> renameTaskList(
            @ApiParam(value = "Id task", required = true)
            @PathVariable("taskListIdString") String taskListIdString,
            @ApiParam(value = "Json name data", required = true, example = "{\n\t\"name\":\"name task list\"\n}")
            @RequestBody Map<String, String> mapData
    )
            throws TaskListException {
        UUID id = TaskListParseParameterService.parseTaskListId(taskListIdString);
        String name = TaskListParseParameterService.getName(mapData);
        TaskList taskList = dbService.getTaskList(id);
        taskList.setName(name);
        taskList.setDateChange(LocalDateTime.now());
        taskList = dbService.saveTaskList(taskList);
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    /**
     * Метод для получения списка всех обьектов TaskList
     *
     * @param mapData получает данные пагинации, сортировки и фильтрации
     * @return возвращает список обьектов TaskList
     */
    @GetMapping
    @ApiOperation(value = "Get all Task List",
            notes = "Getting all Task List")
    public ResponseEntity<?> getAllTaskLists(
            @ApiParam(value = "Json pagination, sorting and filtering data", required = true)
            @RequestBody Map<String, String> mapData
    ) throws TaskListException {
        // Проверки и прочее.
        // Пагинация
        long offset = 0L;
        if (mapData.containsKey("offset")) {
            offset = TaskListParseParameterService.getOffset(mapData);
        }
        int limit = 10;
        if (mapData.containsKey("limit")) {
            limit = TaskListParseParameterService.getLimit(mapData);
        }
        // сортировка
        boolean dateCreatedSort = false;
        if (mapData.containsKey("dateCreatedSort")) {
            dateCreatedSort = TaskListParseParameterService.getDateCreatedSort(mapData);
        }
        boolean dateChangeSort = false;
        if (mapData.containsKey("dateChangeSort")) {
            dateChangeSort = TaskListParseParameterService.getDateChangeSort(mapData);
        }
        boolean nameSort = false;
        if (mapData.containsKey("nameSort")) {
            nameSort = TaskListParseParameterService.getNameSort(mapData);
        }
        boolean doneSort = false;
        if (mapData.containsKey("doneSort")) {
            doneSort = TaskListParseParameterService.getDoneSort(mapData);
        }
        // фильтрация
        LocalDateTime dateCreatedFilter = null;
        if (mapData.containsKey("dateCreated")) {
            dateCreatedFilter = TaskListParseParameterService.getDateCreated(mapData);
        }
        LocalDateTime dateChangeFilter = null;
        if (mapData.containsKey("dateChange")) {
            dateChangeFilter = TaskListParseParameterService.getDateChange(mapData);
        }
        String nameFilter = null;
        if (mapData.containsKey("name")) {
            nameFilter = TaskListParseParameterService.getName(mapData);
        }
        Boolean doneFilter = null;
        if (mapData.containsKey("done")) {
            doneFilter = TaskListParseParameterService.getDone(mapData);
        }

        List<Map<String, String>> taskListDate = dbService.getAllTaskList(offset, limit,
                dateCreatedSort, dateChangeSort, nameSort, doneSort,
                dateCreatedFilter, dateChangeFilter, nameFilter, doneFilter
        );
        return new ResponseEntity<>(taskListDate, HttpStatus.OK);
    }

    /**
     * Метод для создания обьекта TaskList
     *
     * @param mapData получает имя в поле 'name' в формате json
     * @return возвращает созданный обьект TaskList
     */
    @PostMapping
    @ApiOperation(value = "Create Task List",
            notes = "Creating Task List")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Not Found or Empty name value"),
            @ApiResponse(code = 500, message = "Database not available")
    })
    public ResponseEntity<?> createTaskList(
            @ApiParam(value = "Json name data", required = true, example = "{\n\t\"name\":\"name task list\"\n}")
            @RequestBody Map<String, String> mapData
    ) throws TaskListException {
        String name = TaskListParseParameterService.getName(mapData);
        TaskList taskList = new TaskList();
        taskList.setName(name);
        taskList.setDateCreated(LocalDateTime.now());
        taskList.setDateChange(LocalDateTime.now());
        Map.Entry<String, UUID> createdTaskListId = Map.entry("id", dbService.saveTaskList(taskList).getId());
        return new ResponseEntity<>(createdTaskListId, HttpStatus.CREATED);
    }
}
