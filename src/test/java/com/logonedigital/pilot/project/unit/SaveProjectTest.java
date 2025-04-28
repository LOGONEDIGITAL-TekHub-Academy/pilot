package com.logonedigital.pilot.project.unit;

import com.logonedigital.pilot.project.domain.aggregate.Project;
import com.logonedigital.pilot.project.domain.repository.ProjectRepository;
import com.logonedigital.pilot.project.domain.vo.ProjectDescription;
import com.logonedigital.pilot.project.domain.vo.ProjectStatus;
import com.logonedigital.pilot.project.domain.vo.ProjectTitle;
import com.logonedigital.pilot.project.domain.vo.PublicId;
import com.logonedigital.pilot.project.unit.repo.InMemoryProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class SaveProjectTest {

    private final ProjectRepository projectRepository;
    List<Project> projects = new ArrayList<>();

    public SaveProjectTest(ProjectRepository projectRepository) {
        this.projectRepository = new InMemoryProjectRepository();;
    }


    @BeforeEach
    void setUp() {

    }

    @Test
    void testCanSaveProject() {
        //Given
        Project projectToSave = Project.builder()
                .publicId(new PublicId(UUID.randomUUID()))
                .title(new ProjectTitle("Title Project 1"))
                .build();

        projectRepository.save(projectToSave);



    }
}
