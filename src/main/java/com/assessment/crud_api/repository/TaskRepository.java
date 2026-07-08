package com.assessment.crudapi.repository;

import com.assessment.crudapi.model.DeploymentTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<DeploymentTask, Long> {}