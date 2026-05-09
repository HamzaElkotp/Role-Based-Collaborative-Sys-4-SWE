package tasks_service.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasks_service.demo.dto.TaskDTO;
import tasks_service.demo.service.TaskService;
import java.util.List;

@RestController
@RequestMapping("/projects/{project_id}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long project_id, @RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.createTask(project_id, taskDTO), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(@PathVariable Long project_id) {
        return ResponseEntity.ok(taskService.getTasksByProject(project_id));
    }


    @GetMapping("/{task_id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long project_id, @PathVariable Long task_id) {
        return ResponseEntity.ok(taskService.getTaskById(task_id));
    }


    @PatchMapping("/{task_id}/change-task-status")
    public ResponseEntity<TaskDTO> changeStatus(@PathVariable Long project_id, @PathVariable Long task_id, @RequestParam Long statusId) {
        return ResponseEntity.ok(taskService.updateStatus(task_id, statusId));
    }


    @PatchMapping("/{task_id}/change-task-priority")
    public ResponseEntity<TaskDTO> changePriority(@PathVariable Long project_id, @PathVariable Long task_id, @RequestParam Long priorityId) {
        return ResponseEntity.ok(taskService.updatePriority(task_id, priorityId));
    }


    @PutMapping("/{task_id}/update-task-info")
    public ResponseEntity<TaskDTO> updateInfo(@PathVariable Long project_id, @PathVariable Long task_id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateInfo(task_id, taskDTO));
    }


    @DeleteMapping("/{task_id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long project_id, @PathVariable Long task_id) {
        taskService.deleteTask(task_id);
        return ResponseEntity.noContent().build();
    }
}