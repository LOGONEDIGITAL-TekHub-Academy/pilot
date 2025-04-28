package com.logonedigital.pilot.project.unit;

import com.logonedigital.pilot.project.domain.aggregate.Project;
import com.logonedigital.pilot.project.domain.repository.ProjectRepository;
import com.logonedigital.pilot.project.domain.vo.PublicId;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryProjectRepository implements ProjectRepository {
    List<Project> projects = new ArrayList<>();

    @Override
    public Project save(Project project) {
        Optional<Project> existingProject = findByPublicId(project.getPublicId());
        existingProject.ifPresent(value -> projects.remove(value));
        projects.add(project);
        return project;
    }

    @Override
    public Optional<Project> findByPublicId(PublicId publicId) {
        return projects.stream()
                .filter(project -> project.getPublicId().equals(publicId))
                .findFirst();
    }

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(projects);
    }
}