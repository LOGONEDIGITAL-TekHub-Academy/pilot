package com.logonedigital.pilot.project.e2e;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logonedigital.pilot.project.infrastructure.primary.dto.ProjectRequest;
import com.logonedigital.pilot.project.infrastructure.primary.dto.ProjectResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Project E2E Tests")
class ProjectE2ETest extends BaseE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

   /* @Nested
    @DisplayName("POST /api/projects")
    class CreateProjectTests {

        @Test
        @DisplayName("Should create project successfully with valid data")
        void shouldCreateProject() {
            // Given
            ProjectRequest request = new ProjectRequest(
                    "E2E Test Project",
                    "E2E Test Description",
                    "CREATED"
            );

            HttpEntity<ProjectRequest> entity = new HttpEntity<>(request, headers);

            // When
            ResponseEntity<ProjectResponse> response = restTemplate.exchange(
                    getBaseUrl(),
                    HttpMethod.POST,
                    entity,
                    ProjectResponse.class
            );

            // Then
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().publicId());
            assertEquals(request.title(), response.getBody().title());
            assertEquals(request.description(), response.getBody().description());
            assertEquals(request.status(), response.getBody().status());

            // Verify location header
            assertNotNull(response.getHeaders().getLocation());

            // Verify can retrieve the created project
            ResponseEntity<ProjectResponse> getResponse = restTemplate.getForEntity(
                    response.getHeaders().getLocation(),
                    ProjectResponse.class
            );
            assertEquals(HttpStatus.OK, getResponse.getStatusCode());
            assertEquals(response.getBody(), getResponse.getBody());
        }

        @Test
        @DisplayName("Should return 400 for invalid project data")
        void shouldReturnBadRequestForInvalidData() {
            // Given
            ProjectRequest invalidRequest = new ProjectRequest(
                    "", // invalid empty title
                    "", // invalid empty description
                    "INVALID_STATUS" // invalid status
            );

            HttpEntity<ProjectRequest> entity = new HttpEntity<>(invalidRequest, headers);

            // When
            ResponseEntity<String> response = restTemplate.exchange(
                    getBaseUrl(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Then
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("Validation failed"));
        }
    }

    @Nested
    @DisplayName("GET /api/projects")
    class GetProjectTests {

        private ProjectResponse createdProject;

        @BeforeEach
        void setUp() {
            // Create a test project
            ProjectRequest request = new ProjectRequest(
                    "Test Project",
                    "Test Description",
                    "CREATED"
            );
            ResponseEntity<ProjectResponse> response = restTemplate.postForEntity(
                    getBaseUrl(),
                    new HttpEntity<>(request, headers),
                    ProjectResponse.class
            );
            createdProject = response.getBody();
        }

        @Test
        @DisplayName("Should retrieve project by ID")
        void shouldGetProjectById() {
            // When
            ResponseEntity<ProjectResponse> response = restTemplate.getForEntity(
                    getBaseUrl() + "/" + createdProject.publicId(),
                    ProjectResponse.class
            );

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(createdProject, response.getBody());
        }

        @Test
        @DisplayName("Should return 404 for non-existent project")
        void shouldReturnNotFoundForNonExistentProject() {
            // Given
            UUID nonExistentId = UUID.randomUUID();

            // When
            ResponseEntity<String> response = restTemplate.getForEntity(
                    getBaseUrl() + "/" + nonExistentId,
                    String.class
            );

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("Project not found"));
        }

        @Test
        @DisplayName("Should retrieve all projects")
        void shouldGetAllProjects() throws Exception {
            // Given - create a second project
            ProjectRequest request = new ProjectRequest(
                    "Second Project",
                    "Second Description",
                    "IN_PROGRESS"
            );
            restTemplate.postForEntity(
                    getBaseUrl(),
                    new HttpEntity<>(request, headers),
                    ProjectResponse.class
            );

            // When
            ResponseEntity<String> response = restTemplate.getForEntity(
                    getBaseUrl(),
                    String.class
            );

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            List<ProjectResponse> projects = objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<>() {}
            );

            assertTrue(projects.size() >= 2);
            assertTrue(projects.stream().anyMatch(p -> p.title().equals("Test Project")));
            assertTrue(projects.stream().anyMatch(p -> p.title().equals("Second Project")));
        }
    }

    @Nested
    @DisplayName("PUT /api/projects/{id}")
    class UpdateProjectTests {

        private ProjectResponse createdProject;

        @BeforeEach
        void setUp() {
            // Create a test project
            ProjectRequest request = new ProjectRequest(
                    "Initial Project",
                    "Initial Description",
                    "CREATED"
            );
            ResponseEntity<ProjectResponse> response = restTemplate.postForEntity(
                    getBaseUrl(),
                    new HttpEntity<>(request, headers),
                    ProjectResponse.class
            );
            createdProject = response.getBody();
        }

        @Test
        @DisplayName("Should update project successfully")
        void shouldUpdateProject() {
            // Given
            ProjectRequest updateRequest = new ProjectRequest(
                    "Updated Project",
                    "Updated Description",
                    "IN_PROGRESS"
            );

            HttpEntity<ProjectRequest> entity = new HttpEntity<>(updateRequest, headers);

            // When
            ResponseEntity<ProjectResponse> response = restTemplate.exchange(
                    getBaseUrl() + "/" + createdProject.publicId(),
                    HttpMethod.PUT,
                    entity,
                    ProjectResponse.class
            );

            // Then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(createdProject.publicId(), response.getBody().publicId());
            assertEquals(updateRequest.title(), response.getBody().title());
            assertEquals(updateRequest.description(), response.getBody().description());
            assertEquals(updateRequest.status(), response.getBody().status());

            // Verify the update persisted
            ResponseEntity<ProjectResponse> getResponse = restTemplate.getForEntity(
                    getBaseUrl() + "/" + createdProject.publicId(),
                    ProjectResponse.class
            );
            assertEquals(response.getBody(), getResponse.getBody());
        }

        @Test
        @DisplayName("Should return 404 when updating non-existent project")
        void shouldReturnNotFoundWhenUpdatingNonExistentProject() {
            // Given
            UUID nonExistentId = UUID.randomUUID();
            ProjectRequest updateRequest = new ProjectRequest(
                    "Non-existent Project",
                    "Should fail",
                    "CREATED"
            );

            HttpEntity<ProjectRequest> entity = new HttpEntity<>(updateRequest, headers);

            // When
            ResponseEntity<String> response = restTemplate.exchange(
                    getBaseUrl() + "/" + nonExistentId,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("Project not found"));
        }
    }

    @Nested
    @DisplayName("DELETE /api/projects/{id}")
    class DeleteProjectTests {

        private ProjectResponse createdProject;

        @BeforeEach
        void setUp() {
            // Create a test project
            ProjectRequest request = new ProjectRequest(
                    "Project to Delete",
                    "Will be deleted",
                    "CREATED"
            );
            ResponseEntity<ProjectResponse> response = restTemplate.postForEntity(
                    getBaseUrl(),
                    new HttpEntity<>(request, headers),
                    ProjectResponse.class
            );
            createdProject = response.getBody();
        }

        @Test
        @DisplayName("Should delete project successfully")
        void shouldDeleteProject() {
            // When
            ResponseEntity<Void> response = restTemplate.exchange(
                    getBaseUrl() + "/" + createdProject.publicId(),
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );

            // Then
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

            // Verify a project is really deleted
            ResponseEntity<String> getResponse = restTemplate.getForEntity(
                    getBaseUrl() + "/" + createdProject.publicId(),
                    String.class
            );
            assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        }

        @Test
        @DisplayName("Should return 404 when deleting non-existent project")
        void shouldReturnNotFoundWhenDeletingNonExistentProject() {
            // Given
            UUID nonExistentId = UUID.randomUUID();

            // When
            ResponseEntity<String> response = restTemplate.exchange(
                    getBaseUrl() + "/" + nonExistentId,
                    HttpMethod.DELETE,
                    null,
                    String.class
            );

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("Project not found"));
        }
    }

    @Nested
    @DisplayName("Project Status Transitions")
    class ProjectStatusTransitionTests {

        @Test
        @DisplayName("Should transition status from CREATED to IN_PROGRESS")
        void shouldTransitionFromCreatedToInProgress() {
            // Given - create project
            ProjectRequest createRequest = new ProjectRequest(
                    "Status Transition Test",
                    "Testing status transitions",
                    "CREATED"
            );
            ResponseEntity<ProjectResponse> createResponse = restTemplate.postForEntity(
                    getBaseUrl(),
                    new HttpEntity<>(createRequest, headers),
                    ProjectResponse.class
            );
            ProjectResponse project = createResponse.getBody();

            // When - update to IN_PROGRESS
            assertNotNull(project);
            ProjectRequest updateRequest = new ProjectRequest(
                    project.title(),
                    project.description(),
                    "IN_PROGRESS"
            );
            ResponseEntity<ProjectResponse> updateResponse = restTemplate.exchange(
                    getBaseUrl() + "/" + project.publicId(),
                    HttpMethod.PUT,
                    new HttpEntity<>(updateRequest, headers),
                    ProjectResponse.class
            );

            // Then
            assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
            assertNotNull(updateResponse.getBody());
            assertEquals("IN_PROGRESS", updateResponse.getBody().status());
        }

        @Test
        @DisplayName("Should reject invalid status transition")
        void shouldRejectInvalidStatusTransition() {
            // Given - create project
            ProjectRequest createRequest = new ProjectRequest(
                    "Invalid Transition Test",
                    "Testing invalid transitions",
                    "CREATED"
            );
            ResponseEntity<ProjectResponse> createResponse = restTemplate.postForEntity(
                    getBaseUrl(),
                    new HttpEntity<>(createRequest, headers),
                    ProjectResponse.class
            );
            ProjectResponse project = createResponse.getBody();

            // When - try invalid transition to ARCHIVE
            assertNotNull(project);
            ProjectRequest invalidRequest = new ProjectRequest(
                    project.title(),
                    project.description(),
                    "ARCHIVED"
            );
            ResponseEntity<String> response = restTemplate.exchange(
                    getBaseUrl() + "/" + project.publicId(),
                    HttpMethod.PUT,
                    new HttpEntity<>(invalidRequest, headers),
                    String.class
            );

            // Then
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("Invalid status transition"));
        }
    }*/
}