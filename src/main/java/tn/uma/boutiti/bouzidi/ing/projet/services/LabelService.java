package tn.uma.boutiti.bouzidi.ing.projet.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.repository.LabelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    
    public Label createLabel(String name) {
        Label label = new Label();
        label.setName(name);
        return labelRepository.save(label);
    }
    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public Optional<Label> getLabelById(Long id) {
        return labelRepository.findById(id);
    }

    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }

    public void deleteLabelById(Long id) {
        labelRepository.deleteById(id);
    }

    public List<Label> findLabelsByIds(List<Long> labelIds) {
        return labelRepository.findAllById(labelIds);
    }
}
