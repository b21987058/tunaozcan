package com.example.internshipfinal.service;


import com.example.internshipfinal.model.User;
import com.example.internshipfinal.repository.UserRepository;
import com.example.internshipfinal.status.SignupStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SignupService {

    private static final int MINIMUM_FIRSTNAME_LENGTH = 2;
    private static final int MAXIMUM_FIRSTNAME_LENGTH = 100;
    private static final String FIRSTNAME_PATTERN = "^[a-zA-ZİiIıÖöÜüŞşÇçĞğ ,.'-]+$"; // Regex pattern for first name

    private static final int MINIMUM_LASTNAME_LENGTH = 2;
    private static final int MAXIMUM_LASTNAME_LENGTH = 100;
    private static final String LASTNAME_PATTERN = "^[a-zA-ZİiIıÖöÜüŞşÇçĞğ ,.'-]+$"; // Regex pattern for last name

    private static final int ID_LENGTH = 11;
    private static final String ID_PATTERN = "^[0-9]+$"; // Regex pattern for ID number

    private static final int MINIMUM_USERNAME_LENGTH = 2;
    private static final int MAXIMUM_USERNAME_LENGTH = 100;

    // TODO TO BE CHANGED LATER
    private static final String USERNAME_PATTERN = ".*"; // Regex pattern for username

    private static final int MINIMUM_PASSWORD_LENGTH = 2;
    private static final int MAXIMUM_PASSWORD_LENGTH = 100;

    // TODO TO BE CHANGED LATER
    private static final String PASSWORD_NOT_ALLOWED_CHARACTERS_PATTERN = ""; // Regex pattern for characters not allowed in password
    private static final String PASSWORD_MUST_USE_CHARACTERS_PATTERN = ""; // Regex pattern for must use characters in password

    private SignupStatus signupStatus; // Can be used to return the status of sign up

    @Autowired
    private UserRepository userRepository;


    public User signup(String firstName, String lastName, String tcNumber, String username, String password) {

        boolean isValid = validate(firstName, lastName, tcNumber, username, password);

        if (isValid) {

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTcNumber(tcNumber);
            user.setUsername(username);
            user.setPassword(password);

            userRepository.save(user);
            return user;
        }

        // Currently, when signup is successful, the signed up user is returned. If not successful, null is returned.
        // However, signupStatus (defined in this class) can be returned instead, if necessary.

        return null;
    }


    public boolean validate(String firstName, String lastName, String tcNumber, String username, String password) {

        if (!isFirstNameCorrect(firstName)) {
            return false;
        }

        if (!isLastNameCorrect(lastName)) {
            return false;
        }

        if (!isTcNumberCorrect(tcNumber)) {
            return false;
        }

        if (!isUsernameCorrect(username)) {
            return false;
        }

        if (!isPasswordCorrect(password)) {
            return false;
        }

        signupStatus = SignupStatus.SIGNED_UP_SUCCESSFULLY;
        return true;
    }


    private boolean isFirstNameCorrect(String firstName) {

        if (firstName.length() > MAXIMUM_FIRSTNAME_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_FIRSTNAME_TOO_LONG;
            return false;
        }

        if (firstName.length() < MINIMUM_FIRSTNAME_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_FIRSTNAME_TOO_SHORT;
            return false;
        }

        Pattern pattern = Pattern.compile(FIRSTNAME_PATTERN);
        Matcher matcher = pattern.matcher(firstName);

        if (!matcher.find()) {
            signupStatus = SignupStatus.INCORRECT_NAME_CHARACTERS;
            return false;
        }

        return true;
    }

    private boolean isLastNameCorrect(String lastName) {

        if (lastName.length() > MAXIMUM_LASTNAME_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_LASTNAME_TOO_LONG;
            return false;
        }

        if (lastName.length() < MINIMUM_LASTNAME_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_LASTNAME_TOO_SHORT;
            return false;
        }

        Pattern pattern = Pattern.compile(LASTNAME_PATTERN);
        Matcher matcher = pattern.matcher(lastName);

        if (!matcher.find()) {
            signupStatus = SignupStatus.INCORRECT_LASTNAME_CHARACTERS;
            return false;
        }

        return true;
    }

    private boolean isTcNumberCorrect(String tcNumber) {


        if (tcNumber.length() > ID_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_ID_LENGTH_TOO_LONG;
            return false;
        }

        if (tcNumber.length() < ID_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_ID_LENGTH_TOO_SHORT;
            return false;
        }

        // Tries to find a user with the given tcNumber
        Optional<User> userOptional = userRepository.findByTcNumber(tcNumber);

        // If there is already a user with the given tcNumber, returns false and sets the signupStatus to INCORRECT_ID_EXISTS.
        if (userOptional.isPresent()) {
            signupStatus = SignupStatus.INCORRECT_ID_EXISTS;
            return false;
        }


        Pattern pattern = Pattern.compile(ID_PATTERN);
        Matcher matcher = pattern.matcher(tcNumber);

        if (!matcher.find()) {
            signupStatus = SignupStatus.INCORRECT_ID_CHARACTERS;
            return false;
        }

        return true;
    }

    private boolean isUsernameCorrect(String username) {

        if (username.length() > MAXIMUM_USERNAME_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_USERNAME_TOO_LONG;
            return false;
        }

        if (username.length() < MINIMUM_USERNAME_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_USERNAME_TOO_SHORT;
            return false;
        }

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);

        if (!matcher.find()) {
            signupStatus = SignupStatus.INCORRECT_USERNAME_CHARACTERS;
            return false;
        }


        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            signupStatus = SignupStatus.INCORRECT_USERNAME_EXISTS;
            return false;
        }


        return true;
    }

    private boolean isPasswordCorrect(String password) {

        if (password.length() > MAXIMUM_PASSWORD_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_PASSWORD_TOO_LONG;
            return false;
        }

        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            signupStatus = SignupStatus.INCORRECT_PASSWORD_TOO_SHORT;
            return false;
        }

        Pattern notAllowedCharactersPattern = Pattern.compile(PASSWORD_NOT_ALLOWED_CHARACTERS_PATTERN);
        Matcher notAllowedCharactersMatcher = notAllowedCharactersPattern.matcher(password);

        if (!notAllowedCharactersMatcher.find()) {
            signupStatus = SignupStatus.INCORRECT_PASSWORD_CHARACTERS;
            return false;
        }

        Pattern mustUseCharactersPattern = Pattern.compile(PASSWORD_MUST_USE_CHARACTERS_PATTERN);
        Matcher mustUseCharactersMatcher = mustUseCharactersPattern.matcher(password);

        if (!mustUseCharactersMatcher.find()) {
            signupStatus = SignupStatus.INCORRECT_PASSWORD_MUST_USE_CHARACTERS_NOT_USED;
            return false;
        }

        return true;
    }


}
