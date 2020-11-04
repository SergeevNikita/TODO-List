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
public class TaskTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e/task/2d6adfc6-39a4-4c78-a39c-1f03c876db5a"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void markDoneTask() throws Exception {
        mockMvc.perform(patch("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e/task/2d6adfc6-39a4-4c78-a39c-1f03c876db5a")
                .contentType(MediaType.APPLICATION_JSON).content("{\"done\":\"true\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"done\":true")));
    }

    @Test
    void getTask() throws Exception {
        mockMvc.perform(get("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e/task/2d6adfc6-39a4-4c78-a39c-1f03c876db5a"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"task-1\"")));
    }

    @Test
    void changeTask() throws Exception {
        mockMvc.perform(put("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e/task/2d6adfc6-39a4-4c78-a39c-1f03c876db5a")
                .contentType(MediaType.APPLICATION_JSON).content("{\"done\":\"true\", \"name\":\"my task\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"my task\"")))
                .andExpect(content().string(containsString("\"done\":true")));
    }

    @Test
    void newTask() throws Exception {
        mockMvc.perform(post("/todo/api/taskList/2d6adfc6-39a4-4c78-a39c-5f08c876db2e/task")
                .contentType(MediaType.APPLICATION_JSON).content("{\"title\":\"task title\", \"name\":\"my task name\", \"priority\":\"4\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("id")));
    }
}
