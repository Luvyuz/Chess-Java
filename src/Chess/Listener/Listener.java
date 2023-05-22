package Chess.Listener;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import Chess.Frame.GamePanel;
import Chess.Frame.GameWindow;
import Chess.Game.Game;

public class Listener extends MouseAdapter{
    private GamePanel panel;
    private Game game;
    private int mouseX,mouseY;
    public Listener(GamePanel panel){
        this.panel = panel;
        this.game = panel.getGame();
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
            if(game.isGameOver())
                return;
            int x = e.getX()/GameWindow.TILE_SIZE;
            int y = e.getY()/GameWindow.TILE_SIZE;
            game.drag = false;
            game.setSelectedPiece(null);
            game.selectPiece(x, y);
            panel.revalidate();
            panel.repaint();
            //debug(x, y);
        }else{
            //game.getBoard().printBoard();
            //System.out.println(game.getAllPieces().toString());
            //System.out.println(game.getBoard().getDeadPieces() + "\n");
        }
    }
    private void debug(int x, int y){
        if(game.getBoard().getPiece(x, y) != null)
           System.out.println(game.getBoard().getPiece(x, y).toString());
    }
    @Override
    public void mouseMoved(MouseEvent e){
        mouseX = e.getX()/GameWindow.TILE_SIZE;
        mouseY = e.getY()/GameWindow.TILE_SIZE;
        if(game.getBoard().getPiece(mouseX, mouseY) != null) 
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        else 
            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        panel.revalidate();
        panel.repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e){
        if(!game.drag && game.getSelectedPiece() != null)
            game.setSelectedPiece(null);
        if(SwingUtilities.isLeftMouseButton(e)) {
            game.selectPiece(e.getX()/GameWindow.TILE_SIZE, e.getY()/GameWindow.TILE_SIZE);
            game.drag = true;
            panel.setPos(e.getX(), e.getY());			
        }
        panel.revalidate();
        panel.repaint();
    }
    @Override
    public void mouseReleased(MouseEvent e){
        int x = e.getX()/GameWindow.TILE_SIZE;
        int y = e.getY()/GameWindow.TILE_SIZE;
        game.move(x, y);
        panel.revalidate();
        panel.repaint();
    }
}
