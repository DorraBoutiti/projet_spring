package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;

import java.util.List;


public interface MemberService {


    MemberDTO save(MemberDTO memberDTO) ;
    List<MemberDTO> findAll();
    MemberDTO findOne(Long id);
    void delete(Long id);
    public List<Project> getAllProjectsAndTasksByUsername(String username);   
}