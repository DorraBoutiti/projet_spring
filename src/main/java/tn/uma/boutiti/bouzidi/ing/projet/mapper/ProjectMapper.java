package tn.uma.boutiti.bouzidi.ing.projet.mapper;

import org.mapstruct.Mapper;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;

@Mapper(componentModel = "spring", uses = {TaskMapper.class, MemberMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

    ProjectDTO toDto(Project project);

    Project toEntity(ProjectDTO projectDTO);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }

}
