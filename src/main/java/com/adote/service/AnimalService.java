package com.adote.service;

import com.adote.domain.*;
import com.adote.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AnimalService {
    private final AnimalRepository animals;
    private final UserRepository users;
    public AnimalService(AnimalRepository animals, UserRepository users){ this.animals = animals; this.users = users; }

    public Animal create(String ownerId, String name, String type, String gender, MultipartFile image) throws IOException {
        var owner = users.findById(ownerId).orElseThrow();
        var a = new Animal();
        a.setOwner(owner); a.setName(name); a.setType(type); a.setGender(gender); a.setAvailable(true);
        if(image!=null && !image.isEmpty()){
            String dir = System.getenv().getOrDefault("UPLOAD_DIR","uploads");
            new File(dir).mkdirs();
            String filename = UUID.randomUUID()+"-"+image.getOriginalFilename();
            File dest = new File(dir, filename);
            image.transferTo(dest);
            a.setImagePath(dest.getAbsolutePath());
        }
        return animals.save(a);
    }

    public List<Animal> listAvailable(String gender, String type){
        if(gender!=null && !gender.isBlank()) return animals.findByAvailableTrueAndGenderIgnoreCase(gender);
        if(type!=null && !type.isBlank()) return animals.findByAvailableTrueAndTypeIgnoreCase(type);
        return animals.findByAvailableTrue();
    }

    public Animal updateAvailability(String id, boolean available){
        var a = animals.findById(id).orElseThrow();
        a.setAvailable(available);
        return animals.save(a);
    }

    public List<Animal> listByUser(String userId){
        var u = users.findById(userId).orElseThrow();
        return animals.findByOwner(u);
    }
}
