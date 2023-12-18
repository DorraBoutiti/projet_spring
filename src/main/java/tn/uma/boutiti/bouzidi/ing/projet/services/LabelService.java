package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;

import java.util.List;


public interface LabelService {

    LabelDTO save(LabelDTO labelDTO) ;

    List<LabelDTO> findAll();

    LabelDTO findOne(Long id);

    void delete(Long id);

}
