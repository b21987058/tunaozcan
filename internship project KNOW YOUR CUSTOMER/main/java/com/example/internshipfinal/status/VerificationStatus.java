package com.example.internshipfinal.status;

public enum VerificationStatus {

    USERNAME_NOT_FOUND,

    NOT_VERIFIED_YET,
    ID_NOT_VERIFIED,
    ID_VERIFIED,

    NO_FACE_DETECTED,
    FACES_DO_NOT_MATCH,
    FACES_MATCH_PERFECTLY, // USED WHEN FACE IMAGES ARE (ALMOST) IDENTICAL (I.E., WHEN THE SAME FACE IMAGE USED)
    FACES_MATCH
}
