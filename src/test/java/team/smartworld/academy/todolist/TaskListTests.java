package team.smartworld.academy.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TaskListTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTaskListTest() throws Exception {
        mockMvc.perform(get("/todo/api/taskList/2d6adfc6-39a7-7c78-a79c-5f08c876db7e"))
                .andDo(print())
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("list 1")));
    }

    @Test
    void renameTaskListTest() throws Exception {
        mockMvc.perform(get("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("list 1")));
        mockMvc.perform(patch("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e")
                .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"list 1000\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("list 1000")));
    }

    @Test
    void getAllTaskListsTest() throws Exception {
        mockMvc.perform(get("/todo/api/taskList").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("list 1")))
                .andExpect(content().string(containsString("task_list 2")))
                .andExpect(content().string(containsString("lists")));
    }

    @Test
    void createTaskListTest() throws Exception {
        mockMvc.perform(post("/todo/api/taskList")
                .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"my big list\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("id")));
    }

    @Test
    void deleteTaskListTest() throws Exception {
        mockMvc.perform(delete("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}