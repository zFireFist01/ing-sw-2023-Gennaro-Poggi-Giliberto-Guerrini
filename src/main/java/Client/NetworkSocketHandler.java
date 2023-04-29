package Client;

import Server.Events.MVEvents.MVEvent;
import Server.Events.MVEvents.ModifiedLivingRoomEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Network.VirtualView;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class NetworkSocketHandler implements NetworkHandler{
    Socket socket;
    ServerSocket server;
    Scanner in;
    OutputStream out;
    public NetworkSocketHandler(ServerSocket server) {
        socket = new Socket();
        try {
            in = new Scanner(socket.getInputStream());
            out = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendMVEvent(String json) throws RemoteException {

    }

    @Override
    public void sendSelectViewEvent(String json) throws RemoteException {

    }

    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        throw new IllegalAccessError("This method should not be called");
    }

    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Gson gson = new Gson();
        String json = gson.toJson(event);
        try {
            out.write(json.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String message;
        String primaryType;
        String secondaryType;
        Event event;
        while (true){
            message = in.nextLine();

            Gson gson = new Gson();
            Event event = gson.fromJson(message, Event.class);
            primaryType = event.getPrimaryType();

            switch (primaryType){
                case "MVEvent":
                    secondaryType = (MVEvent)event.getType();
                    switch (secondaryType){
                        case "ModifiedPointsEvent":
                            ModifiedPointsEvent modifiedPointsEvent = gson.fromJson(message, ModifiedPointsEvent.class);
                            break;
                        case "ModifiedBookshelfEvent":
                            ModifiedBookshelfEvent modifiedBookshelfEvent = gson.fromJson(message,ModifiedBookshelfEvent.class);
                            break;
                        case "ModifiedChatEvent":
                            ModifiedChatEvent modifiedChatEvent = gson.fromJson(message, ModifiedChatEvent.class);
                            break;
                        case "ModifiedLivingRoomEvent":
                            ModifiedLivingRoomEvent modifiedLivingRoomEvent = gson.fromJson(message, ModifiedLivingRoomEvent.class);
                            break;
                        case "ModifiedMatchEndedEvent":
                            ModifiedMatchEndedEvent modifiedMatchEndedEvent = gson.fromJson(message, ModifiedMatchEndedEvent.class);
                            break;
                        default:
                            throw new RuntimeException("Unknown event type");
                    }
                    break;
                case "SelectViewEvent":
                    secondaryType = (SelectViewEvent)event.getType();
                    switch (secondaryType){

                        case "EndedMatchEvent":
                            EndedMatchEvent endedMatchEvent = gson.fromJson(message, EndedMatchEvent.class);
                            break;
                        case "ChatONEvent":
                            ChatONEvent chatOFFEvent = gson.fromJson(message, ChatONEvent.class);
                            break;
                        case "ChatOFFEvent":
                            ChatOFFEvent chatOFFEvent = gson.fromJson(message, ChatOFFEvent.class);
                            break;
                        default:
                            throw new RuntimeException("Unknown event type");
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown event type");
            }
        }
    }
}
