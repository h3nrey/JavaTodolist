package br.com.h3nrey.todolist.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        taskModel.setId((UUID) userId);

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

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
