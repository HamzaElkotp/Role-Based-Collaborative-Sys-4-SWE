package tasks_service.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tasks_service.demo.dto.TaskDTO;
import tasks_service.demo.entity.Tasks;
import tasks_service.demo.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskStatusRepository statusRepository;
    @Autowired
    private TaskPriorityRepository priorityRepository;

    @Transactional
    public TaskDTO createTask(Long projectId, TaskDTO dto) {
        Tasks task = Tasks.builder()
                .projectId(projectId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .creatorId(dto.getCreatorId())
                .status(statusRepository.findById(dto.getStatusId()).orElse(null))
                .priority(priorityRepository.findById(dto.getPriorityId()).orElse(null))
                .build();
        return convertToDTO(taskRepository.save(task));
    }

    public List<TaskDTO> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Transactional
    public TaskDTO updateStatus(Long taskId, Long statusId) {
        Tasks task = taskRepository.findById(taskId).orElseThrow();
        task.setStatus(statusRepository.findById(statusId).orElseThrow());
        return convertToDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO updatePriority(Long taskId, Long priorityId) {
        Tasks task = taskRepository.findById(taskId).orElseThrow();
        task.setPriority(priorityRepository.findById(priorityId).orElseThrow());
        return convertToDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO updateInfo(Long taskId, TaskDTO dto) {
        Tasks task = taskRepository.findById(taskId).orElseThrow();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        return convertToDTO(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private TaskDTO convertToDTO(Tasks task) {
        return TaskDTO.builder()
                .id(task.getId())
                .projectId(task.getProjectId())
                .title(task.getTitle())
                .description(task.getDescription())
                .creatorId(task.getCreatorId())
                .statusId(task.getStatus() != null ? task.getStatus().getId() : null)
                .statusName(task.getStatus() != null ? task.getStatus().getStatusName() : null)
                .priorityId(task.getPriority() != null ? task.getPriority().getId() : null)
                .priorityName(task.getPriority() != null ? task.getPriority().getPriorityName() : null)
                .createdAt(task.getCreatedAt())
                .build();
    }
}