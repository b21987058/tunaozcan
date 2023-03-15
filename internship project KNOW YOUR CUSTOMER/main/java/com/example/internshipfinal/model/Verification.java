package com.example.internshipfinal.model;

import com.example.internshipfinal.status.VerificationStatus;

import javax.persistence.*;

@Entity
@Table(name = "verifications")
public class Verification {


    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "face_fk")
    private Face face;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCard_fk")
    private IdCard idCard;

    @Column(name = "face_verification_status")
    private VerificationStatus faceVerificationStatus;

    @Column(name = "id_card_verification_status")
    private VerificationStatus idCardVerificationStatus;

    @Column(name = "is_user_verified")
    private boolean isVerified;

    @Column(name = "is_face_verified")
    private boolean isFaceVerified;

    @Column(name = "is_id_card_verified")
    private boolean isIdCardVerified;

    @Column(name = "trained_yml")
    private String trainedYML;


    public VerificationStatus getFaceVerificationStatus() {
        return faceVerificationStatus;
    }

    public void setFaceVerificationStatus(VerificationStatus faceVerificationStatus) {
        this.faceVerificationStatus = faceVerificationStatus;
    }

    public VerificationStatus getIdCardVerificationStatus() {
        return idCardVerificationStatus;
    }

    public void setIdCardVerificationStatus(VerificationStatus idCardVerificationStatus) {
        this.idCardVerificationStatus = idCardVerificationStatus;
    }

    public boolean isVerified() {
        return isFaceVerified && isIdCardVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    public boolean isFaceVerified() {
        return isFaceVerified;
    }

    public void setFaceVerified(boolean faceVerified) {
        isFaceVerified = faceVerified;
    }

    public boolean isIdCardVerified() {
        return isIdCardVerified;
    }

    public void setIdCardVerified(boolean idCardVerified) {
        isIdCardVerified = idCardVerified;
    }

    public String getTrainedYML() {
        return trainedYML;
    }

    public void setTrainedYML(String trainedYML) {
        this.trainedYML = trainedYML;
    }
}
