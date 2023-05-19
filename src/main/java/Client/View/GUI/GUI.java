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
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.PersonalGoalCard;
import Server.Model.Chat.Message;
import Server.Model.GameItems.LivingRoom;
import Server.Model.GameItems.LivingRoomTileSpot;
import Server.Model.GameItems.PointsTile;
import Server.Model.GameItems.TileType;
import Server.Model.LightMatch;
import Server.Model.Player.Player;
import Utils.ConnectionInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.util.HashMap;
import java.util.Random;

public class GUI extends Application implements View {
    //resources
    ImageView titleImageView;
    private String myNick;

    ImageView wallpaperImageView;

    ConnectionType connectionType = null;
    private NetworkHandler networkHandler;
    private Stage primaryStage;

    private SelectViewEvent currentView;
    private boolean matchStarted = false;

    private boolean MatchEnded = false;
    private ConnectionInfo connectionInfo;
    private boolean isReconnecting;
    private String previousNickname = null; //Will be null if isReconnecting==false
    private int numberPlayers;
    private Player me;
    private CommonGoalCard firstCommonGoalCard;
    private CommonGoalCard secondCommonGoalCard;
    private PersonalGoalCard personalGoalCard;



    private FXMLLoader loader = new FXMLLoader();

    //IMAGES
    private Image titleImage;
    private Image bookshelfImage;
    private Image livingRoomImage;
    private Image wallpaperImage;
    private Image chairImage;
    //TILES IMAGES
    private Image catImage1;
    private Image catImage2;
    private Image catImage3;
    private Image bookImage1;
    private Image bookImage2;
    private Image bookImage3;
    private Image frameImage1;
    private Image frameImage2;
    private Image frameImage3;
    private Image trophyImage1;
    private Image trophyImage2;
    private Image trophyImage3;
    private Image gamesImage1;
    private Image gamesImage2;
    private Image gamesImage3;
    private Image plantsImage1;
    private Image plantsImage2;
    private Image plantsImage3;

    //CARDS IMAGES
    private Image firstCommonGoalImage;
    private Image secondCommonGoalImage;
    private Image personalGoalImage;

    //POINTS IMAGES
    private Image points8Image;
    private Image points6Image;
    private Image points4Image;
    private Image points2Image;
    private Image points1Image;







    private HashMap<Integer, Player> players = new HashMap<>();



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
    @FXML
    private TextField onlyUsernameField;
    @FXML
    private TextField usernameField;
    @FXML
    private ComboBox<String> numberPlayersMenu;

    private Parent gameRoot;


    private String[] numberPlayersArray = {"2 Players", "3 Players", "4 Players"};



    public GUI () {

        //SETTING IMAGES
        titleImage = new Image(getClass().getResource("/Publisher material/Title 2000x618px.png").toString());
        bookshelfImage = new Image(getClass().getResource("/boards/bookshelf.png").toString());
        livingRoomImage = new Image(getClass().getResource("/boards/livingroom.png").toString());
        wallpaperImage = new Image(getClass().getResource("/Publisher material/Display_3.jpg").toString());
        this.wallpaperImageView = new ImageView(wallpaperImage);

        chairImage = new Image(getClass().getResource("/misc/firstplayertoken.png").toString());

        catImage1 = new Image(getClass().getResource("/item tiles/Gatti1.1.png").toString());
        catImage2 = new Image(getClass().getResource("/item tiles/Gatti1.2.png").toString());
        catImage3 = new Image(getClass().getResource("/item tiles/Gatti1.3.png").toString());

        bookImage1 = new Image(getClass().getResource("/item tiles/Libri1.1.png").toString());
        bookImage2 = new Image(getClass().getResource("/item tiles/Libri1.2.png").toString());
        bookImage3 = new Image(getClass().getResource("/item tiles/Libri1.3.png").toString());

        frameImage1 = new Image(getClass().getResource("/item tiles/Cornici1.1.png").toString());
        frameImage2 = new Image(getClass().getResource("/item tiles/Cornici1.2.png").toString());
        frameImage3 = new Image(getClass().getResource("/item tiles/Cornici1.3.png").toString());

        trophyImage1 = new Image(getClass().getResource("/item tiles/Trofei1.1.png").toString());
        trophyImage2 = new Image(getClass().getResource("/item tiles/Trofei1.2.png").toString());
        trophyImage3 = new Image(getClass().getResource("/item tiles/Trofei1.3.png").toString());

        gamesImage1 = new Image(getClass().getResource("/item tiles/Giochi1.1.png").toString());
        gamesImage2 = new Image(getClass().getResource("/item tiles/Giochi1.2.png").toString());
        gamesImage3 = new Image(getClass().getResource("/item tiles/Giochi1.3.png").toString());

        plantsImage1 = new Image(getClass().getResource("/item tiles/Piante1.1.png").toString());
        plantsImage2 = new Image(getClass().getResource("/item tiles/Piante1.2.png").toString());
        plantsImage3 = new Image(getClass().getResource("/item tiles/Piante1.3.png").toString());

        points8Image = new Image(getClass().getResource("/scoring tokens/scoring_8.jpg").toString());
        points6Image = new Image(getClass().getResource("/scoring tokens/scoring_6.jpg").toString());
        points4Image = new Image(getClass().getResource("/scoring tokens/scoring_4.jpg").toString());
        points2Image = new Image(getClass().getResource("/scoring tokens/scoring_2.jpg").toString());
        points1Image = new Image(getClass().getResource("/scoring tokens/end game.jpg").toString());

        connectionInfo = null;
        isReconnecting = false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {






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
        Parent root = fxmlLoader.load();
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
            /*case "GameView" -> Platform.runLater(() -> {
                onGameViewEvent(event);
            });
            */

        }

    }

