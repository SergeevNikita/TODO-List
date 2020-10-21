package team.smartworld.academy.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.smartworld.academy.todolist.entity.Task;
import team.smartworld.academy.todolist.entity.TaskList;
import team.smartworld.academy.todolist.exceptions.DatabaseNotAvailableException;
import team.smartworld.academy.todolist.exceptions.NotFoundException;
import team.smartworld.academy.todolist.repository.TaskListRepository;
import team.smartworld.academy.todolist.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.*;

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
     * @throws NotFoundException             exception
     */
    public Task getTask(UUID taskListId, UUID id) throws DatabaseNotAvailableException, NotFoundException {

        TaskList taskList = getTaskList(taskListId);
        Optional<Task> oTask;
        try {
            //oTask = taskRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
//        if (oTask.isPresent() && taskList.getTasks().contains(oTask.get())) {
//            return oTask.get();
//        } else {
//            throw new NotFoundException(NotFoundException.ExceptionType.TASK_NOT_FOUND);
//        }
        return null;
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
     * @throws NotFoundException             exception
     */
    public TaskList getTaskList(UUID id) throws DatabaseNotAvailableException, NotFoundException {
        Optional<TaskList> oTaskList;
        try {
//            oTaskList = taskListRepository.findById(id);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
//        if (oTaskList.isPresent()) {
//            return oTaskList.get();
//        } else {
//            throw new NotFoundException(NotFoundException.ExceptionType.TASK_LIST_NOT_FOUND);
//        }
        return null;
    }

    /**
     * @param id id Task List
     */
    public void deleteTaskList(UUID id) throws DatabaseNotAvailableException {
        try {
            //taskListRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }

    /**
     * @param taskList task list object
     * @return task list saved object
     * @throws DatabaseNotAvailableException exception
     */
    public TaskList saveTaskList(TaskList taskList) throws DatabaseNotAvailableException {
        try {
            return taskListRepository.save(taskList);
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
    }

    /**
     * @param offset            offset to start position
     * @param limit             limit
     * @param dateCreatedSort   dateCreatedSort
     * @param dateChangeSort    dateChangeSort
     * @param nameSort          nameSort
     * @param isDoneSort        isDoneSort
     * @param dateCreatedFilter dateCreatedFilter
     * @param dateChangeFilter  dateChangeFilter
     * @param nameFilter        nameFilter
     * @param isDoneFilter      isDoneFilter
     * @return Task list data
     * @throws DatabaseNotAvailableException exception
     */
    // ИЗМЕНИТЬ РЕАЛИЗАЦИЮ в рамках требований задания
    public List<Map<String, String>> getAllTaskList(long offset, int limit,
                                                    boolean dateCreatedSort, boolean dateChangeSort,
                                                    boolean nameSort, boolean isDoneSort,
                                                    LocalDateTime dateCreatedFilter,
                                                    LocalDateTime dateChangeFilter,
                                                    String nameFilter,
                                                    Boolean isDoneFilter) throws DatabaseNotAvailableException {
        Iterable<TaskList> oTaskList;
        try {
            oTaskList = taskListRepository.findAll();
        } catch (Exception e) {
            throw new DatabaseNotAvailableException();
        }
        Iterator<TaskList> listIterator = oTaskList.iterator();
        List<Map<String, String>> taskListDate = new ArrayList<>();

//        while (listIterator.hasNext() && limit > 0) {
//            TaskList taskList = listIterator.next();
//            if (taskList.getId() >= offset) {
//                Map<String, String> dataMap = new HashMap<>();
//                dataMap.put("id", Long.toString(taskList.getId()));
//                dataMap.put("name", taskList.getName());
//                dataMap.put("dateCreated", taskList.getDateCreated().toString());
//                dataMap.put("dateChange", taskList.getDateChange().toString());
//                dataMap.put("isDone", Boolean.toString(taskList.isDone()));
//                taskListDate.add(dataMap);
//                limit--;
//            }
//        }
        return taskListDate;
    }
}