package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.MemberService;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;
import java.util.List;


@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private  MemberService memberService;
    @Autowired
    private ProjectService projectService;


    @PostMapping
    public ResponseEntity<MemberDTO> create(@RequestBody MemberDTO memberDTO) {
        MemberDTO member = memberService.save(memberDTO);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> members = memberService.findAll();
        return ResponseEntity.ok().body(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> findOne(@PathVariable Long id) {
        MemberDTO member = memberService.findOne(id);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/tasks/{username}")
    public ResponseEntity<List<ProjectDTO>> getAllProjectsAndTasksByUsername(@PathVariable String username) {
        List<ProjectDTO> listProject = memberService.getAllProjectsAndTasksByUsername(username);
        return ResponseEntity.ok().body(listProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<MemberDTO> update(@RequestBody MemberDTO memberDTO) {
        if (memberDTO.getId() == null) {
            return create(memberDTO);
        }else {
            MemberDTO member = memberService.save(memberDTO);
            return ResponseEntity.ok().body(member);
        }
    }
    @PutMapping("/{memberId}/assignProject/{projectId}")
    public ResponseEntity<MemberDTO> assignProjectToMember(@PathVariable Long memberId, @PathVariable Long projectId) {
        MemberDTO member = memberService.findOne(memberId);
        ProjectDTO project = projectService.findOne(projectId);

        if (member != null && project != null) {
            List<ProjectDTO> memberProjects = member.getProjects();
            memberProjects.add(project);
            member.setProjects(memberProjects);
            memberService.save(member);
            return ResponseEntity.ok().body(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
