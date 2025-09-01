package Task.Management.API.controller;

import Task.Management.API.dto.TaskRequestDto;
import Task.Management.API.dto.TaskResponseDto;
import Task.Management.API.exception.TaskNotFoundException;
import Task.Management.API.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto dto){
        return ResponseEntity
                .status(201)
                .body(taskService.createTask(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTaskById(@PathVariable Long id,@RequestBody @Valid TaskRequestDto dto)
            throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.updateTaskById(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) throws TaskNotFoundException {

        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
