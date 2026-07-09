package com.assessment.crud_api.repository;

import com.assessment.crud_api.model.DeploymentTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<DeploymentTask, Long> {}
