package Server.Model.Player;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Connected means that the player is currently playing or waiting for is turn to play
 * @author Paolo Gennaro
 */
public class Connected extends PlayerStatus {
    private Date turnStartTime;
    private long turnDuration;
    private Timer durationTimer;

    public void startTurn() {
        //imposta il tempo di inizio ad adesso
        turnStartTime = new Date();

        // fa partire il timer
        durationTimer = new Timer();
        durationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateDuration();
            }
        }, 1000, 1000);
    }

    public long endTurn() {

        durationTimer.cancel();

        // ritorno la durata totale del tempo
        return turnDuration;
    }

    private void updateDuration() {
        // posso sapere la durata in real time
        Date turnEndTime = new Date();
        turnDuration = turnEndTime.getTime() - turnStartTime.getTime();

        if (turnDuration > 300000) {
            System.out.println(" sono passati 5 minuti. fai la tua mossa!");
            //genero eventooooo
        }

        // stampa la durata in real-time
        System.out.println("Turn duration: " + turnDuration / 1000 + " seconds");
    }
}