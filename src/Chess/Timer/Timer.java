package Chess.Timer;

import Chess.Frame.TimerPanel;
import Chess.Game.Game;

public class Timer extends Thread {
    private int countdownMinutes;
    private int countdownSeconds;
    private TimerPanel pnl;
    private boolean running;
    private boolean isWhite;
    private boolean timeOut;
    private Game game;
    public static int MINUTES = 5;
    public static int SECONDS = 0;
    public Timer(TimerPanel pnl, boolean isWhite, Game game) {
        this.countdownMinutes = MINUTES;
        this.countdownSeconds = 0;
        this.game = game;
        this.pnl = pnl;
        this.isWhite = isWhite;
        running = false;
        timeOut = false;
        setTimerInGame();
    }
    public void setTimerInGame(){
        if(isWhite){
            pnl.setTimerPlayer(countdownMinutes, countdownSeconds);
            game.setTimerPlayer(this);
        }else{ 
            pnl.setTimerOpponent(countdownMinutes, countdownSeconds);
            game.setTimerOpponent(this);
        }
    }
    @Override
    public void run() {
        while(!game.isGameOver()){
            if(countdownMinutes <= 0 && countdownSeconds <= 0){
                game.gameOver();
                timeOut = true;
            }
            if (countdownMinutes >= 0 && running) {
                if (countdownSeconds == 0) {
                    if (countdownMinutes == 0) {
                        //System.out.println("Tempo scaduto!");
                        break;
                    } else {
                        countdownMinutes--;
                        countdownSeconds = 60;
                    }
                } else {
                    countdownSeconds--;
                    if(isWhite)
                        pnl.setTimerPlayer(countdownMinutes, countdownSeconds);
                    else 
                        pnl.setTimerOpponent(countdownMinutes, countdownSeconds);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                ;//e.printStackTrace();
            }
        }
        if(timeOut)
            game.timeOut(isWhite);
        running = false;
    }

    public void stopTimer() {
        running = false;
    }

    public void startTimer() {
        running = true;
        if(!this.isAlive()){
            try{
                this.start();
            }catch(IllegalThreadStateException e){
                ;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isWhiteTimer() {
        return isWhite;
    }

}
