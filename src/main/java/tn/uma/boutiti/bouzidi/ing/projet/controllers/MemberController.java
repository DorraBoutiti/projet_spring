package tn.uma.boutiti.bouzidi.ing.projet.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.uma.boutiti.bouzidi.ing.projet.exceptions.EntityNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.services.MemberService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<Member> createMember(
    		@RequestParam String username,
    		@RequestParam String password) {
        Member createdMember = memberService.createMember(username, password);
        
        if (createdMember != null) {
            return ResponseEntity.ok(createdMember);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   
    @GetMapping("all-members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) {
        Optional<Member> member = memberService.findMemberById(id);
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable("id") Long id) {
        memberService.deleteMemberById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{memberId}/projects/{projectId}")
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
    }
}
