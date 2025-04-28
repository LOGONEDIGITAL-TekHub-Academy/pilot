package com.logonedigital.pilot.project.domain.repository;

import com.logonedigital.pilot.project.domain.aggregate.Project;

public interface ProjectRepository {

    void save(Project project);
}
