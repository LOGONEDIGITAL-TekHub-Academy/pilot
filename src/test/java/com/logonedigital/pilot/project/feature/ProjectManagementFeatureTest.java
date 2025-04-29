package com.logonedigital.pilot.project.feature;

import com.logonedigital.pilot.project.domain.aggregate.Project;
import com.logonedigital.pilot.project.domain.repository.ProjectRepository;
import com.logonedigital.pilot.project.domain.vo.ProjectDescription;
import com.logonedigital.pilot.project.domain.vo.ProjectStatus;
import com.logonedigital.pilot.project.domain.vo.ProjectTitle;
import com.logonedigital.pilot.project.unit.InMemoryProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Project Management Feature")
class ProjectManagementFeatureTest {

    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        //TODO: Should change this with JpaProjectRepository
        projectRepository = new InMemoryProjectRepository();
    }

    @Nested
    @DisplayName("Project Creation")
    class ProjectCreationFeature {

        @Test
        @DisplayName("Should successfully create a new project")
        void shouldCreateNewProject() {
            // Given
            Project newProject = createTestProject("New Feature Project", "Feature test description");

            // When
            Project savedProject = projectRepository.save(newProject);

            // Then
            assertNotNull(savedProject, "Project should be saved successfully");
            assertEquals(newProject.getTitle(), savedProject.getTitle(), "Title should match");
            assertEquals(ProjectStatus.CREATED, savedProject.getStatus().value(), "Status should be CREATED");

            // Verify a project can be retrieved
            Optional<Project> retrievedProject = projectRepository.findByPublicId(savedProject.getPublicId());
            assertTrue(retrievedProject.isPresent(), "Project should be retrievable after creation");
            assertEquals(savedProject, retrievedProject.get(), "Retrieved project should match saved project");
        }

        @Test
        @DisplayName("Should not allow duplicate project IDs")
        void shouldPreventDuplicateProjectIds() {
            // Given
            PublicId publicId = new PublicId(UUID.randomUUID());
            Project project1 = createTestProject(publicId, "Project 1", "Description 1");
            Project project2 = createTestProject(publicId, "Project 2", "Description 2");

            // When
            projectRepository.save(project1);
            Project secondSaveResult = projectRepository.save(project2);

            // Then
            assertEquals(project1, secondSaveResult, "Saving with same ID should return existing project");
            assertEquals(1, projectRepository.findAll().size(), "Only one project should exist");
        }
    }

    @Nested
    @DisplayName("Project Retrieval")
    class ProjectRetrievalFeature {

        @Test
        @DisplayName("Should retrieve all projects")
        void shouldRetrieveAllProjects() {
            // Given
            Project project1 = projectRepository.save(createTestProject("Project 1", "Desc 1"));
            Project project2 = projectRepository.save(createTestProject("Project 2", "Desc 2"));

            // When
            List<Project> allProjects = projectRepository.findAll();

            // Then
            assertEquals(2, allProjects.size(), "Should return all projects");
            assertTrue(allProjects.contains(project1), "Should contain first project");
            assertTrue(allProjects.contains(project2), "Should contain second project");
        }

        @Test
        @DisplayName("Should find project by ID")
        void shouldFindProjectById() {
            // Given
            Project project = projectRepository.save(createTestProject("Find Me", "Test project"));

            // When
            Optional<Project> foundProject = projectRepository.findByPublicId(project.getPublicId());

            // Then
            assertTrue(foundProject.isPresent(), "Project should be found");
            assertEquals(project, foundProject.get(), "Found project should match");
        }

        @Test
        @DisplayName("Should return empty when project not found")
        void shouldReturnEmptyWhenProjectNotFound() {
            // Given
            PublicId nonExistentId = new PublicId(UUID.randomUUID());

            // When
            Optional<Project> foundProject = projectRepository.findByPublicId(nonExistentId);

            // Then
            assertFalse(foundProject.isPresent(), "Should not find non-existent project");
        }
    }

    @Nested
    @DisplayName("Project Update")
    class ProjectUpdateFeature {

        @Test
        @DisplayName("Should update existing project")
        void shouldUpdateExistingProject() {
            // Given
            Project originalProject = projectRepository.save(createTestProject("Original", "Original desc"));

            // When
            Project updatedProject = Project.builder()
                    .publicId(originalProject.getPublicId())
                    .title(new ProjectTitle("Updated Title"))
                    .description(new ProjectDescription("Updated Description"))
                    .status(new ProjectStatus(ProjectStatus.IN_PROGRESS))
                    .build();

            Project saveResult = projectRepository.save(updatedProject);

            // Then
            assertEquals(originalProject.getPublicId(), saveResult.getPublicId(), "ID should remain the same");
            assertEquals("Updated Title", saveResult.getTitle().value(), "Title should be updated");
            assertEquals("Updated Description", saveResult.getDescription().value(), "Description should be updated");
            assertEquals(ProjectStatus.IN_PROGRESS, saveResult.getStatus().value(), "Status should be updated");

            // Verify only one project exists
            assertEquals(1, projectRepository.findAll().size(), "Should only have one project after update");
        }

        @Test
        @DisplayName("Should transition project status properly")
        void shouldTransitionProjectStatus() {
            // Given
            Project project = projectRepository.save(createTestProject("Status Test", "Test status transitions"));

            // When & Then: CREATED -> IN_PROGRESS
            project = updateProjectStatus(project, ProjectStatus.IN_PROGRESS);
            assertEquals(ProjectStatus.IN_PROGRESS, project.getStatus().value());

            // When & Then: IN_PROGRESS -> COMPLETED
            project = updateProjectStatus(project, ProjectStatus.COMPLETED);
            assertEquals(ProjectStatus.COMPLETED, project.getStatus().value());
        }
    }

    @Nested
    @DisplayName("Project Deletion")
    class ProjectDeletionFeature {

        @Test
        @DisplayName("Should delete project successfully")
        void shouldDeleteProjectSuccessfully() {
            // Given
            Project project = projectRepository.save(createTestProject("To Delete", "Delete me"));

            // When
            projectRepository.deleteByPublicId(project.getPublicId());

            // Then
            Optional<Project> deletedProject = projectRepository.findByPublicId(project.getPublicId());
            assertFalse(deletedProject.isPresent(), "Project should be deleted");
            assertTrue(projectRepository.findAll().isEmpty(), "No projects should remain");
        }

        @Test
        @DisplayName("Should not fail when deleting non-existent project")
        void shouldNotFailWhenDeletingNonExistentProject() {
            // Given
            PublicId nonExistentId = new PublicId(UUID.randomUUID());

            // When & Then (should not throw exception)
            assertDoesNotThrow(() -> projectRepository.deleteByPublicId(nonExistentId));
        }
    }

    // Helper methods
    private Project createTestProject(String title, String description) {
        return Project.builder()
                .publicId(new PublicId(UUID.randomUUID()))
                .title(new ProjectTitle(title))
                .description(new ProjectDescription(description))
                .status(new ProjectStatus(ProjectStatus.CREATED))
                .build();
    }

    private Project createTestProject(PublicId publicId, String title, String description) {
        return Project.builder()
                .publicId(publicId)
                .title(new ProjectTitle(title))
                .description(new ProjectDescription(description))
                .status(new ProjectStatus(ProjectStatus.CREATED))
                .build();
    }

    private Project updateProjectStatus(Project project, String newStatus) {
        Project updated = Project.builder()
                .publicId(project.getPublicId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(new ProjectStatus(newStatus.toUpperCase()))
                .build();
        return projectRepository.save(updated);
    }
}
