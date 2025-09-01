package Task.Management.API.entity;

import Task.Management.API.enums.Priority;
import Task.Management.API.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    private String description;

    @NotNull(message = "Priority is required")
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

    @NotNull(message = "Status is required")
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    public Task(String title, String description, Priority priority, TaskStatus status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return dueDate;
    }

    public void setDate(LocalDate date) {
        this.dueDate = date;
    }
}
