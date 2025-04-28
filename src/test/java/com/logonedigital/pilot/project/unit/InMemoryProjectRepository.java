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
        if (existingProject.isPresent()) {
            Project existing = existingProject.get();
            existing.setTitle(project.getTitle());
            existing.setDescription(project.getDescription());
            existing.setStatus(project.getStatus());
            return existing;
        }
        projects.add(project);
        return project;
    }

    @Override
    public Optional<Project> findByPublicId(PublicId publicId) {
        return projects.stream()
                .filter(project -> project.getPublicId().value().equals(publicId.value()))
                .findFirst();
    }

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(projects);
    }

    @Override
    public void deleteByPublicId(PublicId publicId) {
        projects.removeIf(project -> project.getPublicId().value().equals(publicId.value()));
    }
}