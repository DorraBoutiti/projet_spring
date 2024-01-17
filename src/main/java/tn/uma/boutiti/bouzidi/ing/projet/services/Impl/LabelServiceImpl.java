package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.LabelMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.repository.LabelRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public LabelDTO save(LabelDTO labelDTO) {
        Label label = labelMapper.toEntity(labelDTO);
        label = labelRepository.save(label);
        return labelMapper.toDto(label);
    }

    @Override
    public Page<LabelDTO> findAll(Pageable pageable) {
        Page<Label> labelsPage = labelRepository.findAll(pageable);
        List<LabelDTO> labelDTOList = labelsPage.getContent()
                .stream()
                .map(labelMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(labelDTOList, pageable, labelsPage.getTotalElements());
    }

    @Override
    public LabelDTO findOne(Long id) {
        Optional<Label> label =labelRepository.findById(id);
        return labelMapper.toDto(label.get());
    }

    @Override
    public void delete(Long id) {
        labelRepository.deleteById(id);
    }
}
