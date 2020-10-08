package team.smartworld.academy.todolist.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.smartworld.academy.todolist.models.TodoList;

import java.util.List;

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
     */
    @RequestMapping(value = "/delete_todo_list",
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
     * @return TodoList
     */
    @GetMapping("/get_todo_list/{id}")
    public TodoList getTodoList(@PathVariable String id) {
        //return new TodoList(1L, new Date(), new Date(), "List", true);
        return null;
    }

    /**
     * Method for rename TodoLIst
     *
     * @param newNameTodoList new name for TodoList
     * @param id              is the id for search TodoList in DB
     * @return TodoList
     */
    // TODO найти больше инфы и доработать!!!
    @PutMapping("/change_todo_list/{id}")
    public TodoList changeTodoList(@RequestBody String newNameTodoList, @PathVariable String id) {
        return null;
    }

    /**
     * Method for create TodoList
     *
     * @param newTodoList new TodoList date
     * @return TodoList
     */
    // TODO найти больше инфы и доработать!!!
    @PostMapping("/new_todo_list")
    public TodoList newTodoList(@RequestBody TodoList newTodoList) {
        return null;
    }

    /**
     * Method for getting all TodoLists
     *
     * @param id    start id search
     * @param limit limit getting TodoLists
     * @return all TodoLists
     */
    // TODO найти больше инфы и доработать!!!
    @GetMapping("/get_all_todo_lists")
    public List<TodoList> getAllTodoLists(@RequestParam(value = "id", defaultValue = "1") String id,
                                          @RequestParam(value = "limit", defaultValue = "10") String limit) {
        return null;
    }
}
