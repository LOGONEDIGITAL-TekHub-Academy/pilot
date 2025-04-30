package com.logonedigital.pilot.project.domain.aggregate;

import com.logonedigital.pilot.project.domain.enums.EventState;
import com.logonedigital.pilot.project.domain.vo.ProjectDescription;
import com.logonedigital.pilot.project.domain.vo.ProjectStatus;
import com.logonedigital.pilot.project.domain.vo.ProjectTitle;
import com.logonedigital.pilot.shared.domain.PublicId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Project {

    private Long dbId;
    private PublicId publicId;
    private ProjectTitle title;
    private ProjectDescription description;
    private ProjectStatus status;
    private EventState eventState;


    public Project() {}
    public Project(PublicId publicId, ProjectTitle title, ProjectDescription description, ProjectStatus status) {
        this.publicId = publicId;
        this.title = title;
        this.status = status;
        this.description = description;
    }
}
