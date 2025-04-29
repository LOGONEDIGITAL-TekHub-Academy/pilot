package com.logonedigital.pilot.project.unit;

import com.logonedigital.pilot.project.domain.aggregate.Project;
import com.logonedigital.pilot.project.domain.repository.ProjectRepository;
import com.logonedigital.pilot.project.domain.vo.ProjectDescription;
import com.logonedigital.pilot.project.domain.vo.ProjectStatus;
import com.logonedigital.pilot.project.domain.vo.ProjectTitle;
import com.logonedigital.pilot.shared.domain.PublicId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectUnitTest {

    private ProjectRepository projectRepository;
    private Project testProject;

    @BeforeEach
    void setUp() {
        projectRepository = new InMemoryProjectRepository();

        testProject = Project.builder()
                .publicId(PublicId.fromUuid(UUID.randomUUID()))
                .title(new ProjectTitle("Test Project"))
                .description(new ProjectDescription("Test Description"))
                .status(new ProjectStatus(ProjectStatus.CREATED))
                .build();
    }

    @Test
    void shouldSaveProjectSuccessfully() {
        // When
        Project savedProject = projectRepository.save(testProject);

        // Then
        assertNotNull(savedProject, "Saved project should not be null");
        assertEquals(testProject.getPublicId(), savedProject.getPublicId(), "Public IDs should match");
        assertEquals(testProject.getTitle(), savedProject.getTitle(), "Titles should match");
        assertEquals(testProject.getDescription(), savedProject.getDescription(), "Descriptions should match");
        assertEquals(testProject.getStatus(), savedProject.getStatus(), "Statuses should match");
    }

    @Test
    void shouldFindSavedProjectById() {
        // Given
        Project savedProject = projectRepository.save(testProject);

        // When
        Optional<Project> foundProject = projectRepository.findByPublicId(testProject.getPublicId());

        // Then
        assertTrue(foundProject.isPresent(), "Project should be found");
        assertEquals(savedProject, foundProject.get(), "Found project should match saved project");
    }

    @Test
    void shouldReturnAllSavedProjects() {
        // Given
        Project project1 = projectRepository.save(testProject);

        Project project2 = Project.builder()
                .publicId(PublicId.fromUuid(UUID.randomUUID()))
                .title(new ProjectTitle("Second Project"))
                .description(new ProjectDescription("Second Description"))
                .status(new ProjectStatus(ProjectStatus.IN_PROGRESS))
                .build();
        projectRepository.save(project2);

        // When
        List<Project> allProjects = projectRepository.findAll();

        // Then
        assertEquals(2, allProjects.size(), "Should return all saved projects");
        assertTrue(allProjects.contains(project1), "Should contain first project");
        assertTrue(allProjects.contains(project2), "Should contain second project");
    }

    @Test
    void shouldUpdateExistingProject() {
        // Given
        Project savedProject = projectRepository.save(testProject);

        // When
        Project updatedProject = Project.builder()
                .publicId(savedProject.getPublicId())
                .title(new ProjectTitle("Updated Title"))
                .description(new ProjectDescription("Updated Description"))
                .status(new ProjectStatus(ProjectStatus.COMPLETED))
                .build();

        Project result = projectRepository.save(updatedProject);

        // Then
        assertEquals(savedProject.getPublicId(), result.getPublicId(), "Public ID should remain the same");
        assertEquals("Updated Title", result.getTitle().value(), "Title should be updated");
        assertEquals("Updated Description", result.getDescription().value(), "Description should be updated");
        assertEquals(ProjectStatus.COMPLETED, result.getStatus().value(), "Status should be updated");

        // Verify only one project exists (update, not create)
        assertEquals(1, projectRepository.findAll().size(), "Should only have one project after update");
    }
}
