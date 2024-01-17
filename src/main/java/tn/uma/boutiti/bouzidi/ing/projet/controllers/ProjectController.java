package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:3000")
public class ProjectController {

    @Autowired
    private  ProjectService projectService;
    @Autowired
    private TaskService taskService;
 // findAllProjects with pagination
    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> findAllProjects(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectDTO> projectsPage = projectService.findAll(pageable);
        return ResponseEntity.ok().body(projectsPage);
    }

    // findOneProject
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findOneProject(@PathVariable Long id) {
        ProjectDTO project = projectService.findOne(id);
        return project != null
                ? ResponseEntity.ok().body(project)
                : ResponseEntity.notFound().build();
    }

    // assignTaskToProject
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
    
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO ProjectDTO) {
        ProjectDTO Project = projectService.save(ProjectDTO);
        return ResponseEntity.ok().body(Project);
    }

    /*@GetMapping
    public ResponseEntity<List<ProjectDTO>> findAllProjects() {
        List<ProjectDTO> Projects = projectService.findAll();
        return ResponseEntity.ok().body(Projects);
    }*/

    /*@GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findOneProject(@PathVariable Long id) {
        ProjectDTO Project = projectService.findOne(id);
        return ResponseEntity.ok().body(Project);
    }*/

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
       /*@PutMapping("/{projectId}/assignTask/{taskId}")
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
    }*/
}
