package com.logonedigital.pilot.project.domain.repository;

import com.logonedigital.pilot.project.domain.aggregate.Project;
import com.logonedigital.pilot.shared.domain.PublicId;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    Project save(Project project);

    Optional<Project> findByPublicId(PublicId publicId);

    List<Project> findAll();

    void deleteByPublicId(PublicId publicId);
}
