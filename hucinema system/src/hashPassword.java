import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class hashPassword {
    /*
            ∗ Returns Base64 encoded v e r s i o n o f MD5 hashed v e r si o n o f the gi v e n password .
            ∗
            ∗ @param password Password t o be hashed .
            ∗ @return Base64 encoded v e r s i o n o f MD5 hashed v e r s i o n o f password .
*/
    public static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }
}



/*if(usernames.contains(usernameInput.getText())&&passwords.contains(hashPassword.hashPassword(usernameInput.getText()))){
        if(datFileRead.usersArrayList.get(datFileRead.usersArrayList.indexOf(usernameInput.getText())).isTrue_if_admin().equals("true")){
            window.setScene(sceneWelcomeWindow3);
        }
        else if(datFileRead.usersArrayList.get(datFileRead.usersArrayList.indexOf(usernameInput.getText())).isTrue_if_admin().equals("false")){
            if(datFileRead.usersArrayList.get(datFileRead.usersArrayList.indexOf(usernameInput.getText())).isTrue_if_club_member().equals("true")){
                window.setScene(sceneWelcomeWindow1);
            }
            else if(datFileRead.usersArrayList.get(datFileRead.usersArrayList.indexOf(usernameInput.getText())).isTrue_if_club_member().equals("false")){
                window.setScene(sceneWelcomeWindow2);
            }
        }
        }
        else if ((usernames.contains(usernameInput.getText()))&&(passwords.contains(hashPassword.hashPassword(usernameInput.getText()))) == false){
            //mediaPlayer.play();
        }*/
