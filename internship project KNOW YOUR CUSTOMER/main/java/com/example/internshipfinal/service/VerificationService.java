package com.example.internshipfinal.service;

import com.example.internshipfinal.model.Face;
import com.example.internshipfinal.model.IdCard;
import com.example.internshipfinal.model.User;
import com.example.internshipfinal.model.Verification;
import com.example.internshipfinal.repository.UserRepository;
import com.example.internshipfinal.status.VerificationStatus;
import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@Service
public class VerificationService {

    // Used to change strings with Turkish characters to English characters (for comparison)
    // E.g., if signed up name is Cagdas but name on the ID Card is Çağdaş, the program will match the names.
    private static final ArrayList<Character> turkishCharacters = new ArrayList<>(Arrays.asList('Ğ', 'Ü', 'Ş', 'İ', 'Ö', 'Ç'));
    private static final ArrayList<Character> englishCharacters = new ArrayList<>(Arrays.asList('G', 'U', 'S', 'I', 'O', 'C'));

    // Path for the CascadeClassifier
    private static final String cascadeClassifierPath = "CascadeClassifier/haarcascade_frontalface_default.xml";


    private static final String trainedPath = "VerificationFiles/Trained/"; // Path to save the trained files
    private static final String inputImagesPath = "VerificationFiles/InputImages/"; // Path to save the input images
    private static final String idCardImageName = "idCard"; // Name of id card images to save
    private static final String faceImageName = "face"; // Name of face images to save

    private static final String imageType = ".png";
    private static final String delimiter = "_"; // Delimiter used in names of saved images

    // Language used when using the method toUpperCase() for strings
    // E.g., "ismail" becomes "İSMAİL" and not "ISMAIL", to make sure comparison works as expected.
    private static final String languageCode = "tr-TR";

    private static final int PERFECT_MATCH_LEVEL = 5; // When there is a perfect match between faces, i.e., when the same image is used.

    private static final int MATCH_LEVEL = 65; // Values between PERFECT_MATCH_LEVEL and MATCH_LEVEL are considered as match.
    private static final double MAX_IMAGE_HEIGHT = 500; // Used to rescale images for openCV.

    // USED TO DEBUG
    private static final String trainedWithImagesPath = "VerificationFiles/TrainedWithImages/";
    private static final String facesDetectedPath = "VerificationFiles/FacesDetected/";


    @Autowired
    private IdCardService idCardService;

    @Autowired
    private UserRepository userRepository;


    // idCardMultipartFile: multipart file of the idCard image.
    // faceMultipartFile: multipart file of the face image
    public void verify(User user, MultipartFile idCardMultipartFile, MultipartFile faceMultipartFile) throws IOException, TesseractException {

        // Converts MultipartFile to PNG so that openCV and Tesseract can work.
        String idCardImagePath = multipartFileToPng(idCardMultipartFile, user.getTcNumber() + delimiter + idCardImageName);
        String faceImagePath = multipartFileToPng(faceMultipartFile, user.getTcNumber() + delimiter + faceImageName);

        if (user.getVerification() == null) {
            user.setVerification(new Verification());
        }
        Verification verification = user.getVerification();


        if (verification.getFace() == null) {
            verification.setFace(new Face());
        }
        Face face = verification.getFace();

        if (verification.getIdCard() == null) {
            verification.setIdCard(new IdCard());
        }
        IdCard idCard = verification.getIdCard();


        face.setImagePath(faceImagePath);
        idCard.setImagePath(idCardImagePath);

        verification.setFace(face);
        verification.setIdCard(idCard);

        verifyIdCardText(user);
        verifyFaces(user);

        verification.setVerified(verification.isFaceVerified() && verification.isIdCardVerified());
        user.setVerified(verification.isVerified());

        update(user);
    }


    // Converts MultipartFile to PNG so that openCV and Tesseract can work.
    public String multipartFileToPng(MultipartFile multipartFile, String name) throws IOException {

        String path = inputImagesPath + name;

        if (!name.toLowerCase().endsWith(imageType.toLowerCase()))
            path += imageType.toLowerCase();

        File image = new File(path);
        multipartFile.transferTo(image);

        return path;
    }


    private void verifyIdCardText(User user) throws TesseractException {

        IdCard idCard = user.getVerification().getIdCard();

        // This will read the text from the idCard
        idCardService.readTextFromTcImage(idCard, user.getUsername());


        String signedUpFirstName = user.getFirstName().toUpperCase(Locale.forLanguageTag(languageCode));
        String signedUpLastName = user.getLastName().toUpperCase(Locale.forLanguageTag(languageCode));

        String FirstNameWithEnglishCharacters = changeTurkishCharactersToEnglish(signedUpFirstName);
        String LastNameWithEnglishCharacters = changeTurkishCharactersToEnglish(signedUpLastName);

        // The text read from the idCard is assigned to idCardText
        String idCardText = idCard.getIdCardReadText().toUpperCase(Locale.forLanguageTag(languageCode));

        // idCardTextWithEnglishCharacters is the idCardText, however, the Turkish characters are changed to English for comparison
        String idCardTextWithEnglishCharacters = changeTurkishCharactersToEnglish(idCardText).toUpperCase();


        boolean doNamesMatchWithTurkishCharacters = idCardText.contains(signedUpFirstName) &&
                idCardText.contains(signedUpLastName);

        boolean doNamesMatchWithEnglishCharacters = idCardTextWithEnglishCharacters.contains(FirstNameWithEnglishCharacters) &&
                idCardTextWithEnglishCharacters.contains(LastNameWithEnglishCharacters);


        boolean doNamesMatch = doNamesMatchWithEnglishCharacters || doNamesMatchWithTurkishCharacters;
        boolean doTcNumbersMatch = idCard.getTcNumberRead().equals(user.getTcNumber());

        VerificationStatus status;
        boolean isVerified;

        if (doNamesMatch && doTcNumbersMatch) {
            status = VerificationStatus.ID_VERIFIED;
            isVerified = true;
        } else {
            status = VerificationStatus.ID_NOT_VERIFIED;
            isVerified = false;
        }

        user.getVerification().setIdCardVerified(isVerified);
        user.getVerification().setIdCardVerificationStatus(status);

    }


