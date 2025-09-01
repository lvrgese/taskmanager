package Task.Management.API.controller;

import Task.Management.API.dto.TaskRequestDto;
import Task.Management.API.dto.TaskResponseDto;
import Task.Management.API.enums.Priority;
import Task.Management.API.enums.TaskStatus;
import Task.Management.API.exception.TaskNotFoundException;
import Task.Management.API.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    TaskResponseDto exampleResponse;
    @BeforeEach
    void setup() {
        exampleResponse = new TaskResponseDto(
                1L, "Test Task", "Some description", TaskStatus.TO_DO, Priority.MEDIUM, LocalDate.now()
        );
    }

    @Test
    public void testGetTaskById_whenTaskExists() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(exampleResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(exampleResponse)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Some description"))
                .andExpect(jsonPath("$.status").value("TO_DO"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"))
                .andExpect(jsonPath("$.dueDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetTaskById_whenTaskDoesNotExist() throws Exception {
        when(taskService.getTaskById(1L)).thenThrow(new TaskNotFoundException("Task not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTask_withValidData() throws Exception {

        TaskRequestDto req = new TaskRequestDto("New Task", "New description",
                TaskStatus.IN_PROGRESS, Priority.HIGH, LocalDate.now().plusDays(5));
        TaskResponseDto response = new TaskResponseDto(
                2L, "New Task", "New description", TaskStatus.IN_PROGRESS,
                Priority.HIGH, LocalDate.now().plusDays(5)
        );

        when(taskService.createTask(any(TaskRequestDto.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.description").value("New description"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.dueDate").value(LocalDate.now().plusDays(5).toString()));
    }

    @Test
    public void testCreateTask_withInvalidData() throws Exception {
        TaskRequestDto req = new TaskRequestDto("", "New description",
                TaskStatus.IN_PROGRESS, Priority.HIGH, LocalDate.now().plusDays(5));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateTaskById_whenTaskExists() throws Exception {
        TaskRequestDto req = new TaskRequestDto("Updated Task", "Updated description",
                TaskStatus.COMPLETED, Priority.LOW, LocalDate.now().plusDays(10));
        TaskResponseDto response = new TaskResponseDto(
                1L, "Updated Task", "Updated description", TaskStatus.COMPLETED,
                Priority.LOW, LocalDate.now().plusDays(10)
        );

        when(taskService.updateTaskById(any(Long.class), any(TaskRequestDto.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.priority").value("LOW"))
                .andExpect(jsonPath("$.dueDate").value(LocalDate.now().plusDays(10).toString()));
    }

    @Test
    public void testUpdateTaskById_whenTaskDoesNotExist() throws Exception {
        TaskRequestDto req = new TaskRequestDto("Updated Task", "Updated description",
                TaskStatus.COMPLETED, Priority.LOW, LocalDate.now().plusDays(10));

        when(taskService.updateTaskById(any(Long.class), any(TaskRequestDto.class)))
                .thenThrow(new TaskNotFoundException("Task not found"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTaskById_whenTaskExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTaskById_whenTaskDoesNotExist() throws Exception {
        doThrow(new TaskNotFoundException("Task not found"))
                .when(taskService).deleteTaskById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }
}
