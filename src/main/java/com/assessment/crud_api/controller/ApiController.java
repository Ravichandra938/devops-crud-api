package com.assessment.crud_api.controller;

import com.assessment.crudapi.model.DeploymentTask;
import com.assessment.crudapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DataSource dataSource;

    // Fulfills the read/write requirement
    @GetMapping("/tasks")
    public List<DeploymentTask> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public DeploymentTask createTask(@RequestBody DeploymentTask task) {
        return taskRepository.save(task);
    }

    // Fulfills the strict database health check requirement
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("app_status", "UP");
        
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) { // 2-second timeout check
                statusMap.put("database_status", "CONNECTED");
                return ResponseEntity.ok(statusMap);
            } else {
                throw new Exception("Database connection invalid.");
            }
        } catch (Exception e) {
            statusMap.put("app_status", "UP");
            statusMap.put("database_status", "DISCONNECTED");
            statusMap.put("error", e.getMessage());
            // Returns a 500 error so Jenkins knows the deployment failed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusMap);
        }
    }
}
