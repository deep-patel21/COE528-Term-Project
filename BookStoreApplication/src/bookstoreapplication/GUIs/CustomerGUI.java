/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication.GUIs;

import bookstoreapplication.BookData;
import bookstoreapplication.BookStoreApplication;
import bookstoreapplication.DataStructures.CustomerData;
import bookstoreapplication.GUIs.ApplicationGUI;
import static bookstoreapplication.GUIs.LoginGUI.defaultHeight;
import static bookstoreapplication.GUIs.LoginGUI.defaultWidth;
import java.awt.print.Book;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import bookstoreapplication.LoginManager;
import java.io.File;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TableColumn;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import bookstoreapplication.DataStructures.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;
import java.text.DecimalFormat;
import javafx.animation.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*; 
import javafx.scene.paint.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
/**
 *
 * @author deeps
 */
public class CustomerGUI extends ApplicationGUI {

    public LoginManager LM;
    private Scene CustomerCostScene;
    BookStoreApplication BSA;

    public CustomerGUI(LoginManager LM, BookStoreApplication BSA) {
        this.LM = LM;
        this.BSA = BSA;
    }
   
    @Override
    public void accessUI(Stage primaryStage) {
        TableView<BookData> table = new TableView<>();
        table.setEditable(true);
        
        primaryStage.setResizable(false);
        
        String buttonStyle = "-fx-font-family: Helvetica; -fx-background-color: #fff;";
        
        CustomerData CD = (CustomerData) LM.getCurrentUser();
        Label topParagraph = new Label("Welcome " + CD.getUsername() + ". You have " + CD.getPoints() + " points. Your status is " + CD.getStatus() + ".");
        topParagraph.setMinHeight(40);
        BorderPane.setAlignment(topParagraph, Pos.CENTER);
        table.setItems(FXCollections.observableArrayList(BSA.getBookManager().getBookList()));

        DecimalFormat f = new DecimalFormat("##.00");
        
        TableColumn<BookData, String> col1 = new TableColumn<>("Name of Book");
            col1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((cellData.getValue().getBookName())));
           
        TableColumn<BookData, String> col2 = new TableColumn<>("Price of Book");
            col2.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(f.format(Double.valueOf((cellData.getValue().getPrice())))));
            
            
        TableColumn<BookData, Boolean> col3 = new TableColumn<>("Selection Box");
            col3.setCellValueFactory(new PropertyValueFactory("isSelected"));//this is a boolean property variable's name in BookData
            col3.setCellFactory(tc -> new CheckBoxTableCell<>());
          
        
        double tableWidth = LoginGUI.defaultWidth;
     
