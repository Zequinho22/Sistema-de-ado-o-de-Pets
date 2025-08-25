package com.adote.controller;

import com.adote.dto.AnimalDTOs.*;
import com.adote.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/animals")
public class AnimalsController {
    private final AnimalService animals;
    public AnimalsController(AnimalService animals){ this.animals = animals; }

    @PostMapping
    public ResponseEntity<?> create(@RequestPart("data") CreateAnimalRequest data,
                                    @RequestPart(value="image", required=false) MultipartFile image,
                                    Authentication auth) throws Exception {
        var ownerEmail = auth.getName();
        // Em serviço: recuperar ID pelo email; encurtando aqui para exemplo:
        var created = animals.create(
            // No serviço, resolvemos o userId por email; aqui, passaremos o email como id, simplificado
            // ajuste: buscar userId por email
            animals.listByUser("").stream().findFirst().orElse(null) == null ? "" : "",
            data.name(), data.type(), data.gender(), image
        );
        return ResponseEntity.status(201).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAvailability(@PathVariable String id, @RequestBody UpdateAnimalStatusRequest req){
        return ResponseEntity.ok(animals.updateAvailability(id, Boolean.TRUE.equals(req.available())));
    }

    @GetMapping("/available")
    public ResponseEntity<?> available(@RequestParam(required=false) String gender,
                                       @RequestParam(required=false) String type){
        return ResponseEntity.ok(animals.listAvailable(gender, type));
    }
}
