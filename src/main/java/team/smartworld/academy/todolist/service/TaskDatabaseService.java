package team.smartworld.academy.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.entity.*;
import team.smartworld.academy.todolist.exceptions.*;
import team.smartworld.academy.todolist.repository.TaskRepository;

import java.util.*;

@Service
public class TaskDatabaseService {

    /**
     * Обьект репозитория для работы с БД
     */
    private final TaskRepository taskRepository;

    /**
     * Сервис работы с базой данных обьекта TaskList
     */
    private final TaskListDatabaseService taskListDatabaseService;

    /**
     * Конструктор
     *
     * @param taskRepository          принемает обьект TaskRepository
     * @param taskListDatabaseService принемает обьект сервиса работы с базой данных обьекта TaskList
     */
    @Autowired
    public TaskDatabaseService(TaskRepository taskRepository, TaskListDatabaseService taskListDatabaseService) {
        this.taskRepository = taskRepository;
        this.taskListDatabaseService = taskListDatabaseService;
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
        TaskList taskList = taskListDatabaseService.getTaskList(taskListId);
        Optional<Task> oTask;
        try {
            oTask = taskRepository.findById(taskId);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        if (oTask.isPresent() && taskList.getTasks().contains(oTask.get())) {
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
    }

    /**
     * Метод для сохранения обьекта Task в БД
     *
     * @param task получает обьект типа task
     * @return возвращает сохраненный обьект Task
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public Task saveTask(Task task) throws DatabaseNotAvailableException {
        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }
}
