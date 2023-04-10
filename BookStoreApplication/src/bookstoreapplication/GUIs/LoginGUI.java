/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication.GUIs;

import bookstoreapplication.LoginManager;
import bookstoreapplication.Viewable;
import java.awt.geom.AffineTransform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.lang.Math;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.*;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author Victor
 */
public class LoginGUI implements Viewable {

    private LoginManager loginManager;

    private Scene scene, scene1, scene2;
    public static final int defaultWidth = 900;
    public static final int defaultHeight = 600;
    final int spacing = 15;
    private DropShadow ds;

    public void setLoginPresenter(LoginManager presenter) {
        loginManager = presenter;

        ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);
    }

    public void accessUI(Stage primaryStage) {
        // Opening Sound
        //String musicFile = "phase2/sound1.mp3";
        //Media sound = new Media(new File(musicFile).toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.setVolume(0.25);
        //mediaPlayer.setAutoPlay(true);
        //MediaView mediaView = new MediaView(mediaPlayer);

        //Button Styling 
        String buttonStyle = "-fx-font-family: Helvetica; -fx-background-color: #fff; -fx-font-size: 16pt;";
        primaryStage.setTitle("BookstoreApplication - Window");
        primaryStage.setResizable(false);
        Image icon = new Image(new File("Media/icon.png").toURI().toString());

        // Buttons
        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");
        Button cancelButton = new Button("Cancel");
        Button exitButton = createExitButton(primaryStage);

        // Button list
        List<Button> buttons = new ArrayList<>(Arrays.asList(loginButton, signUpButton, exitButton));
        for (Button button : buttons) {
            button.setMinWidth(200);
            button.setStyle(buttonStyle);
        }

        // Login
        accessLoginGUI(primaryStage, loginButton, cancelButton);

        // Sign Up (implement the signup button)
        accessSignUpGUI(primaryStage, signUpButton, scene);

        // Main Layout
        VBox layout = createLayout(buttons);

        //layout.getChildren().add(mediaView);
        scene = new Scene(layout, defaultWidth, defaultHeight);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {//THIS CHANGES BEHAVIOR OF X BUTTON
            public void handle(WindowEvent we) {
                logOutSequence(primaryStage);
            }
        });
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    private void accessLoginGUI(Stage primaryStage, Button loginButton, Button returnButton) {

        loginButton.setOnAction(e -> primaryStage.setScene(scene1));

        // Layout 1
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.setHgap(spacing);
        grid.setVgap(spacing);

        String path = "Media/bg.jpg";
        BackgroundImage myBI = new BackgroundImage(new Image(new File(path).toURI().toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        // Labels
        List<Label> labelList = createLabelList(Arrays.asList("username", "password"), 1, "Enter your ");
        for (Label label : labelList) {
            label.setTextFill(Color.WHITE);
            label.setEffect(ds);
            label.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        }
        Label loginSystem = new Label("Please type in your username and password");
        loginSystem.setEffect(ds);
        loginSystem.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        loginSystem.setTextFill(Color.WHITE);
        GridPane.setConstraints(loginSystem, 0, 5, 4, 1, HPos.CENTER, VPos.CENTER);

        // TextFields
        TextField nameInput = createTextField("username", 1, 1);

        // PasswordFields
        PasswordField passInput = createPasswordField();

        // Layout 2
        Text title = new Text("Login");
        title.setEffect(ds);
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 35));
        title.setFill(Color.WHITE);
        GridPane.setConstraints(title, 1, 0);
        grid.getChildren().add(title);
        grid.getChildren().addAll(labelList);
        grid.getChildren().addAll(returnButton, loginSystem, nameInput, passInput,
                loginHelper(nameInput, passInput, loginSystem, primaryStage, returnButton));
        grid.setAlignment(Pos.CENTER);
        grid.setBackground(new Background(myBI));
        scene1 = new Scene(grid, defaultWidth, defaultHeight);
    }

    /**
     * Creates a scene where the user can create an account on the program
     *
     * @param primaryStage The Stage the GUI uses
     * @param button The button that will send the Stage to the scene when
     * activated
     * @param goBackScene The return scene
     * @return returns the sign up scene
     */
    public Scene accessSignUpGUI(Stage primaryStage, Button button, Scene goBackScene) {
        button.setOnAction(e -> primaryStage.setScene(scene2));

        // Layout 1
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.setHgap(spacing);
        grid.setVgap(spacing);

        // Labels
        List<Label> labelList = createLabelList(Arrays.asList("username", "password"), 2, "Enter your new ");
        for (Label label : labelList) {
            label.setTextFill(Color.WHITE);
            label.setEffect(ds);
            label.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        }
        Label confirmLabel = new Label();

        // TextFields
        TextField nameInput = createTextField("username", 0, 2);
        TextField passInput = createTextField("password", 0, 4);

        // Buttons
        Button confirmButton = signUpConfirm(nameInput, passInput, confirmLabel);
        grid.getChildren().add(confirmLabel);
        Button returnButton = signUpButtons(confirmLabel, confirmButton, Arrays.asList(nameInput, passInput), primaryStage, goBackScene);

        // Layout 2
        signUpLayout(grid, labelList, Arrays.asList(confirmButton, returnButton), Arrays.asList(nameInput, passInput));

        scene2 = new Scene(grid, defaultWidth, defaultHeight);
        return scene2;
    }

    private Button signUpButtons(Label confirmLabel, Button confirmButton, List<TextField> textFields, Stage primaryStage, Scene goBackScene) {
        String buttonStyle = "-fx-font-family: Helvetica; -fx-background-color: #fff;";
        GridPane.setConstraints(confirmButton, 0, 8, 1, 1, HPos.LEFT, VPos.CENTER);
        Button returnButton = new Button("Cancel");
        returnButton.setStyle(buttonStyle);
        returnButton.setOnAction(e -> {
            for (TextField textField : textFields) {
                System.out.println("clearning text fields");
                textField.clear();
            }
            System.out.println("Going back to start menu");
            confirmLabel.setText("");
            primaryStage.setScene(scene);

        });
        GridPane.setConstraints(returnButton, 0, 8, 1, 1, HPos.RIGHT, VPos.CENTER);
        return returnButton;
    }

    private Button signUpConfirm(TextField nameInput, TextField passInput, Label confirmLabel) {
        String buttonStyle = "-fx-font-family: Helvetica; -fx-background-color: #fff;";
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(buttonStyle);
        confirmButton.setOnAction(e -> {
            if (loginManager.signUp(nameInput.getText(), passInput.getText())) {
                confirmLabel.setText("Signed up successfully!");
                confirmLabel.setTextFill(Color.WHITE);

            } else {
                confirmLabel.setText("Either the username already exists,\nor there in an invalid input.\nPlease try again.");
                confirmLabel.setEffect(ds);
                confirmLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
                confirmLabel.setTextFill(Color.WHITE);
            }
            GridPane.setConstraints(confirmLabel, 0, 9);
        });
        return confirmButton;
    }

    private void signUpLayout(GridPane grid, List<Label> labelList, List<Button> buttonList, List<TextField> textFields) {
        String path = "Media/bg.jpg";
        BackgroundImage myBI = new BackgroundImage(new Image(new File(path).toURI().toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Text title = new Text("Sign Up");
        title.setEffect(ds);
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 35));
        title.setFill(Color.WHITE);
        grid.getChildren().add(title);
        grid.getChildren().addAll(labelList);
        grid.getChildren().addAll(buttonList);
        grid.getChildren().addAll(textFields);
        grid.setBackground(new Background(myBI));
        grid.setAlignment(Pos.CENTER);
    }

    private Button loginHelper(TextField nameInput, PasswordField passInput, Label loginSystem, Stage primaryStage,
            Button returnButton) {
        String buttonStyle = "-fx-font-family: Helvetica; -fx-background-color: #fff;";
        Button nameConfirm = loginConfirm(nameInput, passInput, loginSystem, primaryStage);
        returnButton.setStyle(buttonStyle);
        returnButton.setOnAction(e -> {
            nameInput.clear();
            passInput.clear();
            loginSystem.setText("Please type in your username and password");
            primaryStage.setScene(scene);
        });
        GridPane.setConstraints(returnButton, 1, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
        return nameConfirm;
    }

    private Button loginConfirm(TextField nameInput, PasswordField passInput, Label nameSystem, Stage primaryStage) {
        String buttonStyle = "-fx-font-family: Helvetica; -fx-background-color: #fff;";
        Button nameConfirm = new Button("Confirm");
        nameConfirm.setStyle(buttonStyle);
        nameConfirm.setOnAction(e -> {
            if (loginManager.login(nameInput.getText(), passInput.getText())) {
                nameSystem.setText("Log in Successful!");
                nameSystem.setTextFill(Color.WHITE);
                System.out.println("login sucessful");
                loginManager.createApplicationGUI(nameInput.getText(), primaryStage, this).accessUI(primaryStage);
            } else {
                nameSystem.setText("Incorrect Username or Password. Please Try again.");
                nameSystem.setTextFill(Color.WHITE);
            }
        });
        GridPane.setConstraints(nameConfirm, 1, 3);
        return nameConfirm;
    }

    private List<Label> createLabelList(List<String> asList, int increment, String offset) {
        List<Label> labelList = new ArrayList<>();
        int i = 1;
        int j = 0;
        while (j < asList.size()) {
            labelList.add(createLabel(offset + asList.get(j), i));
            i = i + increment;
            j++;
        }
        return labelList;
    }

    private Label createLabel(String input, int rowIndex) {
        Label label = new Label(input);
        label.setEffect(ds);
        GridPane.setConstraints(label, 0, rowIndex);
        return label;
    }

    private PasswordField createPasswordField() {
        PasswordField passInput = new PasswordField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 2);
        return passInput;
    }

    private TextField createTextField(String input, int columnIndex, int rowIndex) {
        TextField textField = new TextField();
        textField.setPromptText(input);
        GridPane.setConstraints(textField, columnIndex, rowIndex);
        return textField;
    }

    private VBox createLayout(List<Button> buttons) {
        String path = "Media/bg.jpg";
        BackgroundImage myBI = new BackgroundImage(new Image(new File(path).toURI().toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        VBox layout = new VBox(spacing);
        Text title = new Text("Bookstore Application");
        title.setEffect(ds);
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 35));
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(0, 0, 0, 0));
        layout.getChildren().add(title);
        layout.getChildren().addAll(buttons); //#b4a6a4
        layout.setBackground(new Background(myBI));
        //#e6e0da
        //layout.setStyle("-fx-background-color: #e6e0da;");

        return layout;
    }

    private Button createExitButton(Stage primaryStage) {
        Button button3 = new Button("Quit");
        button3.setOnAction(e -> logOutSequence(primaryStage, true));
        return button3;
    }

    public void logOutSequence(Stage primaryStage) {
        logOutSequence(primaryStage, false);
    }

    public void logOutSequence(Stage primaryStage, boolean ShutDown) {

        Text endCredits = new Text("Brought to you by, Group_3");

        ImageView gifView = new ImageView();
        ImageView gifView2 = new ImageView();
        ImageView gifView3 = new ImageView();
        ImageView gifView4 = new ImageView();

        String musicFile = "Media/shutdown.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        System.out.println(new File(musicFile).toURI().toString());
        AudioClip mediaPlayer = new AudioClip(sound.getSource());
        if (!ShutDown) {
            {
                musicFile = "Media/shutdown.mp3";
                sound = new Media(new File(musicFile).toURI().toString());
                mediaPlayer = new AudioClip(sound.getSource());
                mediaPlayer.setVolume(0.25);
                mediaPlayer.play();

                PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Create a 6-second pause
                pause.setOnFinished(e -> accessUI(primaryStage));

                pause.play(); // Start the pause
                endCredits = new Text("Logging Out . . .");
                endCredits.setEffect(ds);
            }
        } else {
            endCredits = new Text("Brought to you by, Group 3");
            endCredits.setEffect(ds);
            gifView.setFitWidth(LoginGUI.defaultWidth / 2); // set the width of the GIF image
            gifView.setFitHeight(LoginGUI.defaultHeight / 2); // set the height of the GIF image
            gifView.setPreserveRatio(true);
            gifView.setTranslateX(-gifView.getFitWidth() / 1.5); // set the x position of the GIF image
            gifView.setTranslateY(150); // set the y position of the GIF image
            String gifFile = "Media/KeFqCfBzmPCEuovZDfHBJ.gif";
            Image gifImage = new Image(new File(gifFile).toURI().toString());
            gifView.setImage(gifImage);

            gifView2.setFitWidth(LoginGUI.defaultWidth / 2); // set the width of the GIF image
            gifView2.setFitHeight(LoginGUI.defaultHeight / 2); // set the height of the GIF image
            gifView2.setPreserveRatio(true);
            gifView2.setTranslateX(gifView.getFitWidth() / 2 - 25); // set the x position of the GIF image
            gifView2.setTranslateY(150); // set the y position of the GIF image
            gifView2.setImage(gifImage);
            AffineTransform tx = new AffineTransform(-1, 0, 0, 1, gifView2.getImage().getWidth(), 0);
            // Convert AffineTransform to javafx.scene.transform.Affine
            javafx.scene.transform.Affine flip = new javafx.scene.transform.Affine(
                    tx.getScaleX(), tx.getShearY(), tx.getTranslateX(),
                    tx.getShearX(), tx.getScaleY(), tx.getTranslateY());
            gifView2.getTransforms().add(flip);

            gifView3.setFitWidth(LoginGUI.defaultWidth); // set the width of the GIF image
            gifView3.setFitHeight(LoginGUI.defaultHeight); // set the height of the GIF image
            gifView3.setTranslateX(0); // set the x position of the GIF image
            gifView3.setTranslateY(0); // set the y position of the GIF image
            String gifFile3 = "Media/icegif-3602.gif";
            Image gifImage3 = new Image(new File(gifFile3).toURI().toString());
            gifView3.setImage(gifImage3);

            gifView4.setFitWidth(LoginGUI.defaultWidth / 4); // set the width of the GIF image
            gifView4.setFitHeight(LoginGUI.defaultHeight / 4); // set the height of the GIF image
            gifView4.setTranslateX(0); // set the x position of the GIF image
            gifView4.setTranslateY(LoginGUI.defaultHeight / 2 - 40); // set the y position of the GIF image
            String gifFile4 = "Media/output-onlinegiftools.gif";
            Image gifImage4 = new Image(new File(gifFile4).toURI().toString());
            gifView4.setImage(gifImage4);
            
            musicFile = "Media/Super Mario - Stage Win.mp3";
            sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new AudioClip(sound.getSource());
            mediaPlayer.setVolume(0.15);
            mediaPlayer.play();

            PauseTransition pause = new PauseTransition(Duration.seconds(7)); // Create a 6-second pause
            pause.setOnFinished(e -> System.exit(0));
            pause.play(); // Start the pause
        }
        //MediaView mediaView = new MediaView(mediaPlayer);

        System.out.println("Shutting down ...");

        endCredits.setFont(Font.font("Helvetica", FontWeight.BOLD, 36));
        endCredits.setFill(Color.WHITE);

        String path = "Media/bg.jpg";
        BackgroundImage myBI = new BackgroundImage(new Image(new File(path).toURI().toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        StackPane layout = new StackPane();
        //layout.setStyle("-fx-background-color: white;");
        layout.getChildren().add(gifView);
        layout.getChildren().add(gifView2);
        layout.getChildren().add(gifView3);
        layout.getChildren().add(gifView4);

        layout.getChildren().add(endCredits);
        layout.setBackground(new Background(myBI));
        StackPane.setAlignment(endCredits, Pos.CENTER);
        scene = new Scene(layout, defaultWidth, defaultHeight);
        primaryStage.setScene(scene);

        loginManager.shutdownSequence(); //IMPLEMENT THIS LATER <<CALS SAVING FUNCTION

        primaryStage.show();
    }

}
