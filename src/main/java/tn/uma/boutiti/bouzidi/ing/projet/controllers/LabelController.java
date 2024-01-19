package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;

import java.util.List;

@RestController
//@RequestMapping("/api/labels")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "bearer-token")
public class LabelController {

    @Autowired
    private LabelService labelService;


    @PostMapping("/api/labels")
    public ResponseEntity<LabelDTO> create(@RequestBody LabelDTO labelDTO) {
        LabelDTO label = labelService.save(labelDTO);
        return ResponseEntity.ok().body(label);
    }

    @GetMapping("/api/user/labels")
    public ResponseEntity<Page<LabelDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LabelDTO> labelsPage = labelService.findAll(pageable);

        if (labelsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(labelsPage);
        }
    }

    @GetMapping("/api/labels/{id}")
    public ResponseEntity<LabelDTO> findOne(@PathVariable Long id) {
        LabelDTO label = labelService.findOne(id);
        return ResponseEntity.ok().body(label);
    }

    @DeleteMapping("/api/labels/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labelService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/labels/{id}")
    public ResponseEntity<LabelDTO> update(@PathVariable Long id, @RequestBody LabelDTO updatedLabelDTO) {
        LabelDTO label = labelService.findOne(id);
        if (label == null) {
            return ResponseEntity.notFound().build();
        }
        label.setName(updatedLabelDTO.getName());
        LabelDTO updatedLabel = labelService.save(label);
        return ResponseEntity.ok().body(updatedLabel);
    }
}
