package Chess.Pieces;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import Chess.Board.Board;
import Chess.Board.Move;
import Chess.Frame.GameWindow;
import Chess.Game.Game;
import Utils.PieceImages;

public abstract class Piece implements Cloneable {
	protected int xCord;
	protected int yCord;
	protected boolean isWhite;
	protected boolean isAlive;
	protected int valueInTheBoard;
	protected Board board;
	protected LinkedList<Move> moves;
	protected BufferedImage image;
	protected int color;
	protected Game game;
	public Piece(int x,int y,boolean iswhite,Board board, int value){
		this.xCord = x;
		this.yCord = y;
		this.isWhite = iswhite;
		this.board = board;
		isAlive = true;
		moves = new LinkedList<Move>();
		intializeSide(value);
		board.setPieceIntoBoard(x, y, this);
	}
	//Tutti i pezzi devono possedere il metodo per controllare il loro movimento
	public abstract boolean canMove(int x ,int y, Board board);
	public abstract int getRank();
	public void setGame(Game game){
		this.game = game;
	}
	//restituisce true se la mossa è stata effetuata altrimento false
	public boolean makeMove(int xNew, int yNew, Board board){
		//crea una muova mossa
		Move move = new Move(xCord, yCord, xNew, yNew, this);
		if(!alive()) 
			return false;
		for(Move m: moves)
			//se questa mossa è prensente tra le mosse legali, esegue la mossa
			if(m.compareTo(move) == 0){
				board.updatePieces(xCord, yCord, xNew, yNew, this);
				xCord = xNew;
				yCord = yNew;
				return true;
			}
		return false;
	}
	public boolean alive(){
		if (board.getXY(xCord, yCord) != valueInTheBoard || board.getXY(xCord, yCord) == 0 || board.getPiece(xCord, yCord) == null) {
			isAlive = false;
			game.getAllPieces().remove(this);
		}
		return isAlive;
	}
	//inizializzazione del pezzo in base al colore e in base al valore
	public void intializeSide(int value){
		if(isWhite) 
			color = PieceImages.WHITE;
		else 
			color = PieceImages.BLACK;
		valueInTheBoard = value;
	}
	//mostra le mosse sul pannello
	public void showMoves(Graphics g, JPanel panel){
		Graphics2D g2 = (Graphics2D) g;
		for(Move m: moves){
			if(board.getPiece(m.getxNew(), m.getyNew()) != null && board.getPiece(m.getxNew(), m.getyNew()).isWhite() != isWhite())
				g.setColor(new Color(255, 28, 28, 175));
			else 
				g.setColor(new Color(0, 0, 0, 100));
			g.fillOval((m.getxNew()* GameWindow.TILE_SIZE) + GameWindow.TILE_SIZE / 3, (m.getyNew() * GameWindow.TILE_SIZE) + GameWindow.TILE_SIZE / 3, GameWindow.TILE_SIZE / 3, GameWindow.TILE_SIZE / 3);
			g2.setColor(Color.DARK_GRAY);
			if(game.drag) 
				g2.fillRect(m.getxOld() * GameWindow.TILE_SIZE, m.getyOld() * GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);				
			else 
				g2.drawRect(m.getxOld() * GameWindow.TILE_SIZE, m.getyOld() * GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
		}
		panel.revalidate();
		panel.repaint();
	}
	//disegna il pezzo sul pannello
	public void draw(Graphics g, JPanel panel){
			g.drawImage(image, xCord * GameWindow.TILE_SIZE, yCord * GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, panel);
			panel.revalidate();
			panel.repaint();
	}
	//disegna il pezzo trascinato
	public void drawDragPiece(Graphics g, boolean player, int x, int y, JPanel panel){
			g.drawImage(image, x - GameWindow.TILE_SIZE / 2, y - GameWindow.TILE_SIZE / 2, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, panel);
			panel.revalidate();
			panel.repaint();
	}
	//riempe tutte le mosse possibili
	public void fillAllPseudoLegalMoves(Board b){
		moves = new LinkedList<Move>();
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(canMove(i, j, b))
					moves.add(new Move(xCord, yCord, i, j, this));
	}
	public int getXcord(){
		return xCord;
	}
	public void setXcord(int xcord){
		this.xCord = xcord;
	}
	public int getYcord(){
		return yCord;
	}
	public void setYcord(int ycord){
		this.yCord = ycord;
	}
	public boolean isWhite(){
		return isWhite;
	}
	public void setWhite(boolean isWhite){
		this.isWhite = isWhite;
	}
	public Board getBoard(){
		return board;
	}
	public void setBoard(Board board){
		this.board = board;
	}
	public void setValueInTheboard(int value){
		this.valueInTheBoard = value;
	}
	public int getValueInTheboard(){
		return valueInTheBoard;
	}
	public List<Move> getMoves(){
		return moves;
	}
	public void setMoves(LinkedList<Move> moves){
		this.moves = moves;
	}
	public Piece getClone(){
		try{
			return (Piece) this.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String toString(){
		return "[x:" + xCord + ", y:" + yCord + ", " + isWhite + ", isAlive:" + isAlive + ", value:" + valueInTheBoard + "]";
	}
}
