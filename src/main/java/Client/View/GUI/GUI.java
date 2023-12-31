package Client.View.GUI;

import Client.ConnectionType;
import Client.NetworkHandler;
import Client.NetworkRMIHandler;
import Client.NetworkSocketHandler;
import Client.View.CLI.ANSIParameters;
import Client.View.CLI.CLI;
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
import com.google.gson.Gson;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.ScrollEvent;

/**
 * This class represents the GUI view of the game
 * @author Valentino Guerrini, Paolo Gennaro, Marta Giliberto
 */
public class GUI extends Application implements View {

    private final String CONNECTION_INFO_DIRECTORY_NAME = "ClientFiles";
    //resources
    ImageView titleImageView;
    private String myNick;

    private String currentPlayerNickname;
    ImageView wallpaperImageView;

    ConnectionType connectionType = null;
    private NetworkHandler networkHandler;
    private Stage primaryStage;

    private Scene previousScene;

    private SelectViewEvent currentView;
    private boolean matchStarted = false;

    private boolean MatchEnded = false;
    private ConnectionInfo connectionInfo;
    private boolean isReconnecting = false;
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


    private static final double MIN_ZOOM = 0.1;
    private static final double MAX_ZOOM = 10.0;

    private DoubleProperty zoomLevel = new SimpleDoubleProperty(1.0);





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
    private TextField addressServerRMI1;
    @FXML
    private Button quitButton;
    @FXML
    private Button quitButtonBeforeRunning;
    @FXML
    private Button playButton;
    @FXML
    private Button showBookshelf;
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

    @FXML
    private Button rulesButton;


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
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label servermessage;



    private Parent Connectionroot;
    private Parent gameRoot;

    //connection
    @FXML
    private Button submitname;
    @FXML
    private Button yesalreadyconnected;
    @FXML
    private Button noalreadyconnected;
    @FXML
    private Button okComboButton;
    //attributes
    private String directoryPath = null;
    private String jarFilePath = null;
    private String jarFolder = null;
    private File newDirectory = null;
    private File directory = null;
    private ConnectionInfo ci = null;
    private List<String> listDirectoryPath = null;
    private File connectionFile = null;
    private BufferedWriter bufferedWriter;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    String name = null;


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

