package Client.View.GUI;

import Client.NetworkRMIHandler;
import Client.NetworkSocketHandler;
import Utils.ConnectionInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class GUIController {
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
    private void onClickYesReconnect(ActionEvent event) throws IOException {
        isReconnecting = true;
        Parent newRoot = FXMLLoader.load(getClass().getResource("/Socket_RMI_Requests.fxml"));
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) yesButtonReConnection.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void OnClickNoReconnect(ActionEvent event) throws IOException {
        isReconnecting = false;
        Parent newRoot = FXMLLoader.load(getClass().getResource("/Socket_RMI_Requests.fxml"));
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) noButtonReConnection.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void OnClickSocketButton(ActionEvent event) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/Insert_IP_Port_Server.fxml"));
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) socketButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
    @FXML
    private void OnClickConnectButton(ActionEvent event) throws IOException {
        String host = addressServer.getText();
        int port = Integer.parseInt(portServer.getText());
        this.networkHandler = new NetworkSocketHandler(host, port, this, isReconnecting);
        String localIP = null;
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            localIP = ipAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        connectionInfo = new ConnectionInfo(localIP,connectionType, previousNickname);
        Parent newRoot = FXMLLoader.load(getClass().getResource("/FirstView.fxml"));

        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) connectButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
    @FXML
    private void OnClickRMIButton(ActionEvent event) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/Insert_Port_Server.fxml"));
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) RMIButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
    @FXML
    private void OnClickConnectRMIButton(ActionEvent event) throws IOException {
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
        Parent newRoot = FXMLLoader.load(getClass().getResource("/FirstView.fxml"));
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) connectButtonRMI.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
    @FXML
    private void OnClickUSubmitUsernameButton(ActionEvent event) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/GameView.fxml"));
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) submitUsernameAndPlayersButton.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
    @FXML
    private void OnQuitButton(ActionEvent event){
        Stage currentStage = (Stage) quitButton.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    private void OnPlayButton(ActionEvent event) throws IOException{
        new Thread(networkHandler).start();
    }
}
