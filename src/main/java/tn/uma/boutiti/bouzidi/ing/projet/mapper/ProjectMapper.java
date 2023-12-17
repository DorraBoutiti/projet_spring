package tn.uma.boutiti.bouzidi.ing.projet.mapper;

import org.springframework.stereotype.Component;

import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;

@Component
public class ProjectMapper {

    public ProjectDTO toProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
               
        return projectDTO;
    }

    public Project toProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
       
        
        return project;
    }
}
