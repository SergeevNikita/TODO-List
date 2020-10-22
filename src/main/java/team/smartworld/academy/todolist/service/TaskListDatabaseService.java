package team.smartworld.academy.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.entity.*;
import team.smartworld.academy.todolist.exceptions.*;
import team.smartworld.academy.todolist.repository.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Сервис работы с базой данных
 */
@Service
public class TaskListDatabaseService {

    /**
     * Обьекты репозиториев для работы с БД
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
    public TaskListDatabaseService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
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
        TaskList taskList = getTaskList(taskListId);
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

    /**
     * Метод для получения обьекта TaskList из БД
     *
     * @param taskListId принемает id обьекта TaskList
     * @return возвращает обьект TaskList
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     * @throws NotFoundException             выбрасывает исключение если обьект TaskList не найден в БД
     */
    public TaskList getTaskList(UUID taskListId) throws DatabaseNotAvailableException, NotFoundException {
        Optional<TaskList> oTaskList;
        try {
            oTaskList = taskListRepository.findById(taskListId);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        if (oTaskList.isPresent()) {
            return oTaskList.get();
        } else {
            throw new NotFoundException(NotFoundException.ExceptionType.TASK_LIST_NOT_FOUND);
        }
    }

    /**
     * Метод для удаления обьекта TaskList из БД
     *
     * @param taskListId принемает id обьекта TaskList
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public void deleteTaskList(UUID taskListId) throws DatabaseNotAvailableException {
        try {
            taskListRepository.deleteById(taskListId);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }

    /**
     * Метод для сохранения обьекта TaskList в БД
     *
     * @param taskList принемает обьект TaskList
     * @return возвращает сохраненный обьект TaskList
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public TaskList saveTaskList(TaskList taskList) throws DatabaseNotAvailableException {
        try {
            return taskListRepository.save(taskList);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }

    /**
     * Метод для получения списка обьектов TaskList
     *
     * @param offset            начальная позиция
     * @param limit             размер списка
     * @param dateCreatedSort   сортировка по дате создания
     * @param dateChangeSort    сортировка по дате изменения
     * @param nameSort          сортировка по имени
     * @param isDoneSort        сортировка по полю выполненно
     * @param dateCreatedFilter фильтр по дате создания
     * @param dateChangeFilter  фильтр по дате изменения
     * @param nameFilter        фильтр по имени
     * @param doneFilter        фильтр по полю выполненно
     * @return возвращает список обьектов TaskList
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    // ИЗМЕНИТЬ РЕАЛИЗАЦИЮ в рамках требований задания
    public List<Map<String, String>> getAllTaskList(
            long offset, int limit,
            boolean dateCreatedSort, boolean dateChangeSort,
            boolean nameSort, boolean isDoneSort,
            LocalDateTime dateCreatedFilter,
            LocalDateTime dateChangeFilter,
            String nameFilter,
            Boolean doneFilter
    ) throws DatabaseNotAvailableException {
        Iterable<TaskList> oTaskList;
        try {
            oTaskList = taskListRepository.findAll();
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        Iterator<TaskList> listIterator = oTaskList.iterator();
        List<Map<String, String>> taskListDate = new ArrayList<>();

        while (listIterator.hasNext() && limit > 0) {
            TaskList taskList = listIterator.next();
//            if (taskList.getId() >= offset) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("id", taskList.getId().toString());
            dataMap.put("name", taskList.getName());
            dataMap.put("dateCreated", taskList.getDateCreated().toString());
            dataMap.put("dateChange", taskList.getDateChange().toString());
            dataMap.put("done", Boolean.toString(taskList.isDone()));
            taskListDate.add(dataMap);
//                limit--;
//            }
        }
        return taskListDate;
    }
}