package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LabelService {
    LabelDTO save(LabelDTO labelDTO) ;
    Page<LabelDTO> findAll(Pageable pageable);
    LabelDTO findOne(Long id);
    void delete(Long id);
}
