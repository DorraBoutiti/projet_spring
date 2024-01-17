package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.util.List;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	
    @Autowired
    private  ProjectService projectService;
    @Autowired
    private TaskService taskService;
    
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO project = projectService.save(projectDTO);
        return ResponseEntity.ok().body(project);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAllProjects() {
        List<ProjectDTO> projects = projectService.findAll();
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findOneProject(@PathVariable Long id) {
        ProjectDTO project = projectService.findOne(id);
        return ResponseEntity.ok().body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO) {
        if (projectDTO.getId() == null) {
            return createProject(projectDTO);
        }else {
            ProjectDTO project = projectService.save(projectDTO);
            return ResponseEntity.ok().body(project);
        }
    }
    @PutMapping("/{projectId}/assignTask/{taskId}")
    public ResponseEntity<ProjectDTO> assignTaskToProject(@PathVariable Long projectId, @PathVariable Long taskId) {
        ProjectDTO project = projectService.findOne(projectId);
        TaskDTO task = taskService.findOne(taskId);
        if (project != null && task != null) {            
            List<TaskDTO> projectTasks = project.getTasks();
            projectTasks.add(task);            
            project.setTasks(projectTasks);
            projectService.save(project);
            return ResponseEntity.ok().body(project);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
}
