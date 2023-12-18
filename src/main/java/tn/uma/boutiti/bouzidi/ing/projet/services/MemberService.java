package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;

import java.util.List;


public interface MemberService {


    MemberDTO save(MemberDTO memberDTO) ;


    List<MemberDTO> findAll();

    MemberDTO findOne(Long id);

    void delete(Long id);


   /* @Transactional
    public void assignProjectToMember(Long memberId, Long projectId) {
    	System.out.println(2);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
        System.out.println(3);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
        System.out.println(4);
        member.getProjects().add(project);
        System.out.println(5);
        project.getMembers().add(member);
        System.out.println(6);
        memberRepository.save(member);
        System.out.println(7);
        projectRepository.save(project);
        System.out.println(8);
    }*/
}
