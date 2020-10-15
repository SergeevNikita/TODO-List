package team.smartworld.academy.todolist.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.entity.Task;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.repository.TaskListRepository;
import team.smartworld.academy.todolist.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

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
     *
     */
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    /**
     * @param taskRepository     Task repository
     * @param taskListRepository TaskList repository
     */
    @Autowired
    public TaskController(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    /**
     * Method for deleting Task in TodoList.
     *
     * @param taskListId TaskList id
     * @param id is the id of the Task to delete in DB
     */
    @DeleteMapping("/{taskListId}/task/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("taskListId") Long taskListId,
                           @PathVariable("id") Long id) {
        // добавить проверки ID
        Optional<Task> oTask = taskRepository.findById(id);
        if (oTask.isPresent()) {
            Task task = oTask.get();
            if (task.getTaskList().getId().equals(taskListId)) {
                taskRepository.delete(task);
            }
        }

    }

    /**
     * Method for mark done in Task.
     *
     * @param id is the id of the Task to mark done in DB
     *           (uri type mark_done_task/?id={number}, json body is empty)
     * @return status or error type end status
     */


    @PatchMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> markDoneTask(@PathVariable("taskListId") Long taskListId,
                                          @PathVariable("id") Long id) {

        Optional<Task> oTask = taskRepository.findById(id);
        if (oTask.isPresent()) {
            Task task = oTask.get();
            if (task.getTaskList().getId().equals(taskListId)) {
                task.setDone(true);
                return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /**
     * Method for change in Task.
     *
     * @param id         is the id of the Task to change in DB
     * @param changeTask changed Task date
     * @return status or error type end status
     */
    @PutMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> changeTask(@PathVariable("taskListId") Long taskListId,
                                        @PathVariable("id") Long id,
                                        @RequestBody Map<String, String> changeTask) {
        // Проверки
        // изменение
        Optional<Task> oTask = taskRepository.findById(id);
        if (oTask.isPresent()) {
            Task task = oTask.get();
            if (task.getTaskList().getId().equals(taskListId)) {
                if (changeTask.containsKey("isDone")) {
                    task.setDone(Boolean.parseBoolean(changeTask.get("isDone")));
                }
                if (changeTask.containsKey("name")) {
                    task.setName(changeTask.get("name").replaceAll("[^A-Za-zА-Яа-я0-9 ]", ""));
                }
                if (changeTask.containsKey("title")) {
                    task.setTitle(changeTask.get("title").replaceAll("[^A-Za-zА-Яа-я0-9 ]", ""));
                }
                if (changeTask.containsKey("priority")) {
                    task.setPriority(Byte.parseByte(changeTask.get("priority")));
                }
                return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // не верно указан список
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // не найден Task
        }

    }

    /**
     * Method for create Task
     *
     * @param newTask new Task Date
     * @return Task and status or error and status
     */
    @PostMapping("/{taskListId}/task")
    public ResponseEntity<?> newTask(@PathVariable("taskListId") Long taskListId,
                                     @RequestBody Map<String, String> newTask) {
        // Проверки
        // создание и сохранение в БД
        Optional<TaskList> oTaskList = taskListRepository.findById(taskListId);
        if (oTaskList.isPresent()) {
            TaskList taskList = oTaskList.get();
            Task task = new Task();
            task.setTaskList(taskList);
            task.setName(newTask.get("name").replaceAll("[^A-Za-zА-Яа-я0-9 ]", ""));
            task.setTitle(newTask.get("title").replaceAll("[^A-Za-zА-Яа-я0-9 ]", ""));
            task.setPriority(Byte.parseByte(newTask.get("priority")));
            task.setDateCreated(LocalDateTime.now());
            task.setDateChange(LocalDateTime.now());
            String createdTaskId = "{ \"id\": \"" + taskRepository.save(task).getId() + "\" }";
            return new ResponseEntity<>(createdTaskId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for getting Task
     *
     * @param id is the id for search Task in DB
     * @return Task and status or error and status
     */
    @GetMapping("/{taskListId}/task/{id}")
    public ResponseEntity<?> getTask(@PathVariable("taskListId") Long taskListId,
                                     @PathVariable("id") Long id) {
        // Проверить id
        // Найти в БД
        Optional<Task> oTasks = taskRepository.findById(id);
        if (oTasks.isPresent()) {
            Iterator<Task> iterator = oTasks.stream().iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getTaskList().getId().equals(taskListId)) {
                    return new ResponseEntity<>(task, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
