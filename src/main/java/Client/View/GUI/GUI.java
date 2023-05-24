package Client.View.GUI;

import Client.ConnectionType;
import Client.NetworkHandler;
import Client.NetworkRMIHandler;
import Client.NetworkSocketHandler;
import Client.View.View;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.*;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.PersonalGoalCard;
import Server.Model.Chat.Message;
import Server.Model.GameItems.LivingRoomTileSpot;
import Server.Model.GameItems.PointsTile;
import Server.Model.GameItems.TileSpot;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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
    private Button backSocket;
    @FXML
    private Button backRMI;
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

    //chat elements
    @FXML
    private TextArea chatTextArea;
    @FXML
    private ChoiceBox<String> chatChoiceBox;
    @FXML
    private Button sendButton;

    private ArrayList<String> chat = new ArrayList<>();

    //livingroom elements
    @FXML
    private Button tilea3;
    @FXML
    private Button tilea4;
    @FXML
    private Button tileb3;
    @FXML
    private Button tileb4;
    @FXML
    private Button tileb5;
    @FXML
    private Button tilec2;
    @FXML
    private Button tilec3;
    @FXML
    private Button tilec4;
    @FXML
    private Button tilec5;
    @FXML
    private Button tilec6;
    @FXML
    private Button tiled1;
    @FXML
    private Button tiled2;
    @FXML
    private Button tiled3;
    @FXML
    private Button tiled4;
    @FXML
    private Button tiled5;
    @FXML
    private Button tiled6;
    @FXML
    private Button tiled7;
    @FXML
    private Button tiled8;
    @FXML
    private Button tilee0;
    @FXML
    private Button tilee1;
    @FXML
    private Button tilee2;
    @FXML
    private Button tilee3;
    @FXML
    private Button tilee4;
    @FXML
    private Button tilee5;
    @FXML
    private Button tilee6;
    @FXML
    private Button tilee7;
    @FXML
    private Button tilee8;
    @FXML
    private Button tilef0;
    @FXML
    private Button tilef1;
    @FXML
    private Button tilef2;
    @FXML
    private Button tilef3;
    @FXML
    private Button tilef4;
    @FXML
    private Button tilef5;
    @FXML
    private Button tilef6;
    @FXML
    private Button tilef7;
    @FXML
    private Button tileg2;
    @FXML
    private Button tileg3;
    @FXML
    private Button tileg4;
    @FXML
    private Button tileg5;
    @FXML
    private Button tileg6;
    @FXML
    private Button tileh3;
    @FXML
    private Button tileh4;
    @FXML
    private Button tileh5;
    @FXML
    private Button tilei4;
    @FXML
    private Button tilei5;
    @FXML
    private GridPane livingroomgridbuttons;

    //bookshelf elements
    @FXML
    private Button bookshelfcol0;
    @FXML
    private Button bookshelfcol1;
    @FXML
    private Button bookshelfcol2;
    @FXML
    private Button bookshelfcol3;
    @FXML
    private Button bookshelfcol4;
    @FXML
    private HBox mybookshelf;

    //checkout
    @FXML
    private Button checkoutbutton;


    //LastView
    @FXML
    private Label primo;
    @FXML
    private Label secondo;
    @FXML
    private Label terzo;
    @FXML
    private Label quarto;
    @FXML
    private Label nomePrimo;
    @FXML
    private Label nomeSecondo;
    @FXML
    private Label nomeTerzo;
    @FXML
    private Label nomeQuarto;
    @FXML
    private Label punteggioPrimo;
    @FXML
    private Label punteggioSecondo;
    @FXML
    private Label punteggioTerzo;
    @FXML
    private Label punteggioQuarto;



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


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Re_Connection_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) root.lookup("#wallpaper");
        ImageView wallpaper =(ImageView) root.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
        primaryStage.show();

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
            case "PickingTilesGameView" -> Platform.runLater(() -> {
                onPickingTilesGameView(event);
            });
            case "InsertingTilesGameView" -> Platform.runLater(() -> {
                InsertingTilesGameView(event);
            });


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

    private void onGameViewEvent(SelectViewEvent event){
        if(matchStarted) {
            livingroomgridbuttons.setDisable(true);
            mybookshelf.setDisable(true);
            checkoutbutton.setDisable(true);
            checkoutbutton.setVisible(false);
        }

    }

    private void onPickingTilesGameView(SelectViewEvent event){
        livingroomgridbuttons.setDisable(false);
        checkoutbutton.setDisable(false);
        checkoutbutton.setVisible(true);
        mybookshelf.setDisable(true);

        String[] message = event.getMessage().split(" ");
        if(message[0].equals("Tiles") && message[1].equals("Selected:")){
            clearGrid(livingroomgridbuttons);
            for(int i=0;i< message.length-2;i++) {

                String[] coordinates = message[2+i].split(",");
                char row = coordinates[0].charAt(0);
                int column = Integer.parseInt(coordinates[1]);
                int[] coordinatesInt = new int[2];
                if (row >= 'a' && row <= 'i' && column >= 0 && column <= 8) {
                    coordinatesInt[0] = row - 'a';
                    coordinatesInt[1] = column;
                }
                Button selected = getButtonAt(livingroomgridbuttons, coordinatesInt[0], coordinatesInt[1]);
                selected.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 24px;");

                selected.setOpacity(0.5);

                selected.setText(String.valueOf(i));

            }


        }
    }

    private void clearGrid(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setOpacity(0);
                ((Button) node).setText("");
                ((Button) node).setStyle("");
            }
        }
    }

    private Button getButtonAt(GridPane gridPane, int i, int j) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {

                int rowIndex = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
                int columnIndex = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);

                if (rowIndex == i && columnIndex == j) {
                    return (Button) node;
                }
            }
        }
        return null;
    }

    private void InsertingTilesGameView(SelectViewEvent event) {
        clearGrid(livingroomgridbuttons);
        livingroomgridbuttons.setDisable(true);
        checkoutbutton.setDisable(true);
        checkoutbutton.setVisible(false);
        mybookshelf.setDisable(false);


    }



    @Override
    public void onMVEvent(MVEvent event) {
        String methodName = event.getMethodName();


        switch(methodName) {
            case "onModifiedChatEvent" -> {

                Platform.runLater(() -> {
                    onModifiedChatEvent((Message)event.getValue());
                });
            }
            case "onModifiedBookshelfEvent" -> {
                Platform.runLater(() -> {
                    onModifiedBookshelfEvent(event.getMatch());
                });
            }
            case "onModifiedLivingRoomEvent" -> {
                Platform.runLater(() -> {
                    onModifiedLivingRoomEvent(event.getMatch());
                });
            }
            case "onModifiedMatchEndedEvent" -> {
                try {
                    onModifiedMatchEndedEvent(event.getMatch());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case "onModifiedPointsEvent" -> {
                Platform.runLater(() -> {
                    onModifiedPointsEvent(event.getMatch());
                });
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

        //TODO
    }

    private void onMatchStartedEvent(LightMatch match) {
        this.matchStarted = true;

        loader.setLocation(getClass().getResource("/Gameview.fxml"));
        loader.setController(this);

        try{
            gameRoot = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }

        Scene scene = new Scene(gameRoot);
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());



        numberPlayers = match.getPlayers().size();

        chatChoiceBox.getItems().add("Everyone");
        chatChoiceBox.setValue("Everyone");
        for(int i = 0; i < numberPlayers; i++){
            players.put(i,match.getPlayers().get(i));
            if (match.getPlayers().get(i).getPlayerNickName().equals(myNick)){
                me = match.getPlayers().get(i);
                if(i!=0) {
                    Player tmp = players.get(0);
                    players.put(0, me);
                    players.put(i, tmp);
                }
            }else{
                chatChoiceBox.getItems().add(match.getPlayers().get(i).getPlayerNickName());
            }


        }

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


        onModifiedBookshelfEvent(match);
        onModifiedLivingRoomEvent(match);
        onModifiedPointsEvent(match);


        bookshelfcol0.setStyle("-fx-background-color: transparent;");
        bookshelfcol0.setOnMouseEntered(event -> bookshelfcol0.setStyle("-fx-background-color: cyan; -fx-opacity: 0.5;"));
        bookshelfcol0.setOnMouseExited(event -> bookshelfcol0.setStyle("-fx-background-color: transparent;"));

        bookshelfcol1.setStyle("-fx-background-color: transparent;");
        bookshelfcol1.setOnMouseEntered(event -> bookshelfcol1.setStyle("-fx-background-color: cyan; -fx-opacity: 0.5;"));
        bookshelfcol1.setOnMouseExited(event -> bookshelfcol1.setStyle("-fx-background-color: transparent;"));

        bookshelfcol2.setStyle("-fx-background-color: transparent;");
        bookshelfcol2.setOnMouseEntered(event -> bookshelfcol2.setStyle("-fx-background-color: cyan; -fx-opacity: 0.5;"));
        bookshelfcol2.setOnMouseExited(event -> bookshelfcol2.setStyle("-fx-background-color: transparent;"));

        bookshelfcol3.setStyle("-fx-background-color: transparent;");
        bookshelfcol3.setOnMouseEntered(event -> bookshelfcol3.setStyle("-fx-background-color: cyan; -fx-opacity: 0.5;"));
        bookshelfcol3.setOnMouseExited(event -> bookshelfcol3.setStyle("-fx-background-color: transparent;"));

        bookshelfcol4.setStyle("-fx-background-color: transparent;");
        bookshelfcol4.setOnMouseEntered(event -> bookshelfcol4.setStyle("-fx-background-color: cyan; -fx-opacity: 0.5;"));
        bookshelfcol4.setOnMouseExited(event -> bookshelfcol4.setStyle("-fx-background-color: transparent;"));


        primaryStage.setScene(scene);
        primaryStage.setTitle("MyShelfie");
        primaryStage.show();


    }



    private void updateBookshelf(Player p , int index){
        TileSpot matrix[][]=p.getBookshelf().getTileMatrix();
        int random;

        GridPane grid = (GridPane)gameRoot.lookup("#player"+String.valueOf(index)+"bookshelf");
        for (int i=0;i<6;i++ ){
            for(int j=0;j<5;j++){
                if(matrix[i][j].getTileType()!=null){
                    switch(matrix[i][j].getTileType()) {
                        case CATS -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            random = (int) (Math.random() * 3 + 1);
                            if (random == 1) {
                                tmp.setImage(catImage1);
                            } else if (random == 2) {
                                tmp.setImage(catImage2);
                            } else {
                                tmp.setImage(catImage3);
                            }
                        }
                        case PLANTS -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            random = (int) (Math.random() * 3 + 1);
                            if (random == 1) {
                                tmp.setImage(plantsImage1);
                            } else if (random == 2) {
                                tmp.setImage(plantsImage2);
                            } else {
                                tmp.setImage(plantsImage3);
                            }
                        }
                        case GAMES -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            random = (int) (Math.random() * 3 + 1);
                            if (random == 1) {
                                tmp.setImage(gamesImage1);
                            } else if (random == 2) {
                                tmp.setImage(gamesImage2);
                            } else {
                                tmp.setImage(gamesImage3);
                            }
                        }
                        case BOOKS -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            random = (int) (Math.random() * 3 + 1);
                            if (random == 1) {
                                tmp.setImage(bookImage1);
                            } else if (random == 2) {
                                tmp.setImage(bookImage2);
                            } else {
                                tmp.setImage(bookImage3);
                            }
                        }
                        case FRAMES -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            random = (int) (Math.random() * 3 + 1);
                            if (random == 1) {
                                tmp.setImage(frameImage1);
                            } else if (random == 2) {
                                tmp.setImage(frameImage2);
                            } else {
                                tmp.setImage(frameImage3);
                            }
                        }
                        case TROPHIES -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            random = (int) (Math.random() * 3 + 1);
                            if (random == 1) {
                                tmp.setImage(trophyImage1);
                            } else if (random == 2) {
                                tmp.setImage(trophyImage2);
                            } else {
                                tmp.setImage(trophyImage3);
                            }
                        }
                    }
                }
            }
        }


    }

    private void onModifiedLivingRoomEvent(LightMatch match){

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
                }else{
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp!=null)
                        tmp.setImage(null);
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

    private void onModifiedPointsEvent(LightMatch match){
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

        for(Player p : match.getPlayers()){
            for(int i =0; i<numberPlayers;i++){
                if(players.get(i).getPlayerID()==p.getPlayerID()){
                    ImageView point1 = (ImageView)gameRoot.lookup("#player"+String.valueOf(i)+"point1");
                    ImageView point2 = (ImageView)gameRoot.lookup("#player"+String.valueOf(i)+"point2");

                    if(p.getPointsTiles()!=null){
                        for(PointsTile point : p.getPointsTiles()){
                            if(point== PointsTile.EIGHT_1) {
                                point1.setImage(points8Image);
                            }else if(point== PointsTile.SIX_1){
                                point1.setImage(points6Image);
                            }else if(point== PointsTile.FOUR_1){
                                point1.setImage(points4Image);

                            }else if(point== PointsTile.TWO_1){
                                point1.setImage(points2Image);
                            }else if(point== PointsTile.EIGHT_2){
                                point2.setImage(points8Image);
                            }else if(point== PointsTile.SIX_2){
                                point2.setImage(points6Image);
                            }else if(point== PointsTile.FOUR_2){
                                point2.setImage(points4Image);
                            }else if(point== PointsTile.TWO_2) {
                                point2.setImage(points2Image);
                            }

                        }
                    }
                }
                if(match.getFirstToFinish()!=null && players.get(i).getPlayerID()==match.getFirstToFinish().getPlayerID()){
                    ImageView firstToFinish = (ImageView)gameRoot.lookup("#player"+String.valueOf(i)+"finish");
                    firstToFinish.setImage(points1Image);
                }
            }
        }

    }

    private void onModifiedMatchEndedEvent(LightMatch match) throws IOException {
        Player tmpPlayer=match.getWinner();
        String Punteggio;
        Integer max=0;
        this.MatchEnded= true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MatchEndedView.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();

        nomePrimo.setText(tmpPlayer.getPlayerNickName());
        Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
        punteggioPrimo.setText(Punteggio);
        tmpPlayer= tmpPlayer.getNextPlayer();
        if(numberPlayers==2){
            terzo.setVisible(false);
            nomeTerzo.setVisible(false);
            punteggioTerzo.setVisible(false);
            quarto.setVisible(false);
            nomeQuarto.setVisible(false);
            punteggioQuarto.setVisible(false);
            nomeSecondo.setText(tmpPlayer.getPlayerNickName());
            Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
            punteggioSecondo.setText(Punteggio);
        }else if(numberPlayers==3){
            quarto.setVisible(false);
            nomeQuarto.setVisible(false);
            punteggioQuarto.setVisible(false);
            nomePrimo.setText(tmpPlayer.getPlayerNickName());
            Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
            punteggioPrimo.setText(Punteggio);
            tmpPlayer= tmpPlayer.getNextPlayer();
            if(match.getScores().get(tmpPlayer)>match.getScores().get(tmpPlayer.getNextPlayer())){
                nomeSecondo.setText(tmpPlayer.getPlayerNickName());
                Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
                punteggioSecondo.setText(Punteggio);
                tmpPlayer= tmpPlayer.getNextPlayer();
                nomeTerzo.setText(tmpPlayer.getPlayerNickName());
                Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
                punteggioTerzo.setText(Punteggio);
            }else if(match.getScores().get(tmpPlayer)<match.getScores().get(tmpPlayer.getNextPlayer())){
                nomeTerzo.setText(tmpPlayer.getPlayerNickName());
                Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
                punteggioTerzo.setText(Punteggio);
                tmpPlayer= tmpPlayer.getNextPlayer();
                nomeSecondo.setText(tmpPlayer.getPlayerNickName());
                Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
                punteggioSecondo.setText(Punteggio);
            }else if(match.getScores().get(tmpPlayer)==match.getScores().get(tmpPlayer.getNextPlayer())) {
                if (match.getFirstPlayer().getPlayerNickName().equals(tmpPlayer.getNextPlayer().getPlayerNickName())) {
                    nomeSecondo.setText(tmpPlayer.getPlayerNickName());
                    Punteggio = String.valueOf(match.getScores().get(tmpPlayer));
                    punteggioSecondo.setText(Punteggio);
                    tmpPlayer = tmpPlayer.getNextPlayer();
                    nomeTerzo.setText(tmpPlayer.getPlayerNickName());
                    Punteggio = String.valueOf(match.getScores().get(tmpPlayer));
                    punteggioTerzo.setText(Punteggio);
                }else if((match.getFirstPlayer().equals(tmpPlayer.getPlayerNickName()))||(match.getFirstPlayer().equals(tmpPlayer.getNextPlayer().getNextPlayer()))) {
                    nomeTerzo.setText(tmpPlayer.getPlayerNickName());
                    Punteggio = String.valueOf(match.getScores().get(tmpPlayer));
                    punteggioTerzo.setText(Punteggio);
                    tmpPlayer = tmpPlayer.getNextPlayer();
                    nomeSecondo.setText(tmpPlayer.getPlayerNickName());
                    Punteggio = String.valueOf(match.getScores().get(tmpPlayer));
                    punteggioSecondo.setText(Punteggio);
                }
            }
        }else if(numberPlayers==4){
            tmpPlayer= match.getFirstPlayer();
            for(int i=0; i<4; i++){
                if(tmpPlayer.equals(match.getWinner())){
                    tmpPlayer=tmpPlayer.getNextPlayer();
                }else{
                    if(match.getScores().get(tmpPlayer)>=max){
                        max=match.getScores().get(tmpPlayer);
                        nomeSecondo.setText(tmpPlayer.getPlayerNickName());
                    }
                    tmpPlayer=tmpPlayer.getNextPlayer();
                }
            }
            Punteggio=String.valueOf(max);
            punteggioSecondo.setText(Punteggio);
            max=0;
            tmpPlayer= match.getFirstPlayer();
            for(int i=0; i<4;i++){
                if(tmpPlayer.equals(match.getWinner())||tmpPlayer.equals(nomeSecondo.getText())) {
                    tmpPlayer = tmpPlayer.getNextPlayer();
                }else{
                    if(match.getScores().get(tmpPlayer)>=max){
                        max=match.getScores().get(tmpPlayer);
                        nomeTerzo.setText(tmpPlayer.getPlayerNickName());
                    }
                    tmpPlayer=tmpPlayer.getNextPlayer();
                }
            }
            Punteggio=String.valueOf(max);
            punteggioTerzo.setText(Punteggio);
            max=0;
            tmpPlayer=match.getFirstPlayer();
            while(tmpPlayer.equals(match.getWinner())||tmpPlayer.equals(nomeSecondo.getText())||tmpPlayer.equals(nomeTerzo.getText())){
                tmpPlayer=tmpPlayer.getNextPlayer();
            }
            nomeQuarto.setText(tmpPlayer.getPlayerNickName());
            Punteggio=String.valueOf(match.getScores().get(tmpPlayer));
            punteggioQuarto.setText(Punteggio);

        }
        Scene newScene = new Scene(newRoot);
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    private void onModifiedBookshelfEvent(LightMatch match) {
        for(Player p : match.getPlayers()){
            for(int i=0; i<numberPlayers;i++){
                if(players.get(i).getPlayerNickName().equals(p.getPlayerNickName())){
                    updateBookshelf(p,i);
                    break;
                }
            }
        }
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

    //bookshelfselection
    @FXML
    private void onSelectColoumn(ActionEvent event){
        Button col = (Button) event.getSource();
        String colId = col.getId();

        int index = Integer.parseInt(String.valueOf(colId.charAt(12)));
        try {
            networkHandler.onVCEvent(new SelectColumn(index));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    //checkout
    @FXML
    private void onCheckout(ActionEvent event){
        try {
            networkHandler.onVCEvent(new CheckOutTiles());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //livingroomselection
    @FXML
    private void onTileSelected(ActionEvent event) throws IOException {

        Button tile = (Button) event.getSource();
        String tileId = tile.getId();
        GridPane grid = (GridPane)gameRoot.lookup("#livingroomtiles");

        char row= tileId.charAt(4);
        int column = Integer.parseInt(String.valueOf(tileId.charAt(5)));
        int[] coordinatesInt = new int[2];
        if(row>='a' && row <='i' && column>=0 && column<=8) {
            coordinatesInt[0] = row - 'a';
            coordinatesInt[1] = column;
            try {
                networkHandler.onVCEvent(new ClickOnTile(coordinatesInt));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        //ImageView tmp= getImageViewAt(grid,coordinatesInt[0],coordinatesInt[1]);
        //tmp.setOpacity(0.5);

    }


    //CHAT
    public void onModifiedChatEvent(Message message){

        String s = MessageToString(message);
        VBox guichat = (VBox)gameRoot.lookup("#chatBox");
        HBox messageBox;


        chat.add(s);
        if(message.getReceiver() != null) {
            Text messageText = new Text( "[" + message.getTimeSent() + "]" + " " + message.getSender().getPlayerNickName() + " " + "to @" + message.getReceiver().getPlayerNickName()+ ":" + " " + message.getContent()+"\n");
            TextFlow messageFlow = new TextFlow(messageText);
            if(message.getSender().getPlayerNickName().equals(me.getPlayerNickName()))
                messageFlow.getStyleClass().add( "message-box-from-them");
            else
                messageFlow.getStyleClass().add( "message-box-from-me");
            messageFlow.setMaxWidth(200);

            messageBox = new HBox(messageFlow);
            messageBox.setAlignment(message.getSender().getPlayerNickName().equals(me.getPlayerNickName())? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        }else{
            Text messageText = new Text("[" + message.getTimeSent() + "]" + " " + message.getSender().getPlayerNickName() + " " + "to @All:" + " " + message.getContent()+"\n");
            TextFlow messageFlow = new TextFlow(messageText);
            if(message.getSender().getPlayerNickName().equals(me.getPlayerNickName()))
                messageFlow.getStyleClass().add( "message-box-from-them");
            else
                messageFlow.getStyleClass().add( "message-box-from-me");
            messageFlow.setMaxWidth(200);

            messageBox = new HBox(messageFlow);
            messageBox.setAlignment(message.getSender().getPlayerNickName().equals(me.getPlayerNickName())? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        }

        guichat.getChildren().add(messageBox);

        // Scroll to the bottom
        guichat.layout();
        //scrollChat.setVvalue(1.0);

    }

    private String MessageToString(Message message){
        String receiver;
        if(message.getReceiver() == null) {
            receiver = "All";
        }else{
            receiver = message.getReceiver().getPlayerNickName();
        }

        String s = "[" + message.getTimeSent() + "]" + " " + message.getSender().getPlayerNickName() + " @" + receiver + " " + message.getContent();
        return s;

    }
    @FXML
    private void onSendMessage(ActionEvent event) throws IOException {
        String receiver = (String) chatChoiceBox.getValue();
        String message = chatTextArea.getText();
        if(!message.equals("")){
            chatTextArea.clear();
            chatTextArea.setPromptText("Type your message here");
            try {

                Message messageToSend=null;
                if(receiver.equals("Everyone")) {
                    messageToSend=new Message(this.me,message, LocalTime.now());
                }else{

                    for(Integer i: this.players.keySet()){
                        if((this.players.get(i).getPlayerNickName()).equals(receiver)){
                            messageToSend=new Message(this.me,message,LocalTime.now(), this.players.get(i));
                        }
                    }
                }
                networkHandler.onVCEvent(new SendMessage(messageToSend));

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }



    //Buttons Methods

    @FXML
    private void onClickYesReconnect(ActionEvent event) throws IOException {
        this.isReconnecting = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Button rmiButton = (Button) newRoot.lookup("#rmibutton");
        Button socketButton = (Button) newRoot.lookup("#socketbutton");

        rmiButton.setMinWidth(100);
        rmiButton.setMinHeight(50);

        socketButton.setMinWidth(100);
        socketButton.setMinHeight(50);

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

        ImageView wallpaper = (ImageView) newRoot.lookup("#wallpaper");
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

        Button rmiButton = (Button) newRoot.lookup("#rmibutton");
        Button socketButton = (Button) newRoot.lookup("#socketbutton");

        rmiButton.setMinWidth(100);
        rmiButton.setMinHeight(50);

        socketButton.setMinWidth(100);
        socketButton.setMinHeight(50);

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

        ImageView wallpaper = (ImageView) newRoot.lookup("#wallpaper");

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
        try {
            int port = Integer.parseInt(portServer.getText());
            this.networkHandler = new NetworkSocketHandler(host, port, this);
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid port");
            alert.setContentText("Please insert a valid port");
            alert.showAndWait();
            return;
        }

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
        try {
            int port = Integer.parseInt(portServerRMI.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid port");
            alert.setContentText("Please insert a valid port");
            alert.showAndWait();
            return;
        }

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
        int numPlayer = numberPlayersMenu.getSelectionModel().getSelectedIndex() + 2 ==1 ? 2 : numberPlayersMenu.getSelectionModel().getSelectedIndex()+2;

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

    @FXML
    private void onBackButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Button rmiButton = (Button) newRoot.lookup("#rmibutton");
        Button socketButton = (Button) newRoot.lookup("#socketbutton");

        rmiButton.setMinWidth(100);
        rmiButton.setMinHeight(50);

        socketButton.setMinWidth(100);
        socketButton.setMinHeight(50);

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
        Scene newScene = new Scene(newRoot);
        primaryStage.setScene(newScene);
        primaryStage.show();
    }
}
