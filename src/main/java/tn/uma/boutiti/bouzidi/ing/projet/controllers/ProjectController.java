package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;

import java.util.List;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private  ProjectService projectService;
    
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO ProjectDTO) {
        ProjectDTO Project = projectService.save(ProjectDTO);
        return ResponseEntity.ok().body(Project);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAllProjects() {
        List<ProjectDTO> Projects = projectService.findAll();
        return ResponseEntity.ok().body(Projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findOneProject(@PathVariable Long id) {
        ProjectDTO Project = projectService.findOne(id);
        return ResponseEntity.ok().body(Project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO ProjectDTO) {
        if (ProjectDTO.getId() == null) {
            return createProject(ProjectDTO);
        }else {
            ProjectDTO Project = projectService.save(ProjectDTO);
            return ResponseEntity.ok().body(Project);
        }
    }
}
