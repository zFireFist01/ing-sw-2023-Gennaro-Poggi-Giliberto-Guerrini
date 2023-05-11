package Client.View.GUI;

import Client.ConnectionType;
import Client.NetworkHandler;
import Client.NetworkRMIHandler;
import Client.NetworkSocketHandler;
import Client.View.View;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.GameView;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.LoginEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class GUI extends Application implements View {
    //resources
    ImageView titleImageView;
    ImageView BookshelfImageView;
    ImageView livingRoomImageView;
    ImageView wallpaperImageView;



    ConnectionType connectionType = null;
    private NetworkHandler networkHandler;
    Stage El_loco_Stage;
    private SelectViewEvent currentView;
    private boolean matchStarted = false;
    private boolean MatchEnded = false;


    public GUI () {
        URL titleUrl;
        try {
            titleUrl = new File("/home/due2/Desktop/Progetto_SW_ENG/17_MyShelfie_BGA/Publisher material/Title_2000x618px.png").toURI().toURL();

        }catch(MalformedURLException e){
            System.out.println("Resource not found");
            return;
        }


        Image titleImage = new Image(titleUrl.toString());
        this.titleImageView = new ImageView(titleImage);

        URL bookshelfUrl;
        try {
            bookshelfUrl = new File("/home/due2/Desktop/Progetto_SW_ENG/17_MyShelfie_BGA/boards/bookshelf.png").toURI().toURL();
        }catch(MalformedURLException e){
            System.out.println("Resource not found");
            return;
        }
        Image bookshelfImage = new Image(bookshelfUrl.toString());
        this.BookshelfImageView = new ImageView(bookshelfImage);

        URL livingRoomUrl;

        try {
            livingRoomUrl = new File("/home/due2/Desktop/Progetto_SW_ENG/17_MyShelfie_BGA/boards/livingroom.png").toURI().toURL();
        }catch(MalformedURLException e){
            System.out.println("Resource not found");
            return;
        }
        Image livingRoomImage = new Image(livingRoomUrl.toString());
        this.livingRoomImageView = new ImageView(livingRoomImage);

        URL wallpaperUrl;
        try {
            wallpaperUrl = new File("/home/due2/Desktop/Progetto_SW_ENG/17_MyShelfie_BGA/Publisher material/Display_3.jpg").toURI().toURL();
        }catch(MalformedURLException e){
            System.out.println("Resource not found");
            return;
        }
        Image wallpaperImage = new Image(wallpaperUrl.toString());
        this.wallpaperImageView = new ImageView(wallpaperImage);
    }




    public static void main(String[] args) {



        launch(args);





    }


    public void start(Stage primaryStage) {
        El_loco_Stage = primaryStage;


        titleImageView.setFitWidth(600);
        titleImageView.setPreserveRatio(true);
        titleImageView.setSmooth(true);

        wallpaperImageView.setOpacity(0.7);


        Button rmiButton = new Button("RMI");
        Button socketButton = new Button("SOCKET");

        rmiButton.setMinWidth(200);
        rmiButton.setMinHeight(100);

        socketButton.setMinWidth(200);
        socketButton.setMinHeight(100);

        rmiButton.setStyle("-fx-background-color: #FFC0CB; -fx-border-radius: 15; -fx-background-radius: 15;");

        String hoverStyle1 = "-fx-background-color: #FF69B4; -fx-border-radius: 15; -fx-background-radius: 15;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(255, 105, 180, 0.8), 10, 0, 0, 0);";

        rmiButton.setOnMouseEntered(e -> rmiButton.setStyle(hoverStyle1));
        rmiButton.setOnMouseExited(e -> rmiButton.setStyle("-fx-background-color: #FFC0CB; -fx-border-radius: 15; -fx-background-radius: 15;"));

        String hoverStyle2 = "-fx-background-color: #00BFFF; -fx-border-radius: 15; -fx-background-radius: 15;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0, 191, 255, 0.8), 10, 0, 0, 0);";



        socketButton.setStyle("-fx-background-color: #ADD8E6; -fx-border-radius: 15; -fx-background-radius: 15;");
        socketButton.setOnMouseEntered(e -> socketButton.setStyle(hoverStyle2));
        socketButton.setOnMouseExited(e -> socketButton.setStyle("-fx-background-color: #ADD8E6; -fx-border-radius: 15; -fx-background-radius: 15;"));



        HBox buttons = new HBox(10, rmiButton, socketButton);
        buttons.setAlignment(Pos.CENTER);


        VBox choose = new VBox(10, titleImageView, buttons);
        choose.setAlignment(Pos.CENTER);

        rmiButton.setOnAction(e -> {
            connectionType = ConnectionType.RMI;
            showRMIConnectionWindow(primaryStage);
        });

        socketButton.setOnAction(e -> {
            connectionType = ConnectionType.SOCKET;
            showSocketConnectionWindow(primaryStage);
        });


        StackPane root = new StackPane(wallpaperImageView, choose);
        StackPane.setAlignment(choose, Pos.CENTER);


        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MyShelfie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void showSocketConnectionWindow(Stage primaryStage) {

        Stage loginStage = new Stage();
        loginStage.setTitle("Insert Adress and Port");

        TextField addressField = new TextField();
        addressField.setPromptText("Server IP Address");
        TextField portField = new TextField();
        portField.setPromptText("Server Port");

        Button loginButton = new Button("Connect");
        loginButton.setOnAction(e -> {
            String host = addressField.getText();
            int port = Integer.parseInt(portField.getText());
            networkHandler = new NetworkSocketHandler(host, port, this);

            showInitWindow(primaryStage);


            loginStage.close();
        });

        VBox loginRoot = new VBox(10, addressField, portField, loginButton);
        loginRoot.setAlignment(Pos.CENTER);
        loginRoot.setPadding(new Insets(20));

        Scene loginScene = new Scene(loginRoot, 300, 200);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private void showRMIConnectionWindow(Stage primaryStage) {

        Stage loginStage = new Stage();
        loginStage.setTitle("Insert Port");


        TextField portField = new TextField();
        portField.setPromptText("Server Port");

        Button loginButton = new Button("Connect");
        loginButton.setOnAction(e -> {
            int port = Integer.parseInt(portField.getText());

            try{
                networkHandler = new NetworkRMIHandler(this);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }


            showInitWindow(primaryStage);


            loginStage.close();
        });

        VBox loginRoot = new VBox(10, portField, loginButton);
        loginRoot.setAlignment(Pos.CENTER);
        loginRoot.setPadding(new Insets(20));

        Scene loginScene = new Scene(loginRoot, 300, 200);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private void showInitWindow(Stage primaryStage) {

        Label gameLabel = new Label("Schermata di gioco");


        Button playButton = new Button("Play");
        Button quitButton = new Button("Quit");

        playButton.setOnAction(e -> {
            new Thread(networkHandler).start();
        });

        quitButton.setOnAction(e -> {

            primaryStage.getScene().getWindow().hide();
        });


        HBox buttons = new HBox(10, playButton, quitButton);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, gameLabel, buttons);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MyShelfie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void onSelectViewEvent(SelectViewEvent event) {
        String view = event.getType();
        switch(view){

            case "LoginView" -> {
                Platform.runLater(() -> {
                    onLoginViewEvent(event);
                });
            }
            case "GameView" -> Platform.runLater(() -> {
                onGameViewEvent(event);
            });



        }

    }

    private void onLoginViewEvent(SelectViewEvent event) {
        currentView=event;
        LoginView loginView = (LoginView) event;

        Label messageLabel = new Label(loginView.getMessage());



        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        ComboBox<String> giocatoriComboBox = new ComboBox<>();
        giocatoriComboBox.setItems(FXCollections.observableArrayList(
                "1 Player", "2 Player", "3 Player", "4 Player"));


        Button submitButton = new Button("Submit");



        submitButton.setOnAction(e -> {
            String playerName = nameField.getText();
            if(loginView.isFirstToJoin()) {
                int numPlayer = giocatoriComboBox.getSelectionModel().getSelectedIndex();
                try{
                    networkHandler.onVCEvent(new LoginEvent(playerName, numPlayer));
                }catch(Exception ex){
                    System.out.println("Error in login");
                }

            }else{
                try{
                    networkHandler.onVCEvent(new LoginEvent(playerName));
                }catch(Exception ex){
                    System.out.println("Error in login");
                }
            }
        });

        VBox inputBox = new VBox(10, messageLabel, nameField);
        VBox nameInputBox;
        if(loginView.isFirstToJoin()){
            HBox nameNumberBox = new HBox(10, inputBox, giocatoriComboBox);

            nameNumberBox.setAlignment(Pos.CENTER);
            nameInputBox = new VBox(10, nameNumberBox, submitButton);
            nameInputBox.setAlignment(Pos.CENTER);
        }else {
            nameInputBox = new VBox(10, inputBox, submitButton);
            nameInputBox.setAlignment(Pos.CENTER);
        }



        Scene scene = new Scene(nameInputBox, 800, 600);
        El_loco_Stage.setTitle("MyShelfie");
        El_loco_Stage.setScene(scene);
        El_loco_Stage.show();
    }

    private void onGameViewEvent(SelectViewEvent event) {
        currentView = event;
        GameView gameView = (GameView) event;

        StackPane root;

        if(!matchStarted){
            wallpaperImageView.setOpacity(0.7);

            // Crea un testo con la scritta "Wait for other players to join"
            Text waitingText = new Text("Wait for other players to join");
            waitingText.setFont(Font.font("Arial", FontWeight.BOLD,20));

            waitingText.setFill(Color.WHITE);

            // Crea un riquadro sfocato con angoli smussati
            Label blurredBackground = new Label();
            blurredBackground.setEffect(new BoxBlur(5, 5, 10));
            blurredBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 10;");
            blurredBackground.setPadding(new Insets(10, 20, 10, 20));

            blurredBackground.setMinSize(300, 50);

            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setMaxSize(50, 50);


            StackPane textPane = new StackPane(blurredBackground, waitingText, progressIndicator);
            textPane.setAlignment(Pos.CENTER);

            // Crea un layout StackPane e aggiungi l'immagine di sfondo, il riquadro e il testo
            root = new StackPane(wallpaperImageView, textPane);
            StackPane.setAlignment(textPane, Pos.CENTER);


        }else{
             root=null;
        }
        Scene scene = new Scene(root, 800, 600);

        El_loco_Stage.setTitle("Waiting for other players");
        El_loco_Stage.setScene(scene);
        El_loco_Stage.show();




    }

    @Override
    public void onMVEvent(MVEvent event) {

    }

    public void run() {
        //launch();
    }
}
