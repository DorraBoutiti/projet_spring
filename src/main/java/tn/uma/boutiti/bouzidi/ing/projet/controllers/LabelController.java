package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
@RestController
@RequestMapping("/labels")
public class LabelController {
	@Autowired
	private LabelService labelService;
	@PostMapping("/create")
    public ResponseEntity<String> createLabel(@RequestParam String name) {
        Label label = labelService.createLabel(name);
        return ResponseEntity.ok("Label created with ID: " + label.getId());
    }

}
