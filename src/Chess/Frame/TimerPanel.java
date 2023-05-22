package Chess.Frame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Chess.Timer.Timer;
import Chess.Game.Game;
import Chess.Listener.MouseListener;
import Utils.CustomButton;

public class TimerPanel extends JPanel{

    private final static int WIDTH = 400;
    private final static int HEIGHT = GameWindow.HEIGHT;
    private ArrayList<CustomButton> buttons;
    private Timer timerPlayer, timerOpponent;
    private JLabel lblTimerPlayer, lblTimerOpponent;
    private Game game;

    public TimerPanel(Game game){
        this.game = game;
        
        lblTimerPlayer = new JLabel();
        lblTimerOpponent = new JLabel();

        timerPlayer = new Timer(this, true, game);
        timerOpponent = new Timer(this, false, game);
        
        this.setLayout(null);

        lblTimerPlayer.setSize(50, 30);
        lblTimerPlayer.setLocation(350, 728);
        lblTimerOpponent.setSize(50, 30);
        lblTimerOpponent.setLocation(350, 5);

        this.add(lblTimerPlayer);
        this.add(lblTimerOpponent);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        buttons = new ArrayList<CustomButton>();
        createButtons();
        addMouseListener(new MouseListener(this, game));
        
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        drawButtons(g);
    }
    



    private void createButtons(){
        buttons.add(new CustomButton("OpponentImage", "/Chess/images/UserImg.png" ,5, 5, 34, 34,true));
        buttons.add(new CustomButton("PlayerImage", "/Chess/images/UserImg.png", 5, 728, 34, 34,true));
        buttons.add(new CustomButton("undo", "/Chess/images/undo.png", 0, 450, 100, 60,true));
        buttons.add(new CustomButton("btnNewGame", "/Chess/images/new_game.png", 125, 445, 200, 60,true));
        buttons.add(new CustomButton("Opponent", "/Chess/images/Opponent.png", 39, 5, 70, 40,true));
        buttons.add(new CustomButton("Player", "/Chess/images/Player.png", 39, 728, 70, 40,true));
    }
    private void drawButtons(Graphics g){
        for (CustomButton customButton : buttons) {
            customButton.displayOnPanel(g);
        }
    }

    public ArrayList<CustomButton> getButtons(){
        return buttons;
    }

    public void setTimerPlayer(int min, int sec){
        lblTimerPlayer.setText(String.format("%02d:%02d\n", min, sec));
    }

    public void setTimerOpponent(int min, int sec){
        lblTimerOpponent.setText(String.format("%02d:%02d\n", min, sec));
    }

    public void startTimer(boolean isWhite){
        if(isWhite)timerPlayer.startTimer();
        else timerOpponent.startTimer();
    }

    public boolean isTimerRunning(boolean isWhite){
        if(isWhite)return timerPlayer.isRunning();
        else return timerOpponent.isRunning();
    }

    public void stopTimer(boolean isWhite){
        if(isWhite)timerPlayer.stopTimer();
        else timerOpponent.stopTimer();
    }
}