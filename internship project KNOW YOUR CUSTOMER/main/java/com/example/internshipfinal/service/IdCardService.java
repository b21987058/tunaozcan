package com.example.internshipfinal.service;

import com.example.internshipfinal.model.IdCard;
import org.springframework.stereotype.Service;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgproc;


import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IdCardService {


    private static final String TC_NUMBER_PATTERN = "([0-9]{11})"; // Pattern for an 11 digit number
    private static final String processedImagesPath = "VerificationFiles/ProcessedImages/"; // Path for processed images
    private static final String tessDataPath = "tessdata"; // Path for Tessdata (used by Tesseract)

    private static final String tessLanguage = "tur"; // Language for Tesseract
    private static final String imageType = ".png"; // Used when saving image


    public void readTextFromTcImage(IdCard idCard, String username) throws TesseractException {

        String processedIdImagePath = processedImagesPath + username + imageType;

        // Makes input image black & white
        Mat gray = opencv_imgcodecs.imread(idCard.getImagePath(), opencv_imgcodecs.IMREAD_GRAYSCALE);

        // Blurs image
        Mat blurred = new Mat();
        opencv_imgproc.GaussianBlur(gray, blurred, new Size(3, 3), 1);

        // Binarizes image
        Mat binarized = new Mat();
        opencv_imgproc.adaptiveThreshold(blurred, binarized, 255, opencv_imgproc.ADAPTIVE_THRESH_MEAN_C, opencv_imgproc.THRESH_BINARY, 15, 40);

        // Saves processed image (which has now become black & white, blurred and binarized).
        opencv_imgcodecs.imwrite(processedIdImagePath, binarized);

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessDataPath);
        tesseract.setLanguage(tessLanguage);

        idCard.setIdCardReadText(tesseract.doOCR(new File(processedIdImagePath))); // Reads text from processed image

        readTcNumber(idCard); // Finds the tc number from the read text
    }

    // This method tries to find the tc number (an 11 digit number).
    private void readTcNumber(IdCard idCard) {


        Pattern pattern = Pattern.compile(TC_NUMBER_PATTERN, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(idCard.getIdCardReadText());

        if (matcher.find())
            idCard.setTcNumberRead(matcher.group());
    }

}
