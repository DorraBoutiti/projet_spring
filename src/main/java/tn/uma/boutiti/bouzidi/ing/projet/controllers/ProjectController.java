package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.uma.boutiti.bouzidi.ing.projet.exceptions.EntityNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;      
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestParam String name) {
        Project project = projectService.createProject(name);
        return ResponseEntity.ok("Project created with ID: " + project.getId());
    }

    @PostMapping("/{projectId}/assign-task")
    public ResponseEntity<String> assignTaskToProject(
            @PathVariable Long projectId,
            @RequestParam Long taskId
    ) {
        projectService.assignTaskToProject(taskId, projectId);
        return ResponseEntity.ok("Task assigned to the project successfully");
    }
}
