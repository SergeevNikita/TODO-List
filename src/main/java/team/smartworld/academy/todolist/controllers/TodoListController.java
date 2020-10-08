package team.smartworld.academy.todolist.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.models.TodoList;

/**
 * TodoList controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@RestController
public class TodoListController {

    /**
     * Method for deleting TodoList.
     *
     * @param id is the id of the TodoList to delete in DB
     * @return status or error type end status
     */
    @RequestMapping(value = "/delete_todo_list/",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTodoList(@RequestParam String id) {
        //добавить проверки ID
        //  repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method for getting TodoList
     *
     * @param id is the id for search TodoList in DB
     * @return TodoList and status or error and status
     */
    @RequestMapping(value = "/get_todo_list/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTodoList(@RequestParam String id) {
        // Проверки
        // Поиск в БД
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for rename TodoLIst
     *
     * @param newName new name for TodoList
     * @param id      is the id for search TodoList in DB
     * @return status or error and status
     */
    // PUT or PATCH ???
    @RequestMapping(value = "/rename_todo_list/",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> renameTodoList(@RequestParam String id,
                                            @RequestParam String newName) {
        // Проверки
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for create TodoList
     *
     * @param newTodoList new TodoList date
     * @return TodoList and status or error and status
     */
    @RequestMapping(value = "/new_todo_list/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newTodoList(@RequestBody TodoList newTodoList) {
        // Проверки
        // Создание и сохранение в БД
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method for getting all TodoLists
     *
     * @param startId start id search
     * @param limit   limit getting TodoLists
     * @return all TodoLists and status or error and status
     */
    @RequestMapping(value = "/get_all_todo_lists/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTodoLists(@RequestParam(value = "startId", defaultValue = "1") String startId,
                                             @RequestParam(value = "limit", defaultValue = "10") String limit) {
        // Проверки и прочее.
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
