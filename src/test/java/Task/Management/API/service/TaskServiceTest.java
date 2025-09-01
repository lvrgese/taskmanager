package Task.Management.API.service;

import Task.Management.API.dto.TaskRequestDto;
import Task.Management.API.dto.TaskResponseDto;
import Task.Management.API.entity.Task;
import Task.Management.API.enums.Priority;
import Task.Management.API.enums.TaskStatus;
import Task.Management.API.exception.TaskNotFoundException;
import Task.Management.API.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    Task exampleTask;

    @BeforeEach
    public void beforeEach(){
        exampleTask = new Task("Test","Test description",
                Priority.MEDIUM, TaskStatus.TO_DO, LocalDate.now());
        exampleTask.setId(1L);
    }

    @Test
    public void testGetTaskById_whenTaskExists() throws TaskNotFoundException {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(exampleTask));

        TaskResponseDto dto = taskService.getTaskById(1L);

        assertNotNull(dto);
        assertEquals(dto.getId(),exampleTask.getId());
        assertEquals(dto.getTitle(),exampleTask.getTitle());
        assertEquals(dto.getDescription(),exampleTask.getDescription());
        assertEquals(dto.getPriority(),exampleTask.getPriority());
        assertEquals(dto.getStatus(),exampleTask.getStatus());
        assertEquals(dto.getDueDate(),exampleTask.getDate());
    }

    @Test
    public void testGetTaskById_whenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class,()-> taskService.getTaskById(1L));
    }

    @Test
    public void testCreateTask(){
        when(taskRepository.save(any(Task.class))).thenReturn(exampleTask);

        TaskResponseDto dto = taskService.createTask(
                new TaskRequestDto(exampleTask.getTitle(),exampleTask.getDescription(),
                        exampleTask.getStatus(), exampleTask.getPriority(),exampleTask.getDate()));
        assertNotNull(dto);
        assertEquals(dto.getId(),exampleTask.getId());
        assertEquals(dto.getTitle(),exampleTask.getTitle());
        assertEquals(dto.getDescription(),exampleTask.getDescription());
        assertEquals(dto.getPriority(),exampleTask.getPriority());
        assertEquals(dto.getStatus(),exampleTask.getStatus());
        assertEquals(dto.getDueDate(),exampleTask.getDate());
        verify(taskRepository,times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTaskById_whenTaskExists() throws TaskNotFoundException {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(exampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(exampleTask);

        TaskResponseDto dto = taskService.updateTaskById(1L,
                new TaskRequestDto("Updated Title","Updated Description",
                        TaskStatus.IN_PROGRESS,Priority.HIGH,LocalDate.now().plusDays(5)));
        assertNotNull(dto);
        assertEquals(dto.getId(),exampleTask.getId());
        assertEquals("Updated Title", dto.getTitle());
        assertEquals("Updated Description", dto.getDescription());
        assertEquals(Priority.HIGH, dto.getPriority());
        assertEquals(TaskStatus.IN_PROGRESS, dto.getStatus());
        assertEquals(dto.getDueDate(),LocalDate.now().plusDays(5));
        verify(taskRepository,times(1)).findById(1L);
        verify(taskRepository,times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTaskById_whenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskById(1L,
                new TaskRequestDto("Updated Title", "Updated Description",
                        TaskStatus.IN_PROGRESS, Priority.HIGH, LocalDate.now().plusDays(5))));
    }

    @Test
    public void testDeleteTaskById_whenTaskExists() throws TaskNotFoundException {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(exampleTask));
        doNothing().when(taskRepository).deleteById(1L);
        taskService.deleteTaskById(1L);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTaskById_whenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(1L));
    }
}
