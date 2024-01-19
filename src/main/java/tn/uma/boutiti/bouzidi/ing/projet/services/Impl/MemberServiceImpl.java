package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.MemberNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.MemberMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
 
 
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.models.Role;
 
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.MemberService;

 
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

 
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public MemberDTO save(MemberDTO memberDTO) {
        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        return memberMapper.toDto(member);
    }


    @Override
    public List<MemberDTO> findAll() {
    return memberMapper.toDto(memberRepository.findAll());
    }

    @Override
    public MemberDTO findOne(Long id) {
        Optional<Member> member =memberRepository.findById(id);
        return memberMapper.toDto(member.get());
    }

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

   /* @Override
    public List<ProjectDTO> getAllProjectsAndTasksByUsername(String username) {
        List<Member> members = memberRepository.findByUsername(username);

        if (!members.isEmpty()) {
            Member member = members.get(0); // Assuming username is unique

            return memberMapper.toDto(member).getProjects();
        }

        return Collections.emptyList();
    }*/

    @Override
    public List<ProjectDTO> getAllProjectsAndTasksByUsername(String username) {
	
	   Optional<Member> members = memberRepository.findByUsername(username);

        if (!members.isEmpty()) {
            Member member = members.get(); 

            return memberMapper.toDto(member).getProjects();
        }

        return Collections.emptyList();
    }


    @Override
    @Transactional 
    public void deleteMemberAndRelatedEntities(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId); 

        if (member != null) {
        	Member m = member.get();
        	//m.setProjects(new ArrayList<>());
            //List<Project> projects = m.getProjects(); 

            //for (Project project : projects) {                
            //    projectRepository.deleteById(project.getId());
            //}
        	//memberRepository.save(m);
            memberRepository.deleteById(m.getId());
        }
    }


    @Override
    public void changeMemberPassword(Long memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
        member.setPassword(newPassword);
        memberRepository.save(member);
    }
    @Override
    public void changeMemberRole(Long memberId, String newRole) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
        if(newRole.equals("USER")){
        	member.setRole(Role.USER);
        }else if (newRole.equals("ADMIN"))
        {
        	member.setRole(Role.ADMIN);	
        }       
        
        memberRepository.save(member);
    }

}
