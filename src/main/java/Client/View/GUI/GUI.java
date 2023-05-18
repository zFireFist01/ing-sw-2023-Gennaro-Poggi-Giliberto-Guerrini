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
import Utils.ConnectionInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class GUI extends Application implements View {
    //resources
    ImageView titleImageView;
    ImageView BookshelfImageView;
    ImageView livingRoomImageView;
    ImageView wallpaperImageView;

    ConnectionType connectionType = null;
    private NetworkHandler networkHandler;
    private Stage primaryStage;
    private Stage newStage;

    private SelectViewEvent currentView;
    private boolean matchStarted = false;

    private boolean MatchEnded = false;
    private ConnectionInfo connectionInfo;
    private boolean isReconnecting;
    private String previousNickname = null; //Will be null if isReconnecting==false


    private FXMLLoader loader = new FXMLLoader();

    Image titleImage;
    Image bookshelfImage;
    Image livingRoomImage;
    Image wallpaperImage;
    Image catImage;


    @FXML
    private Button yesButtonReConnection;
    @FXML
    private Button noButtonReConnection;
    @FXML
    private Button socketButton;
    @FXML
    private Button RMIButton;
    @FXML
    private Button submitUsernameAndPlayersButton;
    @FXML
    private Button submitUsernameButton;
    @FXML
    private Button connectButton;
    @FXML
    private TextField addressServer;
    @FXML
    private TextField portServer;
    @FXML
    private Button connectButtonRMI;
    @FXML
    private TextField portServerRMI;
    @FXML
    private Button quitButton;
    @FXML
    private Button playButton;





    public GUI () {


        titleImage = new Image(getClass().getResource("/Publisher material/Title 2000x618px.png").toString());
        this.titleImageView = new ImageView(titleImage);

        bookshelfImage = new Image(getClass().getResource("/boards/bookshelf.png").toString());
        this.BookshelfImageView = new ImageView(bookshelfImage);

        livingRoomImage = new Image(getClass().getResource("/boards/livingroom.png").toString());
        this.livingRoomImageView = new ImageView(livingRoomImage);

        wallpaperImage = new Image(getClass().getResource("/Publisher material/Display_3.jpg").toString());

        this.wallpaperImageView = new ImageView(wallpaperImage);
        catImage = new Image(getClass().getResource("/item tiles/Gatti1.1.png").toString());



        connectionInfo = null;
        isReconnecting = false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {



        loader.setLocation(getClass().getResource("/Gameview.fxml"));
        Parent root=null;
        try{
            root = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Titolo della finestra");
        stage.show();


        ImageView i = (ImageView)root.lookup("#livingRoomImage");

        //Image newImage = new Image(getClass().getResource("/Publisher material/Display_3.jpg").toString());


        i.setImage(livingRoomImage);

        StackPane s = (StackPane)root.lookup("#bookshelf3");
        //wait for 5 second

        StackPane my = (StackPane)root.lookup("#mybookshelf");

        my.setDisable(false);

        GridPane grid = (GridPane)root.lookup("#firstBookshelfGrid");
        TextFlow flow = (TextFlow) root.lookup("#chatframe");

        ImageView cat = new ImageView(catImage);
        cat.setFitWidth(30);
        cat.setFitHeight(30);
        Text text1 = new Text("Ciao sono Leonardo da Vinci ");
        flow.getChildren().add(text1);

        AnchorPane main = (AnchorPane)root.lookup("#MainPane") ;
        ImageView wallpaper =(ImageView)root.lookup("#wallpaper") ;

        wallpaper.fitHeightProperty().bind(main.heightProperty());
        wallpaper.fitWidthProperty().bind(main.widthProperty());

        grid.add(cat,2,5);



        s.setVisible(true);





        this.primaryStage = primaryStage;


        /*
        Text askIsReconnnecting = new Text("Are you reconnecting?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction(e -> {
            isReconnecting = true;
            showIsReconnectingWindow(primaryStage);
            //primaryStage.close();
        });
        noButton.setOnAction(e -> {
            isReconnecting = false;
            showNormalWindow(primaryStage);
            //primaryStage.close();
        });
        HBox reconnectingBox = new HBox(askIsReconnnecting, yesButton, noButton);
        reconnectingBox.setAlignment(Pos.CENTER);
        reconnectingBox.setSpacing(20);
        reconnectingBox.setPadding(new Insets(20, 20, 20, 20));
        Scene reconnectingScene = new Scene(reconnectingBox, 500, 200);
        */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Re_Connection_Requests.fxml"));
        fxmlLoader.setController(this);
        root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
        primaryStage.show();

        /*
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

         */
    }

    private void showIsReconnectingWindow(Stage primaryStage){
        Stage isReconnectingStage = new Stage();
        isReconnectingStage.setTitle("Reconnection process");

        Text askPreviousNicknameText = new Text("Please insert your previous nickname");
        TextField askPreviousNickname = new TextField();
        askPreviousNickname.setPromptText("Previous Nickname");
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            previousNickname = askPreviousNickname.getText();
            showNormalWindow(primaryStage);
            isReconnectingStage.close();
        });
        /*TextField connectionInfoField = new TextField();
        connectionInfoField.setPromptText("Connection Info");*/

        VBox isReconnectingRoot = new VBox(10, askPreviousNicknameText, askPreviousNickname, okButton);
        isReconnectingRoot.setAlignment(Pos.CENTER);
        isReconnectingRoot.setPadding(new Insets(20));

        Scene isReconnectingScene = new Scene(isReconnectingRoot, 300, 200);
        isReconnectingStage.setScene(isReconnectingScene);
        isReconnectingStage.show();
    }

    private void showNormalWindow(Stage primaryStage){
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

        String localIP = null;
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            localIP = ipAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        rmiButton.setOnAction(e -> {
            connectionType = ConnectionType.RMI;
            showRMIConnectionWindow(primaryStage);
        });

        socketButton.setOnAction(e -> {
            connectionType = ConnectionType.SOCKET;
            showSocketConnectionWindow(primaryStage);
        });

        this.connectionInfo = new ConnectionInfo(localIP, connectionType, previousNickname);
        StackPane root = new StackPane(wallpaperImageView, choose);
        StackPane.setAlignment(choose, Pos.CENTER);


        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MyShelfie");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private void showSocketConnectionWindow(Stage primaryStage) {

        Stage loginStage = new Stage();
        loginStage.setTitle("Insert Address and Port");

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
                    try {
                        onLoginViewEvent(event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            case "GameView" -> Platform.runLater(() -> {
                onGameViewEvent(event);
            });



        }

    }

    private void onLoginViewEvent(SelectViewEvent event) throws IOException {
        currentView = event;
        LoginView loginView = (LoginView) event;

        /*
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
        primaryStage.setTitle("MyShelfie");
        primaryStage.setScene(scene);
        primaryStage.show();

        */
        //TODO: da continuare
        if(loginView.isFirstToJoin()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Select_Number_Of_Players.fxml"));
            fxmlLoader.setController(this);
            Parent newRoot = fxmlLoader.load();
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);
            primaryStage.show();
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Insert_Username.fxml"));
            fxmlLoader.setController(this);
            Parent newRoot = fxmlLoader.load();
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);
            primaryStage.show();
        }
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

        primaryStage.setTitle("Waiting for other players");
        primaryStage.setScene(scene);
        primaryStage.show();




    }

    @Override
    public void onMVEvent(MVEvent event) {
    }

    public void run() {
        //launch();
    }

    @Override
    public boolean isReconnecting() {
        return isReconnecting;
    }

    @Override
    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }



    //Buttons Methods

    @FXML
    private void onClickYesReconnect(ActionEvent event) throws IOException {
        this.isReconnecting = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) yesButtonReConnection.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onClickNoReconnect(ActionEvent event) throws IOException {
        this.isReconnecting = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) noButtonReConnection.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onClickSocketButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Insert_IP_Port_Server.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) socketButton.getScene().getWindow();
        this.connectionType = ConnectionType.SOCKET;
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onClickConnectButton(ActionEvent event) throws IOException {
        String host = addressServer.getText();
        int port = Integer.parseInt(portServer.getText());
        this.networkHandler = new NetworkSocketHandler(host, port, this);

        String localIP = null;
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            localIP = ipAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.connectionInfo = (new ConnectionInfo(localIP, this.connectionType, this.previousNickname));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FirstView.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) connectButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onClickRMIButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Insert_Port_Server.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) RMIButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onClickConnectRMIButton(ActionEvent event) throws IOException {
        int port = Integer.parseInt(portServerRMI.getText());

        try{
            networkHandler = new NetworkRMIHandler(this);
            String localIP = null;
            try {
                InetAddress ipAddress = InetAddress.getLocalHost();
                localIP = ipAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            connectionInfo = new ConnectionInfo(localIP,connectionType, previousNickname);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FirstView.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) connectButtonRMI.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onClickSubmitUsernamePlayerButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WaitingPlayersToConnect.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) submitUsernameAndPlayersButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onClickUSubmitUsernameButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WaitingPlayersToConnect.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) submitUsernameButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void onQuitButton(ActionEvent event){
        Stage currentStage = (Stage) quitButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onPlayButton(ActionEvent event) throws IOException{
        new Thread(this.networkHandler).start();
    }

}
