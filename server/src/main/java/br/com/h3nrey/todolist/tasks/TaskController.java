package br.com.h3nrey.todolist.tasks;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.h3nrey.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        // Setting user id
        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        // Validate start date
        var curDate = LocalDateTime.now();
        var taskStartDate = taskModel.getStartAt();
        var taskEndDate = taskModel.getEndAt();

        // ---> Check if current date is after of start/end date
        if (curDate.isAfter(taskStartDate) || curDate.isAfter(taskEndDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio e de termino precisam ser depois da data atual");
        }

        // ---> Check if start date is after of end date
        if (taskStartDate.isAfter(taskEndDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio precisa ser depois da data de término");
        }

        System.out.println("task created with this user id: " + taskModel.getUserId());
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> List(HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        System.out.println("user id: " + userId);
        var tasks = this.taskRepository.findByUserId((UUID) userId);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity UpdateTask(@RequestBody TaskModel taskModel, HttpServletRequest request,
            @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);
        var userId = request.getAttribute("userId");

        // Check if task exists
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task não foi encontrada");
        }

        // Check if the task user is the current user
        if (!task.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão para alterar tarefa");
        }

        Utils.CopyNonNullProperties(taskModel, task);

        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