    /**
     *This method sets up the user interface for connection requests.
     * It starts the application by initializing the primary stage and loading the necessary resources.
     @param primaryStage The primary stage of the JavaFX application.
     @throws IOException If an error occurs while loading the FXML file.
     @author Valentino Guerrini
     */
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Connection_Requests.fxml"));
        fxmlLoader.setController(this);
        Connectionroot = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) Connectionroot.lookup("#wallpaper");
        ImageView wallpaper =(ImageView) Connectionroot.lookup("#parquet") ;

        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

        jarFilePath = CLI.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        // Getting its parent's file path
        jarFolder = new File(jarFilePath).getParent();

        // Crea un oggetto File per la nuova directory nella cartella del file .jar
        newDirectory = new File(jarFolder, CONNECTION_INFO_DIRECTORY_NAME);

        // Crea la directory
        if(!newDirectory.exists()) {
            newDirectory.mkdir();
        }

        Text printer = (Text) Connectionroot.lookup("#printer");
        //Get person's name in order to create a directory
        printer.setText("Insert your name:");

        TextField nameField = (TextField) Connectionroot.lookup("#namefield");
        Button submitButton = (Button) Connectionroot.lookup("#submitbutton");

        nameField.setVisible(true);
        submitButton.setVisible(true);
        submitButton.setDisable(false);

        HBox secondstage = (HBox) Connectionroot.lookup("#secondstage");
        secondstage.setVisible(false);
        HBox thirdstage = (HBox) Connectionroot.lookup("#thirdstage");
        thirdstage.setVisible(false);

        Scene newScene = new Scene(Connectionroot);
        newScene.getStylesheets().add(getClass().getResource("/initial.css").toExternalForm());
        primaryStage.setScene(newScene);
        primaryStage.show();

    }

    /**
     * This method is called when the user clicks on the submit button.
     * This method retrieves the name entered in the name field, performs necessary actions to determine the path of
     * the directory in which to store the connection info file. If it already exists it asks the user if he wants to
     * reconnect to the game or not through the proper scene. If it doesn't exist it creates the directory of the file,
     * and then it continues on with the connection process.
     * @param event The event of clicking on the submit button.
     * @author Valentino Guerrini
     */
    @FXML
    private void onClickSubmitname(ActionEvent event){
        TextField nameField = (TextField) Connectionroot.lookup("#namefield");
        name = nameField.getText();

        Button submitButton = (Button) Connectionroot.lookup("#submitbutton");

        nameField.setVisible(false);
        submitButton.setVisible(false);

        directoryPath = jarFolder+"/"+CONNECTION_INFO_DIRECTORY_NAME+"/Client_"+name;
        directory = new File(directoryPath);
        int i = 1;
        if(directory.exists() && directory.isDirectory()){
            try {
                ci = new Gson().fromJson(
                        new BufferedReader(
                                new FileReader(directoryPath+"/ConnectionInfo.txt")),
                        ConnectionInfo.class
                );
                if(     ci!=null &&
                        ci.getNickname()!=null
                ){
                    Text printer = (Text) Connectionroot.lookup("#printer");
                    printer.setText("It seems you were already connected, is that right? ");
                    Button yesButton = (Button) Connectionroot.lookup("#yesconnectbutton");
                    Button noButton = (Button) Connectionroot.lookup("#noconnectbutton");
                    HBox firststage = (HBox) Connectionroot.lookup("#firststage");
                    firststage.setVisible(false);
                    HBox secondstage = (HBox) Connectionroot.lookup("#secondstage");
                    secondstage.setVisible(true);
                    yesButton.setVisible(true);
                    noButton.setVisible(true);

                }else{
                    while(!isReconnecting && directory.exists() && directory.isDirectory()){
                        directoryPath = jarFolder+"/"+CONNECTION_INFO_DIRECTORY_NAME+"/Client_"+name+"_"+i;
                        directory = new File(directoryPath);
                        i++;
                    }
                    connect();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }else{
            while(!isReconnecting && directory.exists() && directory.isDirectory()){
                directoryPath = jarFolder+"/"+CONNECTION_INFO_DIRECTORY_NAME+"/Client_"+name+"_"+i;
                directory = new File(directoryPath);
                i++;
            }
            connect();
        }
    }

    /**
     *This method handles the event when the "Yes" button is clicked to indicate that the client was previously connected.
     *This method prepares the UI for selecting the correct directory if multiple directories exist for the client,
     *or allows selecting "none" if the correct directory is not known.
     @param event The action event triggered by clicking the "Yes" button.
     @autor Valentino Guerrini
     */
    @FXML
    private void onYesConnected(ActionEvent event){
        int j=1;
        listDirectoryPath = new ArrayList<>();
        listDirectoryPath.add(directoryPath);
        File tempDirectory = new File(directoryPath+"_"+j);
        while(tempDirectory.exists() && tempDirectory.isDirectory()){
            listDirectoryPath.add(directoryPath+"_"+j);
            //directory = new File(listDirectoryPath);
            j++;
            tempDirectory = new File(directoryPath+"_"+j);
        }
        ComboBox<String> comboBox = (ComboBox<String>) Connectionroot.lookup("#selectlastlogin");
        String message ="If you remember which of the following directories is the right one," +
                " please insert the number of it, otherwise select none";
        for(int k=0;k<listDirectoryPath.size();k++){
            comboBox.getItems().add(k+" - "+listDirectoryPath.get(k));
        }
        comboBox.getItems().add("none");
        Text printer = (Text) Connectionroot.lookup("#printer");
        printer.setText(message);
        Button yesButton = (Button) Connectionroot.lookup("#yesconnectbutton");
        Button noButton = (Button) Connectionroot.lookup("#noconnectbutton");
        yesButton.setVisible(false);
        noButton.setVisible(false);
        HBox secondstage = (HBox) Connectionroot.lookup("#secondstage");
        secondstage.setVisible(false);
        HBox thirdstage = (HBox) Connectionroot.lookup("#thirdstage");
        thirdstage.setVisible(true);
        Button okCombo = (Button) Connectionroot.lookup("#okcombobutton");
        okCombo.setVisible(true);
        comboBox.setVisible(true);

    }

    /**
     *This method handles the event when the "OK" button is clicked after selecting a directory from the combo box.
     *This method retrieves the selected directory path and performs actions accordingly.
     *@param event The action event triggered by clicking the "OK" button.
     */
    @FXML
    private void onClickOkCombo(ActionEvent event){
        ComboBox<String> comboBox = (ComboBox<String>) Connectionroot.lookup("#selectlastlogin");
        String selected = comboBox.getSelectionModel().getSelectedItem();
        if(selected.equals("none")){

            Button okCombo = (Button) Connectionroot.lookup("#okcombobutton");
            okCombo.setVisible(false);
            comboBox.setVisible(false);
            isReconnecting = false;
        }else{

            Button okCombo = (Button) Connectionroot.lookup("#okcombobutton");
            okCombo.setVisible(false);
            comboBox.setVisible(false);
            directoryPath = listDirectoryPath.get(Integer.parseInt(String.valueOf(selected.charAt(0))));
            isReconnecting = true;
        }
        int i= 1;
        while(!isReconnecting && directory.exists() && directory.isDirectory()){
            directoryPath = jarFolder+"/"+CONNECTION_INFO_DIRECTORY_NAME+"/Client_"+name+"_"+i;
            directory = new File(directoryPath);
            i++;
        }
        connect();

    }


    /**
     *Handles the event when the "No" button is clicked to indicate that the client was not previously connected.
     *This method prepares for connecting to the server by generating a unique directory name if necessary.
     *@param event The action event triggered by clicking the "No" button.
     *@autor Valentino Guerrini
     */
    @FXML
    private void onNoConnected(ActionEvent event){
        Button yesButton = (Button) Connectionroot.lookup("#yesconnectbutton");
        Button noButton = (Button) Connectionroot.lookup("#noconnectbutton");
        yesButton.setVisible(false);
        noButton.setVisible(false);
        int i=1;
        while(!isReconnecting && directory.exists() && directory.isDirectory()){
            directoryPath = jarFolder+"/"+CONNECTION_INFO_DIRECTORY_NAME+"/Client_"+name+"_"+i;
            directory = new File(directoryPath);
            i++;
        }
        connect();
    }

     /**
     * This method handles the steps involved in establishing a connection, such as displaying messages,
      * creating directories, and invoking the appropriate connection process based on whether it's a new
      * connection or a reconnection.
      * @author Valentino Guerrini
     */
    private void connect(){
        //ConnectionType connectionType = null;
        Text printer = (Text) Connectionroot.lookup("#printer");
        PauseTransition pause1 = new PauseTransition(Duration.seconds(2));
        PauseTransition pause2 = new PauseTransition(Duration.seconds(7));
        PauseTransition pause3 = new PauseTransition(Duration.seconds(5));
        pause1.setOnFinished(event -> {
            printer.setText("Alright, your connection info will be stored locally at: "
                    +directoryPath
                    +"\n"
                    +"Please, try to remember it, you might need it in the future.");
            pause2.play();
        });
        pause2.setOnFinished(event -> {
            try {
                connectionFile = new File(directoryPath+"/ConnectionInfo.txt");
                if(!isReconnecting){
                    if (directory.mkdir()) {
                            printer.setText("The directory was created successfully.");

                        if(connectionFile.createNewFile()==false){
                            printer.setText("File already exists, but it should not.");
                        }
                    } else {
                        printer.setText("Failed to create the directory.");
                    }
                }
                pause3.play();
            } catch (IOException e) {
                printer.setText("An error occurred."+e.getMessage());
            }
        });

        pause3.setOnFinished(event -> {
            String localIP = null;
            if(!isReconnecting){
                try {
                    connectionProcess();
                }catch (Exception e){
                    printer.setText("An error occurred."+e.getMessage());
                }
            }else{
                try {
                    reconnectionProcess();
                }catch (Exception e){
                    printer.setText("An error occurred."+e.getMessage());
                }
            }
        });
        pause1.play();
    }

    /**
     *This method is called when there is a reconnecting to be done.
     *It reads the connection information from the stored file, initializes the network handler based on the
     *connection type, and updates the UI accordingly.
     *@throws IOException If an error occurs while reading the connection information file.
     * @autor Valentino Guerrini
     */
    private void reconnectionProcess() throws IOException{
        String json = null;
        try {
            try {
                fileReader = new FileReader(connectionFile);
                bufferedReader = new BufferedReader(fileReader);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            json  = bufferedReader.readLine();
            connectionInfo = new Gson().fromJson(json, ConnectionInfo.class);
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(connectionInfo == null){
            System.err.println("AAAAAAAAAAA: "+json);
        }else{
            switch(connectionInfo.getConnectionType()){
                case SOCKET:
                    networkHandler = new NetworkSocketHandler(connectionInfo.getIp(), connectionInfo.getPort(), this);
                    break;
                case RMI:
                    try {
                        networkHandler = new NetworkRMIHandler(connectionInfo.getIp(), connectionInfo.getPort(), this);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
            myNick = connectionInfo.getNickname();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FirstView.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        if(matchStarted){
            Text message = (Text) newRoot.lookup("#connectionmessage");
            message.setVisible(true);
        }
        AnchorPane pane = (AnchorPane) newRoot.lookup("#wallpaper");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

        Scene newScene = primaryStage.getScene();
        newScene.setRoot(newRoot);


        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    /**
     *This method is called when there is a connecting to be done.
     *This method initializes the UI for selecting the connection type (RMI or Socket) and updates the scene accordingly.
     *@throws IOException If an error occurs while loading the UI components.
     * @autor Valentino Guerrini
     */
    private void connectionProcess() throws IOException{
        this.isReconnecting = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);

        Parent newRoot = fxmlLoader.load();

        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

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

    /**
     * This method is called when there is a SelectViewEvent. It calls a different method based on the type of the event.
     * @param event The SelectViewEvent the type of SelecyViewEvent
     * @author Valentino Guerrini
     */
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
                onInsertingTilesGameView(event);
            });
            default -> Platform.runLater(() ->{
                currentView = event;
                servermessage.setText(currentView.getMessage());
            });

        }

    }

    /**
     * This method is called when there is a LoginViewEvent.
     * It updates the scene,and, if the user is the first to join, it asks him to select the number of players.
     * @param event The LoginViewEvent to be handled
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    private void onLoginViewEvent(SelectViewEvent event) throws IOException {
        currentView = event;
        LoginView loginView = (LoginView) event;

        if(loginView.isFirstToJoin()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Select_Number_Of_Players.fxml"));
            fxmlLoader.setController(this);
            Parent newRoot = fxmlLoader.load();
            AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
            ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


            wallpaper.fitHeightProperty().bind(pane.heightProperty());
            wallpaper.fitWidthProperty().bind(pane.widthProperty());
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
            AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
            ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


            wallpaper.fitHeightProperty().bind(pane.heightProperty());
            wallpaper.fitWidthProperty().bind(pane.widthProperty());
            Text tmp = (Text)newRoot.lookup("#errormessage");
            if(!loginView.getMessage().equals("Insert your username")){
                tmp.setText(loginView.getMessage());
            }
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);
            primaryStage.show();
        }
    }

    /**
     * This method is called when there is a GameViewEvent. It updates the scene and shows the GameView.
     * @param event The GameViewEvent to be handled
     * @author Valentino Guerrini
     */
    private void onGameViewEvent(SelectViewEvent event){
        currentView = event;
        if(matchStarted) {
            clearGrid(livingroomgridbuttons);
            livingroomgridbuttons.setDisable(true);
            mybookshelf.setDisable(true);
            checkoutbutton.setDisable(true);
            checkoutbutton.setVisible(false);
            servermessage.setText(currentView.getMessage());
        }
    }

    /**
     * This method is called when there is a PickingTilesGameView.
     * It updates the scene and shows the view in which the player's picking tiles.
     * @param event The InsertingTilesGameViewEvent to be handled
     * @throws IOException If an error occurs while loading the UI components.
     */
    private void onPickingTilesGameView(SelectViewEvent event){
        currentView = event;
        livingroomgridbuttons.setDisable(false);
        checkoutbutton.setDisable(false);
        checkoutbutton.setVisible(true);
        mybookshelf.setDisable(true);
        //mybookshelf.setStyle("-fx-background-color: #FFD700;");
        //mybookshelf.setOpacity(0.2);
        servermessage.setText(currentView.getMessage());
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

    /**
     * This method is called when a player selects tiles,
     * in order to set the number on the tiles that the player is picking and to set the opacity.
     * @param gridPane The gridPane in which the tiles are placed
     * @author Valentino Guerrini
     */
    private void clearGrid(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setOpacity(0);
                ((Button) node).setText("");
                ((Button) node).setStyle("");
            }
        }
    }


     /**
     *This method retrieves the Button located at the specified row and column indices in the GridPane.
     *@param gridPane The GridPane containing the Buttons.
     *@param i The row index of the Button.
     *@param j The column index of the Button.
     *@return The Button at the specified row and column indices, or null if not found.
      *@author Valentino Guerrini
     */
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

    /**
     * This method is called when there is a InsertingTilesGameViewEvent.
     * It updates the scene and shows the view in which the player is inserting tiles in his bookshelf.
     * @param event The InsertingTilesGameViewEvent to be handled
     * @author Marta Giliberto
     */
    private void onInsertingTilesGameView(SelectViewEvent event) {
        clearGrid(livingroomgridbuttons);
        livingroomgridbuttons.setDisable(true);
        checkoutbutton.setDisable(true);
        checkoutbutton.setVisible(false);
        mybookshelf.setDisable(false);
        currentView = event;
        servermessage.setText(currentView.getMessage());
    }

    /**
     * This method is called when there is an MVEvent and manage every event with a different method.
     * @param event The MVEvent to be handled
     * @author Marta Giliberto
     */
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
                    Platform.runLater(() -> {
                        try {
                            onModifiedMatchEndedEvent(event.getMatch());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
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

    private void onModifiedTurnEvent(LightMatch match) {}

    /**
     * This method is called when there is a MatchStartedEvent.
     * It shows the GameView and manages the elements of the scene based on the number of players.
     * @param match The match to be handled
     */
    private void onMatchStartedEvent(LightMatch match) {
        if(!matchStarted){
            this.matchStarted = true;

            loader.setLocation(getClass().getResource("/Gameview.fxml"));
            loader.setController(this);

            try{
                gameRoot = loader.load();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        Scene scene;

        try {
            scene = new Scene(gameRoot);
        }catch(IllegalArgumentException e){
            return;
        }

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
            if(players.get(i).getPlayerNickName().equals(match.getFirstPlayer().getPlayerNickName())){
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

    /**
     * This method is needed to delete the files in the directory where are saved data of the player.
     * @author Paolo Gennaro
     */
    private void deleteDirectory(){
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if(files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        directory.delete();
    }

    /**
     *Updates the bookshelf of a player with images based on the tile types present in the bookshelf matrix.
     *@param p The player whose bookshelf needs to be updated.
     *@param index The index of the player's bookshelf (used to locate the corresponding GridPane in the game root).
     */
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
                            if(tmp.getImage()==null) {
                                random = (int) (Math.random() * 3 + 1);
                                if (random == 1) {
                                    tmp.setImage(catImage1);
                                } else if (random == 2) {
                                    tmp.setImage(catImage2);
                                } else {
                                    tmp.setImage(catImage3);
                                }
                            }
                        }
                        case PLANTS -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            if(tmp.getImage()==null) {
                                random = (int) (Math.random() * 3 + 1);
                                if (random == 1) {
                                    tmp.setImage(plantsImage1);
                                } else if (random == 2) {
                                    tmp.setImage(plantsImage2);
                                } else {
                                    tmp.setImage(plantsImage3);
                                }
                            }
                        }
                        case GAMES -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            if(tmp.getImage()==null) {
                                random = (int) (Math.random() * 3 + 1);
                                if (random == 1) {
                                    tmp.setImage(gamesImage1);
                                } else if (random == 2) {
                                    tmp.setImage(gamesImage2);
                                } else {
                                    tmp.setImage(gamesImage3);
                                }
                            }
                        }
                        case BOOKS -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            if(tmp.getImage()==null) {
                                random = (int) (Math.random() * 3 + 1);
                                if (random == 1) {
                                    tmp.setImage(bookImage1);
                                } else if (random == 2) {
                                    tmp.setImage(bookImage2);
                                } else {
                                    tmp.setImage(bookImage3);
                                }
                            }
                        }
                        case FRAMES -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            if(tmp.getImage()==null) {
                                random = (int) (Math.random() * 3 + 1);
                                if (random == 1) {
                                    tmp.setImage(frameImage1);
                                } else if (random == 2) {
                                    tmp.setImage(frameImage2);
                                } else {
                                    tmp.setImage(frameImage3);
                                }
                            }
                        }
                        case TROPHIES -> {
                            ImageView tmp = getImageViewAt(grid, i, j);
                            if(tmp.getImage()==null) {
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
    }

    /**
     *This method is called when there is an edit on the LivingRoom of the match.
     * It updates the GUI View based on the living room of the given match.
     * @param match The LightMatch containing the living room to be modified.
     * @author Valentino Guerrini
     */
    private void onModifiedLivingRoomEvent(LightMatch match){

        LivingRoomTileSpot livingroom[][]=match.getLivingRoom().getTileMatrix();
        GridPane grid = (GridPane)gameRoot.lookup("#livingroomtiles");
        int random;

        for(int i=0;i<9;i++){
            for(int j=0; j<9;j++){
                if(livingroom[i][j].getTileType()== TileType.CATS){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp.getImage()==null) {
                        random = (int) (Math.random() * 3 + 1);
                        if (random == 1) {
                            tmp.setImage(catImage1);
                        } else if (random == 2) {
                            tmp.setImage(catImage2);
                        } else {
                            tmp.setImage(catImage3);
                        }
                    }
                }else if(livingroom[i][j].getTileType()== TileType.TROPHIES){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp.getImage()==null) {
                        random = (int) (Math.random() * 3 + 1);
                        if (random == 1) {
                            tmp.setImage(trophyImage1);
                        } else if (random == 2) {
                            tmp.setImage(trophyImage2);
                        } else {
                            tmp.setImage(trophyImage3);
                        }
                    }
                }else if(livingroom[i][j].getTileType()== TileType.FRAMES){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp.getImage()==null) {
                        random = (int) (Math.random() * 3 + 1);
                        if (random == 1) {
                            tmp.setImage(frameImage1);
                        } else if (random == 2) {
                            tmp.setImage(frameImage2);
                        } else {
                            tmp.setImage(frameImage3);
                        }
                    }
                }else if(livingroom[i][j].getTileType()== TileType.PLANTS){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp.getImage()==null) {
                        random = (int) (Math.random() * 3 + 1);
                        if (random == 1) {
                            tmp.setImage(plantsImage1);
                        } else if (random == 2) {
                            tmp.setImage(plantsImage2);
                        } else {
                            tmp.setImage(plantsImage3);
                        }
                    }
                }else if(livingroom[i][j].getTileType()== TileType.GAMES){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp.getImage()==null) {

                        random = (int) (Math.random() * 3 + 1);
                        if (random == 1) {
                            tmp.setImage(gamesImage1);
                        } else if (random == 2) {
                            tmp.setImage(gamesImage2);
                        } else {
                            tmp.setImage(gamesImage3);
                        }
                    }
                }else if(livingroom[i][j].getTileType()== TileType.BOOKS){
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp.getImage()==null) {
                        random = (int) (Math.random() * 3 + 1);
                        if (random == 1) {
                            tmp.setImage(bookImage1);
                        } else if (random == 2) {
                            tmp.setImage(bookImage2);
                        } else {
                            tmp.setImage(bookImage3);
                        }
                    }
                }else{
                    ImageView tmp= getImageViewAt(grid,i,j);
                    if(tmp!=null)
                        tmp.setImage(null);
                }
            }
        }

    }

    /**
     *This method retrieves the ImageView at the specified position (i, j) in the given GridPane.
     *@param gridPane The GridPane containing the ImageView.
     *@param i The row index of the desired ImageView.
     *@param j The column index of the desired ImageView.
     *@return The ImageView at the specified position, or null if not found.
     */
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

    /**
     *This method updates the GUI view based on the modified points in the LightMatch.
     *@param match The LightMatch containing the updated points information.
     *@author Marta Giliberto
     */
    private void onModifiedPointsEvent(LightMatch match){
        firstCommonGoalCard=match.getCommonGoals()[0];
        secondCommonGoalCard=match.getCommonGoals()[1];
        ImageView firstCommonGoalPoints = (ImageView)gameRoot.lookup("#firstcommongoalpoints");
        ImageView secondCommonGoalPoints = (ImageView)gameRoot.lookup("#secondcommongoalpoints");
        if(firstCommonGoalCard.getPointsTiles().size()!=0){
            if(firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_2){
                firstCommonGoalPoints.setImage(points8Image);

            }else if(firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_2){
                firstCommonGoalPoints.setImage(points6Image);
            }else if(firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_2){
                firstCommonGoalPoints.setImage(points4Image);

            } else if (firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1) == PointsTile.TWO_1 || firstCommonGoalCard.getPointsTiles().get(firstCommonGoalCard.getPointsTiles().size()-1) == PointsTile.TWO_2) {
                firstCommonGoalPoints.setImage(points2Image);
            }
        }else{
            firstCommonGoalPoints.setImage(null);
        }
        if(secondCommonGoalCard.getPointsTiles().size()!=0){
            if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.EIGHT_1) {
                secondCommonGoalPoints.setImage(points8Image);
            }else if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.SIX_1){
                secondCommonGoalPoints.setImage(points6Image);
            }else if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.FOUR_1){
                secondCommonGoalPoints.setImage(points4Image);
            }else if(secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.TWO_2 || secondCommonGoalCard.getPointsTiles().get(secondCommonGoalCard.getPointsTiles().size()-1)== PointsTile.TWO_1) {
                secondCommonGoalPoints.setImage(points2Image);
            }
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

    /**
     * This method is called when the match is ended. The match ended view shows the final ranking of the players.
     * So the method calculates the final ranking based on number players and show it.
     * @param match the match that is ended
     * @throws IOException If an error occurs while loading the UI components.
     * @author Marta Giliberto
     */
    private void onModifiedMatchEndedEvent(LightMatch match) throws IOException {
        Player tmpPlayer = match.getWinner();
        String Punteggio= null;
        String maxPlayer=null;
        Integer max = 0;
        Boolean flag= false;
        this.MatchEnded = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MatchEndedView.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();

        if(currentView.getMessage().equals("Only one player connected")){
            terzo.setVisible(false);
            nomeTerzo.setVisible(false);
            punteggioTerzo.setVisible(false);
            quarto.setVisible(false);
            nomeQuarto.setVisible(false);
            punteggioQuarto.setVisible(false);
            nomeSecondo.setVisible(false);
            punteggioSecondo.setVisible(false);

            nomePrimo.setText(match.getCurrentPlayer().getPlayerNickName());
            punteggioPrimo.setText("every one is disconnected, you won!");
            previousScene = primaryStage.getScene();
            Scene newScene = new Scene(newRoot);
            primaryStage.setScene(newScene);


            AnchorPane pane = (AnchorPane) newRoot.lookup("#anchorPane");
            ImageView imageView =(ImageView) newRoot.lookup("#sfondo") ;


            imageView.fitHeightProperty().bind(pane.heightProperty());
            imageView.fitWidthProperty().bind(pane.widthProperty());
            primaryStage.setMaximized(true);
            pane.setPrefWidth(600);
            pane.setPrefHeight(400);
            primaryStage.setResizable(true);
            primaryStage.show();
            return;
        }


        if (numberPlayers == 2) {
            terzo.setVisible(false);
            nomeTerzo.setVisible(false);
            punteggioTerzo.setVisible(false);
            quarto.setVisible(false);
            nomeQuarto.setVisible(false);
            punteggioQuarto.setVisible(false);

            nomePrimo.setText(tmpPlayer.getPlayerNickName());
            Punteggio = String.valueOf(match.getScores().get(tmpPlayer.getPlayerID()));
            punteggioPrimo.setText(Punteggio);
            tmpPlayer = players.get(0).getPlayerID() == tmpPlayer.getPlayerID() ? players.get(1) : players.get(0);

            nomeSecondo.setText(tmpPlayer.getPlayerNickName());
            Punteggio = String.valueOf(match.getScores().get(tmpPlayer.getPlayerID()));
            punteggioSecondo.setText(Punteggio);
        } else if (numberPlayers == 3) {

            quarto.setVisible(false);
            nomeQuarto.setVisible(false);
            punteggioQuarto.setVisible(false);

            nomePrimo.setText(tmpPlayer.getPlayerNickName());
            Punteggio = String.valueOf(match.getScores().get(tmpPlayer.getPlayerID()));
            punteggioPrimo.setText(Punteggio);

            max=0;
            for(Player p : match.getOrderOfPlayers()){
                if(!(p.getPlayerNickName().equals(nomePrimo.getText()))){
                    if(match.getScores().get(p.getPlayerID()) >= max){
                        max = match.getScores().get(p.getPlayerID());
                        maxPlayer= p.getPlayerNickName();
                    }
                }
            }
            nomeSecondo.setText(maxPlayer);
            Punteggio = String.valueOf(max);
            punteggioSecondo.setText(Punteggio);

            while(!flag) {
                for (Player p : match.getOrderOfPlayers()) {

                    if (!(p.getPlayerNickName().equals(nomePrimo.getText())) && !(p.getPlayerNickName().equals(nomeSecondo.getText()))) {
                        flag = true;
                        nomeTerzo.setText(p.getPlayerNickName());
                        Punteggio = String.valueOf(match.getScores().get(p.getPlayerID()));
                        punteggioTerzo.setText(Punteggio);
                    }
                }
            }
        }else if(numberPlayers==4){
            nomePrimo.setText(tmpPlayer.getPlayerNickName());
            Punteggio = String.valueOf(match.getScores().get(tmpPlayer.getPlayerID()));
            punteggioPrimo.setText(Punteggio);

            max=0;
            for(Player p : match.getOrderOfPlayers()){
                if(!(p.getPlayerNickName().equals(nomePrimo.getText()))){
                    if(match.getScores().get(p.getPlayerID()) >= max){
                        max = match.getScores().get(p.getPlayerID());
                        maxPlayer= p.getPlayerNickName();
                    }
                }
            }
            nomeSecondo.setText(maxPlayer);
            Punteggio = String.valueOf(max);
            punteggioSecondo.setText(Punteggio);

            max = 0;
            for(Player p : match.getOrderOfPlayers()){
                if(!(p.getPlayerNickName().equals(nomePrimo.getText())) && !(p.getPlayerNickName().equals(nomeSecondo.getText()))) {
                    if(match.getScores().get(p.getPlayerID()) >=max){
                        max = match.getScores().get(p.getPlayerID());
                        maxPlayer= p.getPlayerNickName();
                    }
                }
            }
            nomeTerzo.setText(maxPlayer);
            Punteggio = String.valueOf(max);
            punteggioTerzo.setText(Punteggio);
            while(!flag) {
                for (Player p : match.getOrderOfPlayers()) {
                    if (!(p.getPlayerNickName().equals(nomePrimo.getText())) && !(p.getPlayerNickName().equals(nomeSecondo.getText())) && !(p.getPlayerNickName().equals(nomeTerzo.getText()))) {
                        flag = true;
                        nomeQuarto.setText(p.getPlayerNickName());
                        Punteggio = String.valueOf(match.getScores().get(p.getPlayerID()));
                        punteggioQuarto.setText(Punteggio);
                    }
                }
            }

        }

        previousScene = primaryStage.getScene();
        Scene newScene = new Scene(newRoot);
        primaryStage.setScene(newScene);


        AnchorPane pane = (AnchorPane) newRoot.lookup("#anchorPane");
        ImageView imageView =(ImageView) newRoot.lookup("#sfondo") ;


        imageView.fitHeightProperty().bind(pane.heightProperty());
        imageView.fitWidthProperty().bind(pane.widthProperty());
        pane.setPrefWidth(600);
        pane.setPrefHeight(400);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    /**
     * This method updates the bookshelf of the players.
     * @param match the match in which the bookshelf has to be updated.
     * @author Valentino Guerrini
     */
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

    /**
     * This method is called when a player selects a column from the bookshelf.
     * @param event the SelectColumnEvent
     */
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

        mybookshelf.setStyle("-fx-background-color: transparent;");
    }

    //checkout

    /**
     * This method is called when a player wants to check out the tiles that he has selected from the LivingRoom
     * @param event the CheckOutTiles
     * @author Patrick Poggi
     */
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

    /**
     *This method is called when there is a SelectTileEvent
     *@param event The ActionEvent triggered by selecting a tile.
     *@throws IOException If an error occurs while loading the UI components.
     * @author Marta Giliberto
     */
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
    }


    //CHAT

    /**
     * This method is called when there is an ModifiedChatEVent, and it adds message box to the chat box,
     * when the player wants to send a message to another player or to everyone.
     * @param message the message that has to be added to the chat box.
     * @author Valentino Guerrini
     */
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

