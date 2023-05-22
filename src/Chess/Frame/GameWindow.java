package Chess.Frame;

import javax.swing.JFrame;

import Chess.Board.Board;

import java.awt.BorderLayout;
import java.awt.Dimension;
public class GameWindow extends JFrame{
	public static final int TILE_SIZE = 96;
	public static final int WIDTH = TILE_SIZE * Board.ROWS;
	public static final int HEIGTH = TILE_SIZE * Board.COLUMNS;
	private GamePanel gamePanel;
	private TimerPanel timerPanel;
	public GameWindow(GamePanel gamePanel, TimerPanel timerPanel ){
		this.gamePanel = gamePanel;
		setLayout(new BorderLayout());
		this.timerPanel = timerPanel;
		add(gamePanel ,BorderLayout.CENTER );
		add(timerPanel, BorderLayout.EAST);
		setTitle("Chess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		getContentPane().setPreferredSize(new Dimension(WIDTH + 400, HEIGTH));
		pack();
		setLocationRelativeTo(null);
	}
}
