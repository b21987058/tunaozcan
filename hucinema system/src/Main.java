import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;

public class Main extends Application  {

    Stage window;
    Scene sceneLoginWindow, sceneSignUpWindow,sceneWelcomeWindow1,sceneWelcomeWindow2,sceneWelcomeWindow3,sceneFilmWindow;

    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;

        datFileRead.datFileRead("assets\\data\\backup.dat");


        /*String musicFile = "file:src\\assets\\effects\\error.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);*/


        window.getIcons().add(new Image("file:src\\assets\\icons\\logo.png"));

        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });

        //LOGIN WINDOW

        Label label1 = new Label("Welcome to HUCS Reservation System!"
                +"\n"+"Please enter your credentials below and click LOGIN."
                +"\n"+"You can create a new account by clicking SIGN UP button."
        +"\n");

        Button button1 = new Button("LOG IN");
        Button button2 = new Button("SIGN UP");
        button2.setOnAction(e -> window.setScene(sceneSignUpWindow));// SIGNUP WINDOW AÇILCAK
        //button1.setOnAction(e -> window.setScene(sceneWelcomeWindow1));//WelcomeWindow AÇILCAK

            //Layout1 top text
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1);
        layout1.setAlignment(Pos.TOP_CENTER);

            //Layout 3 USERNAME AND PASSWORD

        ArrayList<String> usernames = new ArrayList<>();
        for (Users userX: datFileRead.usersArrayList){
            usernames.add(userX.getUsername());
        }
        ArrayList<String> passwords = new ArrayList<>();
        for (Users passwordsX: datFileRead.usersArrayList){
            usernames.add(passwordsX.getHashedPassword());
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label userName = new Label("Username:");
        GridPane.setConstraints(userName, 0,0);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1,0);

        Label password = new Label("Password:");
        GridPane.setConstraints(password,0 ,1);
        TextField passwordInput = new TextField();
        GridPane.setConstraints(passwordInput,1,1);

        hashPassword hashPassword2 = new hashPassword();

        for(Users userT: datFileRead.usersArrayList){
            System.out.println(userT.getUsername()+"\t"+userT.getHashedPassword()+"\n");
        }

        System.out.println(hashPassword.hashPassword("0dQ6aC5+BxAt4xDaD0tWew=="));
        button1.setOnAction(e ->window.setScene(sceneWelcomeWindow3));

        /*button1.setOnAction(e -> {for(Users userY: datFileRead.usersArrayList){
            int username_true = 0;
            int password_true = 0;
            if(userY.getUsername() == usernameInput.getText()){
                username_true=1;
            }
            if(userY.getHashedPassword()==hashPassword.hashPassword(passwordInput.getText())){
                password_true =1;
            }
            if( (username_true==1) && (password_true==1) ){ //girilen sifre doğruysa
                if(userY.isTrue_if_club_member().equals("true")){ //club member ise
                    if(userY.true_if_admin.equals("true")){ //adminse
                        window.setScene(sceneWelcomeWindow3);
                        return ;
                    }
                    else if(userY.true_if_admin.equals("false")){ //club member
                        window.setScene(sceneWelcomeWindow1);
                        return;
                    }
                }
                else if(userY.isTrue_if_club_member().equals("false")){ //normal member
                    window.setScene(sceneWelcomeWindow2);
                    return;
                }
            }
            else {

            }
        }
        });*/

        GridPane.setConstraints(button2,1,2);
        GridPane.setConstraints(button1,1,3);

        grid.getChildren().addAll(userName,usernameInput,password,passwordInput,button2,button1);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(layout1);
        borderPane.setCenter(grid);
        button2.setOnAction(e -> window.setScene(sceneSignUpWindow));

        sceneLoginWindow = new Scene(borderPane,500,300);

        //SIGNUP WINDOW
        Label label2 = new Label("Welcome to HUCS Reservation System!"
                +"\n"+"Fill the form below to create a new account."
                +"\n"+"You can go to Log In page by clicking LOG IN Button."
                +"\n");
        VBox layoutSIGNUP = new VBox(20);
        layoutSIGNUP.getChildren().addAll(label2);
        layoutSIGNUP.setAlignment(Pos.TOP_CENTER);
        Button button3 = new Button("LOG IN");
        Button button4 = new Button("SIGN UP");

        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10,10,10,10));
        grid2.setVgap(8);
        grid2.setHgap(10);

        Label userName2 = new Label("Username:");
        GridPane.setConstraints(userName2, 0,0);
        TextField usernameInput2 = new TextField();
        GridPane.setConstraints(usernameInput2, 1,0);

        Label password2 = new Label("Password:");
        GridPane.setConstraints(password2,0 ,1);
        TextField passwordInput2 = new TextField();
        GridPane.setConstraints(passwordInput2,1,1);
        Label password3 = new Label("Password:");
        GridPane.setConstraints(password3,0 ,2);
        TextField passwordInput3 = new TextField();
        GridPane.setConstraints(passwordInput3,1,2);



        GridPane.setConstraints(button3,1,3);
        GridPane.setConstraints(button4,1,4);

        grid2.getChildren().addAll(userName2,usernameInput2,password2,passwordInput2,password3,passwordInput3,button3,button4);


        BorderPane borderPane2 = new BorderPane();
        borderPane2.setTop(layoutSIGNUP);
        borderPane2.setCenter(grid2);


        button3.setOnAction(e -> window.setScene(sceneLoginWindow));


        sceneSignUpWindow = new Scene(borderPane2,550,300);

        //Welcome Page Common Parts

        String filmList ="";
        for(Film filmListo : datFileRead.filmArrayList){
            filmList= filmList+"-"+filmListo.getFilmName()+"\n";
        }
        Button okButton = new Button("OK");
        Button LogOutButton = new Button("Log Out");
        LogOutButton.setOnAction(e -> window.setScene(sceneLoginWindow));

        GridPane welcomeGrid = new GridPane();
        welcomeGrid.setPadding(new Insets(10,10,10,10));
        welcomeGrid.setVgap(8);
        welcomeGrid.setHgap(10);

        GridPane.setConstraints(okButton,0,0);
        GridPane.setConstraints(LogOutButton,1,0);
        welcomeGrid.getChildren().addAll(okButton,LogOutButton);


        GridPane adminGrid = new GridPane();
        adminGrid.setPadding(new Insets(10,10,10,10));
        adminGrid.setVgap(8);
        adminGrid.setHgap(10);

            //Welcome Page Normal User a CLUB MEMBER
        Label welcomeLabel1 =new Label("Welcome to HUCS Reservation System"+
                "\n"+"Our Dear CLUB Member"+"\n"+"Our currently available films are listed below as:"
                +"\n"+filmList);

            //Welcome Page Normal User NOT a club member
        Label welcomeLabel2 =new Label("Welcome to HUCS Reservation System"+
                "\n"+"(you can consider our membership options later)"+"\n"+"Our currently available films are listed below as:"
                +"\n"+filmList);

            //Welcome Page ADMIN
        Label welcomeLabelAdmin = new Label("Welcome to HUCS Reservation System"+"\n"
        +"OUR mighty ADMIN !"+"\n"+"Our currently available films are listed below as:"
                +"\n"+filmList);
        Button addFilm = new Button("Add Film");
        Button removeFilm = new Button("Remove Film");
        Button editUsers = new Button("Edit Users");
        GridPane.setConstraints(addFilm,0,1);
        GridPane.setConstraints(removeFilm,1,1);
        GridPane.setConstraints(editUsers,2,1);
        adminGrid.getChildren().addAll(addFilm,removeFilm,editUsers);

            //WelcomeWindow1
        VBox welcome1 = new VBox(20);
        welcome1.getChildren().addAll(welcomeLabel1,okButton,LogOutButton);
        welcome1.setAlignment(Pos.TOP_CENTER);

        BorderPane welcomeBorder1 = new BorderPane();
        welcomeBorder1.setTop(welcome1);
        //welcomeBorder1.setCenter(welcomeGrid);
        sceneWelcomeWindow1 = new Scene(welcomeBorder1,400,400);

            //WelcomeWindow2
        VBox welcome2 = new VBox(20);
        welcome2.getChildren().addAll(welcomeLabel2,okButton,LogOutButton);
        welcome2.setAlignment(Pos.TOP_CENTER);

        BorderPane welcomeBorder2 = new BorderPane();
        welcomeBorder2.setTop(welcome2);
        //welcomeBorder2.setCenter(welcomeGrid);
        sceneWelcomeWindow2 = new Scene(welcomeBorder2,400,400);

            //WelcomeWindow3
        VBox welcome3 = new VBox(20);
        welcome3.getChildren().addAll(welcomeLabelAdmin,okButton,LogOutButton,addFilm,removeFilm,editUsers);
        welcome3.setAlignment(Pos.TOP_CENTER);

        BorderPane welcomeBorder3 = new BorderPane();
        welcomeBorder3.setTop(welcome3);
        //welcomeBorder3.setCenter(adminGrid);
        sceneWelcomeWindow3 = new Scene(welcomeBorder3,400,600);










        window.setScene(sceneLoginWindow);
        window.setTitle("HUCS Cinema Reservation System");
        window.show();



    }
    private void closeProgram(){
        Boolean answer = ConfirmBox.display("HUCS Res. Sys.","Are you sure you want to exit?");
        if(answer)
            window.close();
    }


}
