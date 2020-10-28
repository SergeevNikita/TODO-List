package team.smartworld.academy.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.exceptions.*;
import team.smartworld.academy.todolist.repository.TaskListRepository;
import team.smartworld.academy.todolist.specs.TaskListSpecs;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Сервис работы с базой данных обьекта TaskList
 */
@Service
public class TaskListDatabaseService {

    /**
     * Обьект репозитория для работы с БД
     */
    private final TaskListRepository taskListRepository;

    /**
     * Конструктор
     *
     * @param taskListRepository принемает обьект TaskListRepository
     */
    @Autowired
    public TaskListDatabaseService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
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
     * @param page              страница
     * @param limit             размер списка
     * @param sortBy            сортировка по полю
     * @param dateCreatedFilter фильтр по дате создания
     * @param dateChangeFilter  фильтр по дате изменения
     * @param nameFilter        фильтр по имени
     * @param doneFilter        фильтр по полю выполненно
     * @return возвращает список обьектов TaskList
     * @throws DatabaseNotAvailableException выбрасывает исключение если БД не отвечает
     */
    public List<Map<String, String>> getAllTaskList(
            int page, int limit, String sortBy, boolean ask,
            LocalDateTime dateCreatedFilter,
            LocalDateTime dateChangeFilter,
            String nameFilter,
            Boolean doneFilter
    ) throws DatabaseNotAvailableException {

        Sort sort;
        if (ask) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        Page<TaskList> result;
        long countAllTaskList;
        long countOfDoneTaskList;
        try {
            result = taskListRepository.findAll(Specification.where(TaskListSpecs.withName(nameFilter))
                    .and(TaskListSpecs.withDateCreated(dateCreatedFilter))
                    .and(TaskListSpecs.withDateChange(dateChangeFilter))
                    .and(TaskListSpecs.withDone(doneFilter)), pageRequest);

            countAllTaskList = taskListRepository.count();
            countOfDoneTaskList = taskListRepository.count(Specification.where(TaskListSpecs.withDone(true)));
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }

        List<Map<String, String>> taskListDate = new ArrayList<>();

        for (TaskList taskList : result) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("id", taskList.getId().toString());
            dataMap.put("name", taskList.getName());
            dataMap.put("dateCreated", taskList.getDateCreated().toString());
            dataMap.put("dateChange", taskList.getDateChange().toString());
            dataMap.put("done", Boolean.toString(taskList.isDone()));
            taskListDate.add(dataMap);
        }
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("countOfDoneTaskList", String.valueOf(countOfDoneTaskList));
        dataMap.put("countOfUnfinishedTaskList", String.valueOf(countAllTaskList - countOfDoneTaskList));
        taskListDate.add(dataMap);

        return taskListDate;
    }
}