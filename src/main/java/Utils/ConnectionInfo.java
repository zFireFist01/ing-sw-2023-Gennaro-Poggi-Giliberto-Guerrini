package Utils;

import Client.ConnectionType;
import com.google.gson.Gson;

import java.util.Objects;


/**
 * The {@code ConnectionInfo} class is used to store and handle information related to client connections.
 * It includes details like IP address, port number, nickname of the client, and type of connection (socket, RMI, etc.)
 * This class implements {@code java.io.Serializable}, allowing it to be converted into a byte stream and transferred.
 *
 * @author Patrick Poggi
 */
public class ConnectionInfo implements java.io.Serializable{
    private String ip;
    private int port;
    private String nickname;
    private ConnectionType connectionType;

    /**
     * Constructs a new {@code ConnectionInfo} instance with all necessary information.
     *
     * @param ip The IP address of the client.
     * @param port The port number on which the client is listening.
     * @param nickname The nickname assigned to the client.
     * @param connectionType The type of connection.
     */
    public ConnectionInfo(String ip, int port, String nickname, ConnectionType connectionType) {
        this.ip = ip;
        this.port = port;
        this.nickname = nickname;
        this.connectionType = connectionType;
    }

    /**
     * Constructs a new {@code ConnectionInfo} instance with only IP address and connection type, assigning default values to other fields.
     *
     * @param ip The IP address of the client.
     * @param connectionType The type of connection.
     */
    public ConnectionInfo(String ip, ConnectionType connectionType) {
        this.ip = ip;
        this.port = -1;
        this.nickname = null;
        this.connectionType = connectionType;
    }

    /**
     * Constructs a new {@code ConnectionInfo} instance with IP address, connection type, and nickname, assigning a default value to the port.
     *
     * @param ip The IP address of the client.
     * @param connectionType The type of connection.
     * @param nickname The nickname assigned to the client.
     */
    public ConnectionInfo(String ip, ConnectionType connectionType, String nickname) {
        this.ip = ip;
        this.port = -1;
        this.nickname = nickname;
        this.connectionType = connectionType;
    }

    // getter and setter methods...
    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getNickname() {
        return nickname;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * Returns a string representation of the {@code ConnectionInfo} instance,
     * which uniquely identifies the connection based on the nickname and IP address.
     *
     * @return A string containing the nickname and IP address of the client.
     */
    public String getSignature(){
        return this.nickname+ "@" + this.ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionInfo that = (ConnectionInfo) o;
        return this.getSignature().equals(that.getSignature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, nickname);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

