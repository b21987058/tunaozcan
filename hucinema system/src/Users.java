public class Users {
    String user = "user";
    String username;
    String hashedPassword;
    String true_if_club_member;
    String true_if_admin;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String isTrue_if_club_member() {
        return true_if_club_member;
    }

    public void setTrue_if_club_member(String true_if_club_member) {
        this.true_if_club_member = true_if_club_member;
    }

    public String isTrue_if_admin() {
        return true_if_admin;
    }

    public void setTrue_if_admin(String true_if_admin) {
        this.true_if_admin = true_if_admin;
    }

    public Users(String user, String username, String hashedPassword, String true_if_club_member, String true_if_admin) {
        this.user = user;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.true_if_club_member = true_if_club_member;
        this.true_if_admin = true_if_admin;
    }
    public  boolean ARA(String txt){
        if(this.username.equals(txt))
            return true;
        else
            return false;
    }

    public Users() {
    }
}
