package team.smartworld.academy.todolist.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * TodoList controller
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/todo/api/v1.0/todolist")
public class TodoListController {

    /**
     * Method for deleting TodoList.
     *
     * @param id is the id of the TodoList to delete in DB
     * @return status or error type end status
     */
    @DeleteMapping
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
    @GetMapping
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
    @PatchMapping
    public ResponseEntity<?> renameTodoList(@RequestParam String id,
                                            @RequestBody Map<String, String> newName) {
        // Проверки
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for create TodoList
     *
     * @param newNameTodoList new TodoList date
     * @return TodoList and status or error and status
     */
    @PostMapping
    public ResponseEntity<?> newTodoList(@RequestBody Map<String, String> newNameTodoList) {
        // Проверки
        // Создание и сохранение в БД
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method for getting all TodoLists
     *
     * @param startId             start id search
     * @param limit               limit getting TodoLists
     * @param sortAndFilterParams params sorted and filters
     * @return all TodoLists and status or error and status
     */
    @GetMapping("all")
    public ResponseEntity<?> getAllTodoLists(@RequestParam(value = "startId", defaultValue = "1") String startId,
                                             @RequestParam(value = "limit", defaultValue = "10") String limit,
                                             @RequestBody Map<String, String> sortAndFilterParams) {
        // Проверки и прочее.
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
