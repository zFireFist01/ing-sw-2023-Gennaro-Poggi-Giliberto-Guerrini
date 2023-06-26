package Utils;

import Client.ConnectionType;
import com.google.gson.Gson;

import java.util.Objects;

public class ConnectionInfo implements java.io.Serializable{
    private String ip;
    private int port;
    private String nickname;
    private ConnectionType connectionType;

    public ConnectionInfo(String ip, int port, String nickname, ConnectionType connectionType) {
        this.ip = ip;
        this.port = port;
        this.nickname = nickname;
        this.connectionType = connectionType;
    }

    public ConnectionInfo(String ip, ConnectionType connectionType) {
        this.ip = ip;
        this.port = -1;
        this.nickname = null;
        this.connectionType = connectionType;
    }

    public ConnectionInfo(String ip, ConnectionType connectionType, String nickname) {
        this.ip = ip;
        this.port = -1;
        this.nickname = nickname;
        this.connectionType = connectionType;
    }


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

    public String getSignature(){
        //return this.nickname+ "@" + this.ip + ":" + this.port;
        return this.nickname+ "@" + this.ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionInfo that = (ConnectionInfo) o;
        //return port == that.port && Objects.equals(ip, that.ip) && Objects.equals(nickname, that.nickname) && connectionType == that.connectionType;
        return this.getSignature().equals(that.getSignature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, nickname);
    }

    @Override
    public String toString() {
        /*return "ConnectionInfo{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", nickname='" + nickname + '\'' +
                ", connectionType=" + connectionType +
                '}';*/
        return new Gson().toJson(this);
    }
}

