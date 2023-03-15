package com.example.internshipfinal.controller;

import com.example.internshipfinal.model.User;
import com.example.internshipfinal.model.Verification;
import com.example.internshipfinal.repository.UserRepository;
import com.example.internshipfinal.service.VerificationService;
import com.example.internshipfinal.status.VerificationStatus;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    @Autowired
    VerificationService verificationService;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/verify/{username}")
    public String verify(@PathVariable String username,
                         MultipartFile idCard,
                         MultipartFile faceImage) throws IOException, TesseractException {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return VerificationStatus.USERNAME_NOT_FOUND.name();
        }

        User user = userOptional.get();
        verificationService.verify(user, idCard, faceImage);
        Verification verification = user.getVerification();

        String output = "Face Status: " + verification.getFaceVerificationStatus().name() + "\tFace Verified: " + verification.isFaceVerified();
        output += "\nIdCard Status: " + verification.getIdCardVerificationStatus().name() + "\tIdCard Verified: " + verification.isIdCardVerified();
        output += "\nIs User Verified: " + verification.isVerified();

        return output;
    }

    @GetMapping("/status/{username}")
    public String status(@PathVariable String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return VerificationStatus.USERNAME_NOT_FOUND.name();
        }

        User user = userOptional.get();
        Verification verification = user.getVerification();

        if (verification == null) {
            return VerificationStatus.NOT_VERIFIED_YET.name();
        }

        return verification.getFaceVerificationStatus().name() + "\n" + verification.getIdCardVerificationStatus().name();
    }

    @GetMapping("/face-status/{username}")
    public String faceStatus(@PathVariable String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return VerificationStatus.USERNAME_NOT_FOUND.name();
        }

        User user = userOptional.get();
        Verification verification = user.getVerification();

        if (verification == null) {
            return VerificationStatus.NOT_VERIFIED_YET.name();
        }

        return verification.getFaceVerificationStatus().name();
    }

    @GetMapping("/id-status/{username}")
    public String idCardStatus(@PathVariable String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return VerificationStatus.USERNAME_NOT_FOUND.name();
        }

        User user = userOptional.get();
        Verification verification = user.getVerification();

        if (verification == null) {
            return VerificationStatus.NOT_VERIFIED_YET.name();
        }

        return verification.getIdCardVerificationStatus().name();
    }


    @GetMapping("/is-verified/{username}")
    public boolean isVerified(@PathVariable String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        Verification verification = user.getVerification();

        if (verification == null)
            return false;

        return verification.isVerified();
    }


}
