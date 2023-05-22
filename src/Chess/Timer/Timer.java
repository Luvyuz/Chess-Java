package Chess.Timer;

import Chess.Frame.TimerPanel;
import Chess.Game.Game;

public class Timer extends Thread {
    private int countdownMinutes;
    private int countdownSeconds;
    private TimerPanel pnl;
    private boolean running;
    private boolean isWhite;
    private Game game;

    public Timer(TimerPanel pnl, boolean isWhite, Game game) {
        this.countdownMinutes = 5;
        this.countdownSeconds = 0;
        this.game = game;
        this.pnl = pnl;
        running = false;
        this.isWhite = isWhite;
        if(isWhite){
            pnl.setTimerPlayer(5, 0);
            game.setTimerPlayer(this);
        }else{ 
            pnl.setTimerOpponent(5,0);
            game.setTimerOpponent(this);
        }

    }

    @Override
    public void run() {
       while(true){
        
        
        
        if (countdownMinutes >= 0 && running) {
            
            if (countdownSeconds == 0) {
                if (countdownMinutes == 0) {
                    System.out.println("Tempo scaduto!");
                    break;
                } else {
                    countdownMinutes--;
                    countdownSeconds = 60;
                }
            } else {
                countdownSeconds--;
                if(isWhite){
                    pnl.setTimerPlayer(countdownMinutes, countdownSeconds);
                }else pnl.setTimerOpponent(countdownMinutes, countdownSeconds);
            }
    
                
            }else if(countdownMinutes < 0)break;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
       }


       
    }

    public void stopTimer() {
        running = false;
    }

    public void startTimer() {
        running = true;
        if(!this.isAlive())this.start();
        
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isWhiteTimer() {
        return isWhite;
    }

}
