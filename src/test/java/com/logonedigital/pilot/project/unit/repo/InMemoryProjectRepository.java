package com.logonedigital.pilot.project.unit.repo;

import com.logonedigital.pilot.project.domain.aggregate.Project;
import com.logonedigital.pilot.project.domain.repository.ProjectRepository;
import com.logonedigital.pilot.project.domain.vo.ProjectDescription;
import com.logonedigital.pilot.project.domain.vo.ProjectStatus;
import com.logonedigital.pilot.project.domain.vo.ProjectTitle;
import com.logonedigital.pilot.project.domain.vo.PublicId;

import java.util.List;
import java.util.UUID;

public class InMemoryProjectRepository implements ProjectRepository {
    List<Project> projects = List.of(
            new Project(
                    new PublicId(UUID.randomUUID()), new ProjectTitle("NAME"),
                        new ProjectDescription("Food NAME"),
                        new ProjectStatus(ProjectStatus.CREATED)),
            new Project(
                        new PublicId(UUID.randomUUID()), new ProjectTitle("WORKTOP"),
                        new ProjectDescription("Human Resources"),
                        new ProjectStatus(ProjectStatus.CREATED))
            );
    @Override
    public void save(Project project) {
        projects.add(project);
    }
}
