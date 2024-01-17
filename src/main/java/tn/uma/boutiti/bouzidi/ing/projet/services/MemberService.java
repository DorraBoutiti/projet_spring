package tn.uma.boutiti.bouzidi.ing.projet.services;

import java.util.List;

import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;
 
 
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.models.Role;

import java.util.List;
 n


public interface MemberService {


    MemberDTO save(MemberDTO memberDTO) ;


    List<MemberDTO> findAll();

    MemberDTO findOne(Long id);

    void delete(Long id);

    public List<ProjectDTO> getAllProjectsAndTasksByUsername(String username);


	void deleteMemberAndRelatedEntities(Long id);
	
	void changeMemberPassword(Long memberId, String newPassword);


	void changeMemberRole(Long memberId, String newRole);

}
