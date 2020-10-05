package team.smartworld.academy.todolist.controllers;

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
    @DeleteMapping("/delete_todo_list/{id}")
    public void deleteTodoList(@PathVariable String id) {
        //добавить проверки ID
        //  repository.deleteById(id);
    }

    /**
     * Method for getting TodoList
     *
     * @param id is the id for search TodoList in DB
     * @return TodoList
     */
    @GetMapping("/get_todo_list/{id}")
    public TodoList getTodoList(@PathVariable String id) {

        return null;
    }

    /**
     * Method for rename TodoLIst
     *
     * @param newNameTodoList new name for TodoList
     * @param id              is the id for search TodoList in DB
     * @return TodoList
     */
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
    @PostMapping("/new_todo_list")
    public TodoList newTodoList(@RequestBody TodoList newTodoList) {
        return null;
    }

    /**
     * Method for getting all TodoLists
     *
     * @param id start id search
     * @return all TodoLists
     */
    @GetMapping("/get_all_todo_lists")
    public List<TodoList> getAllTodoLists(@RequestParam(value = "id", defaultValue = "1") String id,
                                          @RequestParam(value = "limit", defaultValue = "10") String limit) {
        return null;
    }
}
