package Chess.Listener;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.event.MouseInputListener;

import Chess.Frame.GameWindow;
import Chess.Frame.TimerPanel;
import Chess.Game.Game;
import Utils.CustomButton;

public class MouseListener implements MouseInputListener {
	TimerPanel pnl;
	boolean sel = false;
    Game game;
    public MouseListener(TimerPanel pnl, Game game){
        this.pnl = pnl;
        this.game = game;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
		int y = e.getY();
        System.out.println("x: " + x + " y: " + y);
        ArrayList<CustomButton> b;
        b = pnl.getButtons();
        CustomButton selectedButton = null;
        for(CustomButton cb : b)
            if(cb.isClicked(x , y))
                selectedButton = cb;
        if(selectedButton != null){
            switch(selectedButton.getName()){
                case "btnNewGame":
                    new Game(game.getLanguage());
                    game.closeWindow();
                    
                break;
                case "undo":
                    game.getBoard().undoMove();

                break;
    
                default: break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub  
    }
 
    
}