/**
     * This method is called when the player wants to send a message to another player or to everyone.
     * @param event the SendMessageEvent
     * @throws IOException If an error occurs while loading the UI components.
     * @author Valentino Guerrini
     */
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

    /**
     * This method is called when a player clicks "Yes" when he wants to reconnect to the game.
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     * @authot Paolo Gennaro
     */
    @FXML
    private void onClickYesReconnect(ActionEvent event) throws IOException {
        this.isReconnecting = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Button rmiButton = (Button) newRoot.lookup("#rmibutton");
        Button socketButton = (Button) newRoot.lookup("#socketbutton");

        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

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
        Stage currentStage = (Stage) yesButtonReConnection.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method is called when a player clicks "No" when he doesn't want to reconnect to the game.
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    @FXML
    private void onClickNoReconnect(ActionEvent event) throws IOException {
        this.isReconnecting = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();

        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

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
        Stage currentStage = (Stage) noButtonReConnection.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method is called when a player clicks "SOCKET" when he chooses to play with socket.
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    @FXML
    private void onClickSocketButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Insert_IP_Port_Server.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) socketButton.getScene().getWindow();
        this.connectionType = ConnectionType.SOCKET;
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method is called after a player has inserted the IP and the port of the server and clicked "CONNECT".
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    @FXML
    private void onClickConnectButton(ActionEvent event) throws IOException {

        String host = addressServer.getText();
        int port;
        try {
            port = Integer.parseInt(portServer.getText());
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
            InetAddress ipAddress = InetAddress.getByName(host);
            localIP = ipAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        connectionInfo = new ConnectionInfo(localIP,port, null, connectionType);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(connectionFile, false));
            bufferedWriter.write(new Gson().toJson(connectionInfo)+"\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FirstView.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) newRoot.lookup("#wallpaper");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) connectButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method is called when a player clicks "RMI" when he chooses to play with RMI.
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    @FXML
    private void onClickRMIButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Insert_Port_Server.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;

        this.connectionType = ConnectionType.RMI;
        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) RMIButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method is called after a player has inserted the port of the server and clicked "CONNECT".
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    @FXML
    private void onClickConnectRMIButton(ActionEvent event) throws IOException {
        int port;
        try {
            port = Integer.parseInt(portServerRMI.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid port");
            alert.setContentText("Please insert a valid port");
            alert.showAndWait();
            return;
        }
        String host = addressServerRMI1.getText();
        String localIP = null;

        try{
            networkHandler = new NetworkRMIHandler(host, port,this);
            localIP = null;
            try {
                InetAddress ipAddress = InetAddress.getByName(host);
                localIP = ipAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
        connectionInfo = new ConnectionInfo(localIP,port, null, connectionType);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(connectionFile, false));
            bufferedWriter.write(new Gson().toJson(connectionInfo)+"\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FirstView.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) newRoot.lookup("#wallpaper");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) connectButtonRMI.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method is called when a player clicks on Submit button after he has inserted his username
     * and number of players, then if the username is valid, the player is logged
     * and the new scene WaitingPlayers is loaded.
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     */
    @FXML
    private void onClickSubmitUsernamePlayerButton(ActionEvent event) throws IOException {
        int numPlayer = numberPlayersMenu.getSelectionModel().getSelectedIndex() + 2 ==1 ? 2 : numberPlayersMenu.getSelectionModel().getSelectedIndex()+2;

        this.myNick = usernameField.getText();
        try{
            networkHandler.onVCEvent(new LoginEvent(myNick, numPlayer));
        }catch(Exception ex){
            System.out.println("Error in login");
        }
        connectionInfo.setNickname(myNick);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(connectionFile, false));
            bufferedWriter.write(new Gson().toJson(connectionInfo)+"\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WaitingPlayersToConnect.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) submitUsernameAndPlayersButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method is called when a player clicks on Submit button after he has inserted his username,
     * then if the username is valid, the player is logged and the new scene WaitingPlayers is loaded.
     * @param event the event of the click
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    @FXML
    private void onClickUSubmitUsernameButton(ActionEvent event) throws IOException {
        this.myNick = onlyUsernameField.getText();
        try{
            networkHandler.onVCEvent(new LoginEvent(myNick));
        }catch(Exception ex){
            System.out.println("Error in login");
        }
        connectionInfo.setNickname(myNick);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(connectionFile, false));
            bufferedWriter.write(new Gson().toJson(connectionInfo)+"\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WaitingPlayersToConnect.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;


        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) submitUsernameButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * This method handles the event generated by clicking the "Quit" button.
     * It closes the current window, displays an information message to the user,
     * and terminates the application execution.
     *
     * @param event the event triggered by clicking the "Quit" button
     */
    @FXML
    private void onQuitButton(ActionEvent event){
        Stage currentStage = (Stage) quitButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Would you like to remember you've been connected and playing this match?");
        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        alert.showAndWait().ifPresent(response -> {
            if(response == buttonTypeOne){

            }else if(response == buttonTypeTwo){
                deleteDirectory();
            }
        });
        currentStage.close();
        System.exit(0);
    }

    /**
     * This method handles the event generated by clicking the "Quit" button before starting the game.
     * It closes the current window, displays an information message to the user,
     * and terminates the application execution.
     * @param event the event triggered by clicking the "Quit" button
     * @author Paolo Gennaro
     */
    @FXML
    private void onQuitButtonBeforeRunning(ActionEvent event){
        Stage currentStage = (Stage) quitButtonBeforeRunning.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(MatchEnded){
            alert.setTitle("Game Over");
            alert.setHeaderText("The directory for this match will be deleted! Thanks for playing! :)");
        }else{
            alert.setTitle("ATTENTION");
            alert.setHeaderText("Since you haven't joined a match yet, you wont be remembered!");
        }
        deleteDirectory();
        alert.showAndWait();
        currentStage.close();
        System.exit(0);
    }

    /**
     * This method handles the event generated by clicking the "Play" button.
     * Starts a new thread for the network handler.
     * @param event the event triggered by clicking the "Play" button
     * @throws IOException If an error occurs while loading the UI components.
     */
    @FXML
    private void onPlayButton(ActionEvent event) throws IOException{
        new Thread(this.networkHandler).start();
    }

    /**
     * This method handles the event generated by clicking the "Show Bookshelf" button.
     * Opens a new window with the previous scene.
     * @param event the event triggered by clicking the "Show Bookshelf" button
     * @throws IOException If an error occurs while loading the UI components.
     * @author Marta Giliberto
     */
    @FXML
    private void onShowBookshelf(ActionEvent event) throws IOException{
        Stage newStage = new Stage();
        newStage.setScene(previousScene);
        newStage.show();
    }

    /**
     * This method handles the event generated by clicking the "Back" button.
     * Loads the main scene and sets it as the current scene.
     * @param event the event triggered by clicking the "Back" button
     * @throws IOException If an error occurs while loading the UI components.
     * @author Paolo Gennaro
     */
    @FXML
    private void onBackButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Socket_RMI_Requests.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Button rmiButton = (Button) newRoot.lookup("#rmibutton");
        Button socketButton = (Button) newRoot.lookup("#socketbutton");

        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView wallpaper =(ImageView) newRoot.lookup("#parquet") ;

        wallpaper.fitHeightProperty().bind(pane.heightProperty());
        wallpaper.fitWidthProperty().bind(pane.widthProperty());

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

    /**
     * This method handles the event generated by clicking the "Rules" button.
     * It loads the rules scene and applies zoom functionality to the image.
     * @param event the event triggered by clicking the "Rules" button
     * @throws IOException If an error occurs while loading the UI components.
     * @author Marta Giliberto
     */
    @FXML
    private void onRulesButtonClicked(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Rules.fxml"));
        fxmlLoader.setController(this);
        Parent newRoot = fxmlLoader.load();
        Scene newScene = new Scene(newRoot);

        AnchorPane pane = (AnchorPane) newRoot.lookup("#pane_wall");
        ImageView rules =(ImageView) newRoot.lookup("#rules") ;

        rules.fitHeightProperty().bind(pane.heightProperty());
        rules.fitWidthProperty().bind(pane.widthProperty());

        // Apply zoom transformation to the scene
        Scale scale = new Scale(1, 1);
        pane.getTransforms().add(scale);

        newScene.setOnScroll((ScrollEvent scrollEvent) -> {
            double zoomFactor = Math.exp(scrollEvent.getDeltaY() * 0.01);
            double newZoomLevel = zoomLevel.get() * zoomFactor;

            // Limit the zoom factor within MIN_ZOOM and MAX_ZOOM limits
            if (newZoomLevel >= MIN_ZOOM && newZoomLevel <= MAX_ZOOM) {
                zoomLevel.set(newZoomLevel);

                // Calculate the zoom point based on the mouse position
                Point2D mousePoint = new Point2D(scrollEvent.getX(), scrollEvent.getY());
                Point2D scenePoint = pane.sceneToLocal(mousePoint);

                // Update the scale transformation relative to the zoom poin
                scale.setPivotX(scenePoint.getX());
                scale.setPivotY(scenePoint.getY());
                scale.setX(zoomLevel.get());
                scale.setY(zoomLevel.get());
            }

            scrollEvent.consume();
        });

        Stage secondaryStage = new Stage();
        secondaryStage.setScene(newScene);
        secondaryStage.show();
    }

    /**
     * This method resets the connection by performing the reconnection process on the UI thread.
     * @throws IOException If an error occurs while loading the UI components.
     */
    @Override
    public void resetConnection() throws IOException {
        Platform.runLater(() -> {
            try {
                isReconnecting = true;
                reconnectionProcess();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
