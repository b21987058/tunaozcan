package com.example.internshipfinal.model;

import javax.persistence.*;

@Table(name = "id_cards")
@Entity
public class IdCard {


    @Id
    @GeneratedValue
    private long id;


    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "read_text", nullable = true, length = 5000)
    private String idCardReadText;

    @Column(name = "read_tc_number", nullable = true)
    private String tcNumberRead;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIdCardReadText() {
        return idCardReadText;
    }

    public void setIdCardReadText(String idCardReadText) {
        this.idCardReadText = idCardReadText;
    }

    public String getTcNumberRead() {
        return tcNumberRead;
    }

    public void setTcNumberRead(String tcNumberRead) {
        this.tcNumberRead = tcNumberRead;
    }


}
