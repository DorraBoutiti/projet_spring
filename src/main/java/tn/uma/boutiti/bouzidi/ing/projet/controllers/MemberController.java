package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private  MemberService memberService;


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

   /* @PutMapping("/{memberId}/projects/{projectId}")
    public ResponseEntity<String> assignProjectToMember(
            @PathVariable Long memberId,
            @PathVariable Long projectId
    ) {
        try {
        	System.out.println(1);
            memberService.assignProjectToMember(memberId, projectId);
            System.out.println(9);
            return ResponseEntity.ok("Project assigned to member successfully");
        } catch (EntityNotFoundException e) {
        	System.out.println(10);
        	return null;
            //return ResponseEntity.notFound().build();
        } catch (Exception e) {
        	System.out.println(11);
        	return null;
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   // .body("Failed to assign project to member");
        }
    }*/
}