//        col1.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
//        col2.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
//        col3.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
//           
        col1.setMinWidth(tableWidth / 3);
        col2.setMinWidth(tableWidth / 3);
        col3.setMinWidth(tableWidth / 3);
        //col3.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        //col3.setCellFactory(CheckBoxTableCell.forTableColumn(col3));

        table.getColumns().addAll(col1, col2, col3);
        
        Button buyBtn = new Button("Buy");
        buyBtn.setOnAction(e -> RegularPurchase(primaryStage, table, f));
        
        Button redeemBtn = new Button("Redeem Points to Buy");
        redeemBtn.setOnAction(e -> PointPurchase(primaryStage, table, f));
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> logoutSequence(primaryStage));
        
        buyBtn.setStyle(buttonStyle); 
        redeemBtn.setStyle(buttonStyle); 
        logoutBtn.setStyle(buttonStyle); 
        
        FlowPane buttons = new FlowPane(10, 10, buyBtn, redeemBtn, logoutBtn);
        buttons.setAlignment(Pos.CENTER);
        buttons.setMinHeight(40);

        BorderPane root = new BorderPane();
        root.setTop(topParagraph);
        root.setCenter(table);
        root.setBottom(buttons);
        root.setStyle("-fx-background-color: #e6e0da;");
        Scene scene = new Scene(root, LoginGUI.defaultWidth, LoginGUI.defaultHeight);
        primaryStage.setTitle("Book Store Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
    private void SetupCostScene(Stage primaryStage, double totalCost, double points, String status, double redeemedPoints, double earnedPoints){
        
        String path = "Media/bg.jpg";
        BackgroundImage myBI = new BackgroundImage(new Image(new File(path).toURI().toString()),BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        
        //Creating the button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> logoutSequence(primaryStage));
            
        //Button Styling 
        String buttonStyle = "-fx-font-family: Helvetica; -fx-background-color: #fff; -fx-font-size: 16pt;";
        
        //Button backBtn = new Button("Back");
        DropShadow ds = new DropShadow(); 
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);
        //Creating the labels
        Label totalCostLabel = new Label("Total Cost:");
        Label pointsLabel = new Label("Points:");
        Label statusLabel = new Label("Status:");
        Label titleLabel = new Label("Thank you for shopping with us, " + LM.getCurrentUser().getUsername() + ". \nHere is your reciept!");        
        Label redeemedLabel = new Label("Points redeemed:");
        Label earnedLabel = new Label("Points earned:");
      
        //Setting up the button
        logoutButton.setTranslateX(375);
        logoutButton.setTranslateY(-75);
        logoutButton.setMinSize(150, 50);
        logoutButton.setStyle(buttonStyle); 
        //logoutButton.setFont(Font.font("Helvetica", 20));
        
        //backBtn.setTranslateX(375);
        //backBtn.setTranslateY(-180);
        //backBtn.setMinSize(150, 50);
        //backBtn.setFont(Font.font("Arial", 20));
        
        //Creating the text fields
        TextField totalCostField = new TextField();
        TextField pointsField = new TextField(); 
        TextField statusField = new TextField();
        TextField redeemedField = new TextField();
        TextField earnedField = new TextField();
        
        //Setting up the labels
        titleLabel.setEffect(ds); 
        titleLabel.setTextAlignment(TextAlignment.CENTER); 
        titleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
        titleLabel.setTextFill(Color.web("F3F2ED"));
        titleLabel.setTranslateY(150);
        totalCostLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        totalCostLabel.setTextFill(Color.WHITE);
        totalCostLabel.setEffect(ds); 
        pointsLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        pointsLabel.setTextFill(Color.WHITE);
        pointsLabel.setEffect(ds); 
        statusLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setEffect(ds); 
        redeemedLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        redeemedLabel.setTextFill(Color.WHITE);
        redeemedLabel.setTranslateX(50);
        redeemedLabel.setEffect(ds); 
        earnedLabel.setFont(Font.font("Helvetica",FontWeight.BOLD, 20));
        earnedLabel.setTextFill(Color.WHITE);
        earnedLabel.setTranslateX(50);
        earnedLabel.setEffect(ds); 
        
        //Setting up the text fields 
        totalCostField.setMinWidth(80 + Double.toString(Math.floor(totalCost)).length());
        pointsField.setMinWidth(80 + Double.toString(Math.floor(points)).length());
        statusField.setMinWidth(80);
        totalCostField.setEditable(false);
        pointsField.setEditable(false);
        statusField.setEditable(false);
        redeemedField.setEditable(false);
        earnedField.setEditable(false);
        totalCostField.setText(String.format("$%.2f", totalCost));
        
        totalCostField.setEffect(ds);
        pointsField.setEffect(ds);
        statusField.setEffect(ds);
        redeemedField.setEffect(ds);
        earnedField.setEffect(ds);
    
        redeemedField.setText(String.format("%.0f", redeemedPoints));
        pointsField.setText(String.format("%.0f", points));
   
        earnedField.setText(String.format("%.0f", earnedPoints));
        statusField.setText(status);
        
        //Creating and setting up the grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(170);
        grid.setVgap(10);

        grid.add(totalCostLabel, 0, 1);
        grid.add(totalCostField, 1, 1);
        grid.add(redeemedLabel, 0, 2);
        grid.add(redeemedField, 1, 2);
        grid.add(pointsLabel, 0, 3);
        grid.add(pointsField, 1, 3);
        grid.add(earnedLabel, 0, 4);
        grid.add(earnedField, 1, 4);
        grid.add(statusLabel, 0, 5);
        grid.add(statusField, 1, 5);
        
        grid.setTranslateY(170);
        grid.setTranslateX(205);
        
        ImageView gifView = new ImageView();
        gifView.setFitWidth(LoginGUI.defaultWidth); // set the width of the GIF image
        gifView.setFitHeight(LoginGUI.defaultHeight); // set the height of the GIF image
        gifView.setTranslateX(0); // set the x position of the GIF image
        gifView.setTranslateY(0); // set the y position of the GIF image
        String gifFile = "Media/confetti.gif";
        Image gifImage = new Image(new File(gifFile).toURI().toString());
        gifView.setImage(gifImage);
        
        ImageView gifView2 = new ImageView();
        gifView2.setFitWidth(LoginGUI.defaultWidth); // set the width of the GIF image
        gifView2.setFitHeight(LoginGUI.defaultHeight); // set the height of the GIF image
        gifView2.setTranslateX(0); // set the x position of the GIF image
        gifView2.setTranslateY(0); // set the y position of the GIF image
        String gifFile2 = "Media/border.png";
        Image gifImage2 = new Image(new File(gifFile2).toURI().toString());
        gifView2.setImage(gifImage2);

        ImageView gifView3 = new ImageView();
        gifView3.setFitWidth(LoginGUI.defaultWidth); // set the width of the GIF image
        gifView3.setFitHeight(LoginGUI.defaultHeight); // set the height of the GIF image
        gifView3.setTranslateX(0); // set the x position of the GIF image
        gifView3.setTranslateY(0); // set the y position of the GIF image
        String gifFile3 = "Media/2a91ebdf918337b5254207bf94b212e7.gif";
        Image gifImage3 = new Image(new File(gifFile3).toURI().toString());
        gifView3.setImage(gifImage3);

        String musicFile = "Media/167535__jordanielmills__01-winner.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.25);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        
        BorderPane root = new BorderPane();
        
        root.getChildren().add(gifView);
        root.getChildren().add(gifView3);
        root.getChildren().add(gifView2);
        
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(grid);
        root.setAlignment(grid, Pos.CENTER);
        root.setBottom(logoutButton);
        root.setBackground(new Background(myBI));
        


        
        CustomerCostScene = new Scene(root, LoginGUI.defaultWidth, LoginGUI.defaultHeight);
    }

    private void RegularPurchase(Stage primaryStage, TableView<BookData> table, DecimalFormat g){
        //CALL THE CART MANAGER CLASS FOR A REGULAR PURCHASE
        //PUT MOST OF THE LOGIC IN THE CART MANAGER CLASS
        
        System.out.println("REGULAR PURCHASE TEST");
        
        CustomerData CD = (CustomerData) LM.getCurrentUser(); 
        for (BookData BD : table.getItems().filtered(BookData::isSelected)) {

            BSA.getCartManager().addselectbook(BD);
            BSA.getBookManager().removeBook(BD); // The method for removing books is not yet implemented in book manager.
         //  BSA.getBookManager().getCM().removeBook(BD);
        }
        
        double ap = BSA.getCartManager().getTotalPrice()*10;
        ap = Double.parseDouble(g.format(ap));
        CD.addPoints((int)ap);
        double earnedPoints = (int)ap;
        //double points = CD.getPoints();
        //String status = CD.getStatus();
        SetupCostScene(primaryStage, BSA.getCartManager().getTotalPrice(), CD.getPoints(), CD.getStatus(), 0, earnedPoints);//UPDATE THIS AFTER YOU DO THE LOGIC FOR CALCULATING COST< POINTS< STATUS
        for (BookData BD : table.getItems().filtered(BookData::isSelected)) {
            BSA.getCartManager().removeselectbook(BD);

        }

        for (BookData i : BSA.getBookManager().getBookList()){
            System.out.println(i.getBookName());
        }        
        primaryStage.setScene(CustomerCostScene);
        
    }
    
    private void PointPurchase(Stage primaryStage,  TableView<BookData> table, DecimalFormat g){
        //CALL THE CART MANAGER CLASS FOR A POINT PURCHASE
        //PUT MOST OF THE LOGIC IN THE CART MANAGER CLASS
        
        System.out.println("PointsPurchase");
        
        CustomerData CD = (CustomerData) LM.getCurrentUser(); 
        double points = CD.getPoints();
        String status = CD.getStatus();
        for (BookData BD : table.getItems().filtered(BookData::isSelected)) {

            BSA.getCartManager().addselectbook(BD);
            BSA.getBookManager().removeBook(BD); // The method for removing books is not yet implemented in book manager.
         //  BSA.getBookManager().getCM().removeBook(BD);
        }
        double cost = BSA.getCartManager().getTotalPrice();
        System.out.println(""+cost);
        System.out.println(""+(int)cost*100);
        //int pointsLoss = (int)cost*100;
        double totalCost = 0;
        double rp = cost*100;


        int discount = CD.removePoints((int)rp);
        double redeemedPoints = discount;
        if (discount == CD.getPoints()){
            totalCost = 0;
        }
        else{
            totalCost = cost - (double)(discount/100.0);
        }
        totalCost = Double.parseDouble(g.format(totalCost));
        double ap = totalCost*10;
        System.out.println(""+ap);
        CD.addPoints((int)ap);
        double earnedPoints = (int)ap;

        SetupCostScene(primaryStage, totalCost, CD.getPoints(), CD.getStatus(), redeemedPoints, earnedPoints);//UPDATE THIS AFTER YOU DO THE LOGIC FOR CALCULATING COST< POINTS< STATUS

        for (BookData BD : table.getItems().filtered(BookData::isSelected)) {
            BSA.getCartManager().removeselectbook(BD);

        }
        primaryStage.setScene(CustomerCostScene);
    }
    
    private void logoutSequence(Stage primaryStage) {
        LoginGUI GUI = new LoginGUI();
        GUI.setLoginPresenter(LM);
        
        GUI.logOutSequence(primaryStage);
       
    }
}
