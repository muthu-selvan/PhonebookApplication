package com.phonebook.controller;

import com.phonebook.dao.PhonebookRepository;
import com.phonebook.exception.ResourceNotFoundException;
import com.phonebook.modal.Phonebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class PhonebookController {

    @Autowired
    private PhonebookRepository phonebookRepository;

    @GetMapping("/phonebooks")
    public List<Phonebook> getAllPhonebookInfo() {
        return phonebookRepository.findAll();
    }

    @GetMapping("/phonebooks/{id}")
    public ResponseEntity<Phonebook> getPhonebookById(@PathVariable(value = "id") long phonebookId)
            throws ResourceNotFoundException {
        final Phonebook phonebook = phonebookRepository.findById(phonebookId)
                .orElseThrow(() -> new ResourceNotFoundException("Phone Book not found for id: "+phonebookId));
        return ResponseEntity.ok().body(phonebook);
    }

    @PostMapping("/phonebooks")
    public Phonebook addPhonebook(@Validated @RequestBody Phonebook phonebook) {
        if(phonebook.isEmpty()) {
            return null;
        }
        return phonebookRepository.save(phonebook);
    }

    @PutMapping("/phonebooks/{id}")
    public ResponseEntity<Phonebook> updatePhonebook(@PathVariable(value = "id") Long phonebookId,
                                                     @Validated @RequestBody Phonebook phonebookDetails) throws ResourceNotFoundException {
        final Phonebook phonebook = phonebookRepository.findById(phonebookId)
                .orElseThrow(() -> new ResourceNotFoundException("Phone Book not found for id: "+phonebookId));

        if(phonebookDetails.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        phonebook.setFirstName(phonebookDetails.getFirstName());
        phonebook.setLastName(phonebookDetails.getLastName());
        phonebook.setPhoneNo(phonebookDetails.getPhoneNo());

        final Phonebook updatedPhonebook = phonebookRepository.save(phonebook);
        return  ResponseEntity.ok(updatedPhonebook);
    }

    @DeleteMapping("/phonebooks/{id}")
    public Map<String, Boolean> deletePhonebook(@PathVariable(value = "id") Long phonebookId)
            throws ResourceNotFoundException {
        final Phonebook phonebook = phonebookRepository.findById(phonebookId)
                .orElseThrow(() -> new ResourceNotFoundException("Phone Book not found for id: "+phonebookId));

        phonebookRepository.delete(phonebook);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
