package tn.uma.boutiti.bouzidi.ing.projet.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.EntityNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

 
    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }
    
    public Member createMember(String username,String password) {
    	Member member = new Member();
    	member.setUsername(username);
    	member.setPassword(password);
    	return member;
    }

    
    public Optional<List<Member>> findMemberByUsername(String username) {
        return Optional.of(memberRepository.findByUsername(username));
    }

   
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    @Transactional
    public void assignProjectToMember(Long memberId, Long projectId) {
        // Fetch Member and Project entities from the database
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Adding the Project to the Member's projects
        member.getProjects().add(project);

        // Adding the Member to the Project's members
        project.getMembers().add(member);

        // Save/update the entities
        memberRepository.save(member);
        projectRepository.save(project);
    }
}
