package Task.Management.API.repository;

import Task.Management.API.entity.Task;
import Task.Management.API.enums.Priority;
import Task.Management.API.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFindByTitle_WhenTaskExists(){
        Task task = new Task("Test","Test description", Priority.MEDIUM, TaskStatus.TO_DO, LocalDate.now());

        taskRepository.save(task);

        Optional<Task> res = taskRepository.findByTitle("Test");

        assertTrue(res.isPresent());
        Task resTask = res.get();
        assertEquals(resTask.getTitle(),task.getTitle());
        assertEquals(resTask.getDescription(),task.getDescription());
        assertEquals(resTask.getPriority(),task.getPriority());
        assertEquals(resTask.getStatus(),task.getStatus());
        assertEquals(resTask.getDate(),task.getDate());
    }

    @Test
    public void testFindByTitle_WhenTaskNotExists(){
        Optional<Task> res = taskRepository.findByTitle("Unknown Task");

        assertTrue(res.isEmpty());
    }
}
