package team.smartworld.academy.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.entity.*;
import team.smartworld.academy.todolist.exceptions.*;
import team.smartworld.academy.todolist.repository.*;
import team.smartworld.academy.todolist.specs.TaskSpecs;

import java.util.*;

/**
 * Сервис работы с базой данных обьекта Task
 */
@Service
public class TaskDatabaseService {

    /**
     * Обьект репозитория для работы с БД
     */
    private final TaskRepository taskRepository;

    private final TaskListRepository taskListRepository;

    /**
     * Конструктор
     *
     * @param taskRepository     принемает обьект TaskRepository
     * @param taskListRepository принемает обьект TaskListRepository
     */
    @Autowired
    public TaskDatabaseService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    /**
     * Метод для получения обьекта Task из БД
     *
     * @param taskListId принемает id обьекта TaskList
     * @param taskId     принемает id обьекта Task
     * @return возвращает обьект Task
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     * @throws NotFoundException             выбрасывает исключение если обьект Task не найден в БД
     */
    public Task getTask(UUID taskListId, UUID taskId) throws DatabaseNotAvailableException, NotFoundException {
        Optional<Task> oTask;
        try {
            oTask = taskRepository.findByIdAndTaskListId(taskId, taskListId);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        if (oTask.isPresent()) {
            return oTask.get();
        } else {
            throw new NotFoundException(NotFoundException.ExceptionType.TASK_NOT_FOUND);
        }
    }

    /**
     * Метод для удаления обьекта Task из БД
     *
     * @param task получает обьект типа task
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public void deleteTask(Task task) throws DatabaseNotAvailableException {
        try {
            taskRepository.delete(task);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        UUID taskListId = task.getTaskList().getId();
        updateTaskListAfterChangeTask(taskListId);
    }

    /**
     * Метод для сохранения обьекта Task в БД
     *
     * @param task получает обьект типа task
     * @return возвращает сохраненный обьект Task
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public Task saveTask(Task task) throws DatabaseNotAvailableException {
        Task taskResult;
        try {
            taskResult = taskRepository.save(task);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        UUID taskListId = task.getTaskList().getId();
        updateTaskListAfterChangeTask(taskListId);
        return taskResult;
    }

    private void updateTaskListAfterChangeTask(UUID taskListId) throws DatabaseNotAvailableException {
        try {
            Optional<TaskList> oTaskList = taskListRepository.findById(taskListId);
            if (oTaskList.isPresent()) {
                TaskList taskList = oTaskList.get();
                long countAllTask = taskRepository.count(Specification.where(TaskSpecs.withTaskList(taskList)));
                long countDoneTask = taskRepository.count(Specification.where(TaskSpecs.withDone(true))
                        .and(TaskSpecs.withTaskList(taskList)));
                taskList.setDone(countAllTask == countDoneTask);
                taskListRepository.save(taskList);
            }
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }
}
