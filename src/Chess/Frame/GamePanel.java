package Chess.Frame;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import Chess.Game.*;
import Chess.Listener.Listener;

public class GamePanel extends JPanel{
	private int x, y;
	private Game game;
	public GamePanel(Game game){
		this.game = game;
		this.setFocusable(true);
		this.addMouseListener(new Listener(this));
		this.addMouseMotionListener(new Listener(this));
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 37){
					//game.getBoard().undoMove();
				}
			}
		});
	}
	public void setPos(int x, int y){
		this.x = x;
        this.y = y;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.draw(g, x, y, this);
	}
	public Game getGame() {
		return game;
    }
}
