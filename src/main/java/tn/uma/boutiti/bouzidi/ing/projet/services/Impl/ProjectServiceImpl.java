package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.ProjectMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;


    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDTO findOne(Long id) {
          Optional<Project> project =projectRepository.findById(id);
            return projectMapper.toDto(project.get());
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }
    
}
