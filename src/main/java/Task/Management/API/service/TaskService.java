package Task.Management.API.service;

import Task.Management.API.dto.TaskRequestDto;
import Task.Management.API.dto.TaskResponseDto;
import Task.Management.API.entity.Task;
import Task.Management.API.exception.TaskNotFoundException;
import Task.Management.API.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDto getTaskById(Long id) throws TaskNotFoundException {
        return convertToResponseDto(getTask(id));
    }

    public TaskResponseDto createTask(TaskRequestDto dto) {
        Task task = new Task(dto.getTitle(), dto.getDescription(),
                dto.getPriority(),dto.getStatus(),dto.getDueDate());
        Task savedTask = taskRepository.save(task);

        return convertToResponseDto(savedTask);
    }

    public TaskResponseDto updateTaskById(Long id,TaskRequestDto dto) throws TaskNotFoundException {
        Task existingTask = getTask(id);
        existingTask.setTitle(dto.getTitle());
        existingTask.setDescription(dto.getDescription());
        existingTask.setPriority(dto.getPriority());
        existingTask.setStatus(dto.getStatus());
        existingTask.setDate(dto.getDueDate());

        return convertToResponseDto(taskRepository.save(existingTask));

    }

    public void deleteTaskById(Long id) throws TaskNotFoundException {
        Task task = getTask(id);
        taskRepository.deleteById(id);
    }

    private Task getTask(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with id" + id + " is not found"));
    }

    private TaskResponseDto convertToResponseDto(Task task){
        return new TaskResponseDto(task.getId(),task.getTitle(),task.getDescription(),
                task.getStatus(),task.getPriority(),task.getDate());
    }
}
