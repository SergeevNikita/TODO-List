package team.smartworld.academy.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.entity.Task;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.exceptions.DatabaseNotAvailableException;
import team.smartworld.academy.todolist.exceptions.TaskListNotFoundException;
import team.smartworld.academy.todolist.exceptions.TaskNotFoundException;
import team.smartworld.academy.todolist.repository.TaskListRepository;
import team.smartworld.academy.todolist.repository.TaskRepository;

import java.util.Optional;

/**
 * Database service
 */
@Service
public class DatabaseService {
    /**
     * Repositories
     */
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    /**
     * Constructor
     *
     * @param taskRepository     Task repository
     * @param taskListRepository TaskList repository
     */
    @Autowired
    public DatabaseService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }


    /**
     * Function for getting Task
     *
     * @param taskListId Task List id
     * @param id         Task id
     * @return Task
     * @throws DatabaseNotAvailableException exception
     * @throws TaskNotFoundException         exception
     * @throws TaskListNotFoundException     exception
     */
    public Task getTask(Long taskListId, Long id) throws DatabaseNotAvailableException,
            TaskNotFoundException, TaskListNotFoundException {

        TaskList taskList = getTaskList(taskListId);
        Optional<Task> oTask;
        try {
            oTask = taskRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        if (oTask.isPresent() && taskList.getTasks().contains(oTask.get())) {
            return oTask.get();
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Function for deleting Task
     *
     * @param task task
     * @throws DatabaseNotAvailableException exception
     */
    public void deleteTask(Task task) throws DatabaseNotAvailableException {
        try {
            taskRepository.delete(task);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }

    /**
     * @param task task
     * @return Task saved
     * @throws DatabaseNotAvailableException exception
     */
    public Task saveTask(Task task) throws DatabaseNotAvailableException {
        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }

    /**
     * @param id id Task List
     * @return Task List
     * @throws DatabaseNotAvailableException exception
     * @throws TaskListNotFoundException     exception
     */
    public TaskList getTaskList(Long id) throws DatabaseNotAvailableException, TaskListNotFoundException {
        Optional<TaskList> oTaskList;
        try {
            oTaskList = taskListRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        if (oTaskList.isPresent()) {
            return oTaskList.get();
        } else {
            throw new TaskListNotFoundException();
        }
    }
}