    private void onLoginViewEvent(SelectViewEvent event) throws IOException {
        currentView = event;
        LoginView loginView = (LoginView) event;

        if(loginView.isFirstToJoin()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Select_Number_Of_Players.fxml"));
            fxmlLoader.setController(this);
            Parent newRoot = fxmlLoader.load();
            Text tmp = (Text)newRoot.lookup("#errormessage");
            if(!loginView.getMessage().equals("Insert your username")){
                tmp.setText(loginView.getMessage());
            }
            numberPlayersMenu.setItems(FXCollections.observableArrayList("2 Players", "3 Players", "4 Players"));
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);
            primaryStage.show();
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Insert_Username.fxml"));
            fxmlLoader.setController(this);
            Parent newRoot = fxmlLoader.load();
            Text tmp = (Text)newRoot.lookup("#errormessage");
            if(!loginView.getMessage().equals("Insert your username")){
                tmp.setText(loginView.getMessage());
            }
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);
            primaryStage.show();
        }
    }

    /*
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
    */


    @Override
    public void onMVEvent(MVEvent event) {
        String methodName = event.getMethodName();


        switch(methodName) {
            case "onModifiedChatEvent" -> {
                onModifiedChatEvent((Message)event.getValue());
                return;
            }
            case "onModifiedBookshelfEvent" -> {
                onModifiedBookshelfEvent(event.getMatch());
            }
            case "onModifiedLivingRoomEvent" -> {
                onModifiedLivingRoomEvent(event.getMatch());
            }
            case "onModifiedMatchEndedEvent" -> {
                onModifiedMatchEndedEvent(event.getMatch());
            }
            case "onModifiedPointsEvent" -> {

                onModifiedPointsEvent(event.getMatch());
            }
            case "onMatchStartedEvent" -> {

                Platform.runLater(() -> {
                    onMatchStartedEvent(event.getMatch());
                });

            }
            case "onModifiedTurnEvent" -> {
                Platform.runLater(() -> {
                        onModifiedTurnEvent(event.getMatch());
                });

            }

        }
    }

    private void onModifiedTurnEvent(LightMatch match) {
    }

    private void onMatchStartedEvent(LightMatch match) {
        this.matchStarted = true;
        numberPlayers = match.getPlayers().size();
        for(int i = 0; i < numberPlayers; i++){
            players.put(i,match.getPlayers().get(i));
            if (match.getPlayers().get(i).getPlayerNickName().equals(myNick)){
                me = match.getPlayers().get(i);
                if(i!=0) {
                    Player tmp = players.get(0);
                    players.put(0, me);
                    players.put(i, tmp);
                }
            }

        }

        loader.setLocation(getClass().getResource("/Gameview.fxml"));
        loader.setController(this);

        try{
            gameRoot = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }

        Scene scene = new Scene(gameRoot);


        if(numberPlayers==3){
            StackPane s = (StackPane)gameRoot.lookup("#player2Bookshelf");
            s.setVisible(true);
            Text name = (Text)gameRoot.lookup("#player1Name");
            name.setText(players.get(1).getPlayerNickName());
            name = (Text)gameRoot.lookup("#player2Name");
            name.setText(players.get(2).getPlayerNickName());

        }else if(numberPlayers==4){
            StackPane s = (StackPane)gameRoot.lookup("#player2Bookshelf");
            s.setVisible(true);
            s = (StackPane)gameRoot.lookup("#player3Bookshelf");
            s.setVisible(true);
            Text name = (Text)gameRoot.lookup("#player1Name");
            name.setText(players.get(1).getPlayerNickName());
            name = (Text)gameRoot.lookup("#player2Name");
            name.setText(players.get(2).getPlayerNickName());
            name = (Text)gameRoot.lookup("#player3Name");
            name.setText(players.get(3).getPlayerNickName());

        }else{
            Text name = (Text)gameRoot.lookup("#player1Name");
            name.setText(players.get(1).getPlayerNickName());
        }

        ImageView wallpaper =(ImageView)gameRoot.lookup("#wallpaper") ;

        AnchorPane main = (AnchorPane)gameRoot.lookup("#MainPane") ;
        wallpaper.fitHeightProperty().bind(main.heightProperty());
        wallpaper.fitWidthProperty().bind(main.widthProperty());

        firstCommonGoalCard=match.getCommonGoals()[0];
        secondCommonGoalCard=match.getCommonGoals()[1];
        personalGoalCard=me.getPersonalGoalCard();

        System.out.println(String.valueOf(firstCommonGoalCard.getCardID()));

        firstCommonGoalImage=new Image(getClass().getResource(("/common goal cards/"+String.valueOf(firstCommonGoalCard.getCardID())+".jpg")).toString());
        secondCommonGoalImage=new Image(getClass().getResource(("/common goal cards/"+String.valueOf(secondCommonGoalCard.getCardID())+".jpg")).toString());
        personalGoalImage=new Image(getClass().getResource(("/personal goal cards/Personal_Goals"+String.valueOf(personalGoalCard.getCardID()==1 ? "" : personalGoalCard.getCardID())+".png")).toString());

        ImageView firstCommonGoalImageView = (ImageView)gameRoot.lookup("#firstcommongoal");
        firstCommonGoalImageView.setImage(firstCommonGoalImage);

        ImageView secondCommonGoalImageView = (ImageView)gameRoot.lookup("#secondcommongoal");
        secondCommonGoalImageView.setImage(secondCommonGoalImage);

        ImageView personalGoalImageView = (ImageView)gameRoot.lookup("#personalgoal");
        personalGoalImageView.setImage(personalGoalImage);

        for(int i=0; i<numberPlayers;i++){
            if(players.get(i).getPlayerNickName().equals(match.getCurrentPlayer().getPlayerNickName())){
                ImageView firsttoken = (ImageView)gameRoot.lookup("#player"+String.valueOf(i)+"firsttoken");
                firsttoken.setVisible(true);
                break;
            }
        }







        updateBookshelfs(match);
        updateLivingroom(match);
        updatePoints(match);


        primaryStage.setScene(scene);
        primaryStage.setTitle("MyShelfie");
        primaryStage.show();


    }



    private void updateBookshelfs(LightMatch match){

    }

    private void updateLivingroom(LightMatch match){

        LivingRoomTileSpot livingroom[][]=match.getLivingRoom().getTileMatrix();
        GridPane grid = (GridPane)gameRoot.lookup("#livingroomtiles");
        int random;

        for(int i=0;i<9;i++){
            for(int j=0; j<9;j++){
                if(livingroom[i][j].getTileType()== TileType.CATS){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    random = (int)(Math.random() * 3 + 1);
                    if (random == 1) {
                        tmp.setImage(catImage1);
                    }else if(random == 2){
                        tmp.setImage(catImage2);
                    }else{
                        tmp.setImage(catImage3);
                    }
                }else if(livingroom[i][j].getTileType()== TileType.TROPHIES){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    random = (int)(Math.random() * 3 + 1);
                    if (random == 1) {
                        tmp.setImage(trophyImage1);
                    }else if(random == 2){
                        tmp.setImage(trophyImage2);
                    }else{
                        tmp.setImage(trophyImage3);
                    }
                }else if(livingroom[i][j].getTileType()== TileType.FRAMES){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    random = (int)(Math.random() * 3 + 1);
                    if (random == 1) {
                        tmp.setImage(frameImage1);
                    }else if(random == 2){
                        tmp.setImage(frameImage2);
                    }else{
                        tmp.setImage(frameImage3);
                    }
                }else if(livingroom[i][j].getTileType()== TileType.PLANTS){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    random = (int)(Math.random() * 3 + 1);
                    if (random == 1) {
                        tmp.setImage(plantsImage1);
                    }else if(random == 2){
                        tmp.setImage(plantsImage2);
                    }else{
                        tmp.setImage(plantsImage3);
                    }
                }else if(livingroom[i][j].getTileType()== TileType.GAMES){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    random = (int)(Math.random() * 3 + 1);
                    if (random == 1) {
                        tmp.setImage(gamesImage1);
                    }else if(random == 2){
                        tmp.setImage(gamesImage2);
                    }else{
                        tmp.setImage(gamesImage3);
                    }
                }else if(livingroom[i][j].getTileType()== TileType.BOOKS){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    random = (int)(Math.random() * 3 + 1);
                    if (random == 1) {
                        tmp.setImage(bookImage1);
                    }else if(random == 2){
                        tmp.setImage(bookImage2);
                    }else{
                        tmp.setImage(bookImage3);
                    }
                }
            }
        }

    }

    private ImageView getImageViewAt(GridPane gridPane, int i, int j) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {

                int rowIndex = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
                int columnIndex = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);

                if (rowIndex == i && columnIndex == j) {
                    return (ImageView) node;
                }
            }
        }
        return null;
    }

    private void updatePoints(LightMatch match){
        firstCommonGoalCard=match.getCommonGoals()[0];
        secondCommonGoalCard=match.getCommonGoals()[1];
        ImageView firstCommonGoalPoints = (ImageView)gameRoot.lookup("#firstcommongoalpoints");
        ImageView secondCommonGoalPoints = (ImageView)gameRoot.lookup("#secondcommongoalpoints");
        if(firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_2){
            firstCommonGoalPoints.setImage(points8Image);

        }else if(firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_2){
            firstCommonGoalPoints.setImage(points6Image);
        }else if(firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_2){
            firstCommonGoalPoints.setImage(points4Image);

        } else if (firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1) == PointsTile.TWO_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1) == PointsTile.TWO_2) {
            firstCommonGoalPoints.setImage(points2Image);
        }else{
            firstCommonGoalPoints.setImage(null);
        }

        if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_1) {
            secondCommonGoalPoints.setImage(points8Image);
        }else if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_1){
            secondCommonGoalPoints.setImage(points6Image);
        }else if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_1){
            secondCommonGoalPoints.setImage(points4Image);
        }else if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.TWO_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.TWO_1){
            secondCommonGoalPoints.setImage(points2Image);
        }else{
            secondCommonGoalPoints.setImage(null);
        }


    }

    private void onModifiedPointsEvent(LightMatch match) {
    }

    private void onModifiedMatchEndedEvent(LightMatch match) {
    }

    private void onModifiedLivingRoomEvent(LightMatch match) {
    }

    private void onModifiedBookshelfEvent(LightMatch match) {
    }

    private void onModifiedChatEvent(Message value) {
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

    @FXML
    private void onTileSelected(ActionEvent event) throws IOException {


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
        int numPlayer = numberPlayersMenu.getSelectionModel().getSelectedIndex() + 2;
        this.myNick = usernameField.getText();
        try{
            networkHandler.onVCEvent(new LoginEvent(myNick, numPlayer));
        }catch(Exception ex){
            System.out.println("Error in login");
        }
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
        this.myNick = onlyUsernameField.getText();
        try{
            networkHandler.onVCEvent(new LoginEvent(myNick));
        }catch(Exception ex){
            System.out.println("Error in login");
        }
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
