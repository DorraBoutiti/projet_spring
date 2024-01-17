package tn.uma.boutiti.bouzidi.ing.projet.services;

import java.util.List;

import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;


public interface MemberService {


    MemberDTO save(MemberDTO memberDTO) ;


    List<MemberDTO> findAll();

    MemberDTO findOne(Long id);

    void delete(Long id);

    public List<ProjectDTO> getAllProjectsAndTasksByUsername(String username);

}