    // Changes Turkish characters of the input string to English characters for comparison
    private String changeTurkishCharactersToEnglish(String string) {

        String output = "";

        for (int i = 0; i < string.length(); i++) {

            char character = string.charAt(i);
            if (turkishCharacters.contains(character))
                character = englishCharacters.get(turkishCharacters.indexOf(character));

            output += character;
        }

        return output;
    }


    private void verifyFaces(User user) {

        Verification verification = user.getVerification();

        Face inputFace = verification.getFace();

        trainWithIdCardFace(user); // Trains with the face on the idCard image

        String trained = verification.getTrainedYML(); // Path for the trained file

        Mat inputFaceImageGray = opencv_imgcodecs.imread(inputFace.getImagePath(), opencv_imgcodecs.IMREAD_GRAYSCALE);

        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        faceRecognizer.read(trained); // Recognizer works with the trained file mentioned above

        CascadeClassifier cascadeClassifier = new CascadeClassifier(cascadeClassifierPath);

        RectVector rectVector = new RectVector();
        cascadeClassifier.detectMultiScale(inputFaceImageGray, rectVector);

        int[] labels = new int[(int) rectVector.size()];
        double[] confidenceLevels = new double[(int) rectVector.size()];

        int j = 0;
        for (Rect rect : rectVector.get()) {
            Mat face = new Mat(inputFaceImageGray, rect);

            double factor = MAX_IMAGE_HEIGHT / face.size().height();

            opencv_imgproc.resize(face, face, new Size(), factor, factor, 0);
            faceRecognizer.predict(face, labels, confidenceLevels);

            // USED TO DEBUG
            opencv_imgcodecs.imwrite(facesDetectedPath + user.getUsername() + delimiter + j++ + imageType, face);
        }


        int outputLabel = 0;
        double outputConfidenceLevel = -1;
        for (int i = 0; i < rectVector.size(); i++) {
            // It is assumed that label 0 is only used when face is not recognized
            if (labels[i] != 0) {
                if (outputConfidenceLevel == -1 || confidenceLevels[i] < outputConfidenceLevel)
                    outputLabel = labels[i];
                outputConfidenceLevel = confidenceLevels[i];
            }
        }

        // USED TO DEBUG
        System.out.println("Face Recognition Confidence Level: " + outputConfidenceLevel);


        VerificationStatus status;
        boolean isVerified = false;

        if (rectVector.size() == 0) {
            status = VerificationStatus.NO_FACE_DETECTED;
        }
        else if (outputLabel == 0 || outputConfidenceLevel > MATCH_LEVEL) {
            status = VerificationStatus.FACES_DO_NOT_MATCH;
        } else if (outputConfidenceLevel <= PERFECT_MATCH_LEVEL) {
            status = VerificationStatus.FACES_MATCH_PERFECTLY;
        } else {

            status = VerificationStatus.FACES_MATCH;
            isVerified = true;
        }

        verification.setFaceVerificationStatus(status);
        verification.setFaceVerified(isVerified);
    }


    // Trains with the face image on the idCard
    private void trainWithIdCardFace(User user) {

        Verification verification = user.getVerification();

        IdCard idCard = verification.getIdCard();

        String idCardImagePath = idCard.getImagePath();

        FaceRecognizer recognizer = LBPHFaceRecognizer.create();

        Mat gray = opencv_imgcodecs.imread(idCardImagePath, opencv_imgcodecs.IMREAD_GRAYSCALE);

        CascadeClassifier cascadeClassifier = new CascadeClassifier(cascadeClassifierPath);

        RectVector rectVector = new RectVector();
        cascadeClassifier.detectMultiScale(gray, rectVector);


        MatVector faces = new MatVector();

        Rect mainRect = new Rect(0, 0, 0, 0);

        // Chooses the biggest rectangle (i.e., the biggest face in the image)
        for (Rect rect : rectVector.get()) {

            if (rect.width() >= mainRect.width() && rect.height() >= mainRect.height())
                mainRect = rect;
        }

        Mat face = new Mat(gray, mainRect);

        // Apparently, OpenCV needs at least 2 images to train. Since we do not have two images to train, we use the
        // same face 2 times.
        faces.push_back(face);
        faces.push_back(face);

        // USED TO DEBUG
        opencv_imgcodecs.imwrite(trainedWithImagesPath + user.getUsername() + imageType, face);

        Mat labels = new Mat((int) faces.size(), 1);


        recognizer.train(faces, labels);

        String trained = trainedPath + user.getUsername();

        // Saves the trained file, so it can be used to recognize face in verifyFaces(User user) method
        recognizer.save(trained);
        verification.setTrainedYML(trained);

    }

    @Transactional
    public void update(User user) {
        userRepository
                .findByUsername(user.getUsername())
                .ifPresent(user1 -> {
                    user1.setVerification(user.getVerification());
                    userRepository.save(user1);
                });
    }

}
