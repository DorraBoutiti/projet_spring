package tn.uma.boutiti.bouzidi.ing.projet.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.EntityNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.UsernameNotUniqueException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.MemberService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public MemberController(MemberService memberService, MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
		this.projectRepository = projectRepository;
    }
    @GetMapping("/create")
    public String showCreateMemberForm() {
        return "login"; 
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createMember(
            @RequestParam String username,
            @RequestParam String password) {
        try {
            Member createdMember = memberService.createMember(username, password);
            return ResponseEntity.ok("Member created with ID: " + createdMember.getId());
        } catch (UsernameNotUniqueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists. Please choose another username.");
        }
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password, HttpSession session) {
        try {
            List<Member> members = memberRepository.findByUsername(username);

            if (!members.isEmpty()) {
                Member member = members.get(0);
                // Check if the password matches
                if (member.getPassword().equals(password)) {
                    // Authentication successful, store member ID in session
                    session.setAttribute("loggedInMemberId", member.getId());
                    return ResponseEntity.ok().body(member);
                } else {
                    // Authentication failed
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
                }
            } else {
                // Member with the given username not found
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during sign-in");
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
    @GetMapping("/members/{memberId}/projects")
    public ResponseEntity<List<Project>> getProjectsByMemberId(@PathVariable Long memberId) {
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

            List<Project> projects = projectRepository.findByMembers(member);

            return ResponseEntity.ok(projects);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
