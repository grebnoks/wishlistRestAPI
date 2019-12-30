package com.sesh.demo.controllers;

import com.sesh.demo.exceptions.custom.GiftIdAlreadyExistsException;
import com.sesh.demo.exceptions.custom.GiftIdMismatchException;
import com.sesh.demo.exceptions.custom.GiftNotFoundException;
import com.sesh.demo.persistence.models.Gift;
import com.sesh.demo.persistence.repos.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    @Autowired
    private GiftRepository giftRepository;

    @GetMapping
    public Iterable findAll() {
        Iterable returnVal = giftRepository.findAll();
        return returnVal;
    }

    @GetMapping("/name/{giftName}")
    public List findByName(@PathVariable String giftName) {
        Optional<List<Gift>> foundGifts = giftRepository.findByName(giftName);
        if(foundGifts.isPresent() && !foundGifts.get().isEmpty()) {
            return foundGifts.get();
        }
        throw new GiftNotFoundException();
    }

    @GetMapping("/{id}")
    public Gift findOne(@PathVariable Long id) {
        return giftRepository.findById(id).orElseThrow(GiftNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Gift create(@RequestBody Gift gift) {
        Optional existingGift = giftRepository.findById(gift.getId());
        if(existingGift.isPresent()) {
            throw new GiftIdAlreadyExistsException();
        }
        return giftRepository.save(gift);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        giftRepository.findById(id).orElseThrow(GiftNotFoundException::new);
        giftRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Gift updateGift(@RequestBody Gift gift, @PathVariable Long id) {
        if (gift.getId() != id) {
            throw new GiftIdMismatchException();
        }
        giftRepository.findById(id).orElseThrow(GiftNotFoundException::new);
        return giftRepository.save(gift);
    }
}
