package com.logonedigital.pilot.project.domain.aggregate;

import com.logonedigital.pilot.project.domain.vo.ProjectDescription;
import com.logonedigital.pilot.project.domain.vo.ProjectStatus;
import com.logonedigital.pilot.project.domain.vo.ProjectTitle;
import com.logonedigital.pilot.project.domain.vo.PublicId;
import lombok.Builder;
import java.util.UUID;

@Builder
public class Project {

    private Long id;
    private PublicId publicId;
    private ProjectTitle title;
    private ProjectDescription description;
    private ProjectStatus status;

    public Project() {}
    public Project(PublicId publicId, ProjectTitle title, ProjectDescription description, ProjectStatus status) {
        this.publicId = publicId;
        this.title = title;
        this.status = status;
        this.description = description;
    }
/*    private TechDreamer owner;
    private TechMentor mentor;
    private List<Task> tasks;
    private List<Document> documents;
    private List<Evaluation> evaluations;*/

    public void initDefaultFields() {
        this.publicId = new PublicId(UUID.randomUUID());
    }

}
