package team.smartworld.academy.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.entity.*;
import team.smartworld.academy.todolist.exceptions.*;
import team.smartworld.academy.todolist.repository.*;
import team.smartworld.academy.todolist.specs.TaskSpecs;

import java.time.LocalDateTime;
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
     * @param taskId получает обьект типа task
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public void deleteTask(UUID taskListId, UUID taskId) throws DatabaseNotAvailableException {
        try {
            taskRepository.deleteByIdAndTaskListId(taskId, taskListId);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
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

    /**
     * Метод для обновления обьекта Task в БД
     *
     * @param taskListId ID обьекта TaskList
     * @param taskId     ID обьекта Task
     * @param name       имя задачи
     * @param title      краткое описание
     * @param done       выполнено или нет
     * @param priority   приоритет
     * @return изменённый обьект Task
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     * @throws NotFoundException             выбрасывает исключение если обьект Task не найден в БД
     */
    public Task updateTask(UUID taskListId, UUID taskId, String name, String title, Boolean done, Byte priority)
            throws DatabaseNotAvailableException, NotFoundException {
        Task task = getTask(taskListId, taskId);
        if (name != null) {
            task.setName(name);
        }
        if (title != null) {
            task.setTitle(title);
        }
        if (done != null) {
            task.setDone(done);
        }
        if (priority != null) {
            task.setPriority(priority);
        }
        task.setDateChange(LocalDateTime.now());
        return saveTask(task);
    }

    /**
     * Метод для созания обьекта Task в БД
     *
     * @param taskListId ID обьекта TaskList
     * @param name       имя задачи
     * @param title      краткое описание
     * @param priority   приоритет
     * @return созданный обьект Task
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public Task createTask(UUID taskListId, String name, String title, Byte priority) throws DatabaseNotAvailableException {
        TaskList taskList;
        try {
            Optional<TaskList> oTaskList = taskListRepository.findById(taskListId);
            if (oTaskList.isPresent()) {
                taskList = oTaskList.get();
            } else {
                throw new NotFoundException(NotFoundException.ExceptionType.TASK_LIST_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        Task task = new Task();
        task.setName(name);
        task.setTitle(title);
        task.setPriority(priority);
        task.setDone(false);
        task.setTaskList(taskList);
        task.setDateCreated(LocalDateTime.now());
        task.setDateChange(LocalDateTime.now());
        return saveTask(task);
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
