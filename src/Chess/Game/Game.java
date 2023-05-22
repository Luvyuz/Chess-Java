package Chess.Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Chess.Board.Board;
import Chess.Board.Move;
import Chess.Frame.GamePanel;
import Chess.Frame.GameWindow;
import Chess.Frame.TimerPanel;
import Chess.Pieces.*;
import Chess.Timer.Timer;
import Utils.LoadSave;
import Utils.PieceImages;
import Utils.SoundsManager;
import Utils.SoundsManager.Sound;
public class Game{
	public static final int ENGLISH = 0;
	public static final int ITALIANO = 1;
	private int language;
	private Board board;
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private TimerPanel timerPanel;
	private King wk;
	private King bk;
	private LinkedList<Piece> wPieces;
	private LinkedList<Piece> bPieces;
	private Timer timerPlayer;
	private Timer timerOpponents;

	private boolean whiteTurn = true;
	private Piece selectedPiece = null;
	public boolean drag = false;
	private LinkedList<Piece> allPieces;
	private LinkedList<Move> allPlayersMove;
	private LinkedList<Move> allEnemysMove;
	private boolean gameOver = false;
	public Game(int language){
		this.language = language;
		initClasses();
		start();
	}
	//inizio del gioco
	public void start(){
		loadFenPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		fillPieces();
		generatePlayersTurnMoves(board);
		generateEnemysMoves(board);
		checkPlayersLegalMoves();
	}
	//inizializzazione delel classi
	public void initClasses(){
		timerPanel = new TimerPanel(this);
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel, timerPanel);
		board = new Board(this);
        wPieces = new LinkedList<Piece>();
        bPieces = new LinkedList<Piece>();
        allPieces = new LinkedList<Piece>();
        allPlayersMove = new LinkedList<Move>();
        allEnemysMove = new LinkedList<Move>();
		new PieceImages();
		new SoundsManager();
	}
	//disegna tutto sul pannello
	public void draw(Graphics g, int x, int y, JPanel panel){
		drawBoard(g);
		drawPiece(g, panel);
		drawPossibleMoves(g, panel);
		drag(selectedPiece, x, y, g, panel);
		drawKingInCheck(whiteTurn, g, panel);
	}
	//genera le mosse del giocatore
	public void generatePlayersTurnMoves(Board board){
		allPlayersMove = new LinkedList<Move>();
		for(Piece p : allPieces){
			if(p.isWhite() == whiteTurn){
				p.fillAllPseudoLegalMoves(board);
				allPlayersMove.addAll(p.getMoves());
			}
		}
	}
	//genera le mosse dell'avversario
	public void generateEnemysMoves(Board board){
		allEnemysMove = new LinkedList<Move>();
		for(Piece p : allPieces){
			if(p.isWhite() != whiteTurn){
				p.fillAllPseudoLegalMoves(board);
				allEnemysMove.addAll(p.getMoves());
			}
		}
	}
	public LinkedList<Piece> getAllPieces(){
		return allPieces;
	}
	//cambio di turno
	public void changeSide(){
		if(!gameOver){
			whiteTurn = !whiteTurn;
			updateTimer();
			//genera le mosse dell'avversario
			generateEnemysMoves(board);
			//genera le mosse del giocatore
			generatePlayersTurnMoves(board);
			//controlla le mosse legali
			checkPlayersLegalMoves();
			//controlla lo scacco matto
			checkMate();
		}
	}
	//Viene usato per l'undo
	public void changeSide(boolean turn){
		if(!gameOver){
			whiteTurn = !whiteTurn;
			updateTimer();
			//genera le mosse dell'avversario
			generateEnemysMoves(board);
			//genera le mosse del giocatore
			generatePlayersTurnMoves(board);
			//controlla le mosse legali
			checkPlayersLegalMoves();
			//controlla lo scacco matto
			checkMate();
		}
	}
/*
 * 
	public void randomPlay() {
		if (gameOver) {
			return;
		}
		if (!whiteTurn) {
			Random r = new Random();
			selectedPiece = bPieces.get(r.nextInt(bPieces.size()));
			while (selectedPiece.getMoves().isEmpty()) {
				selectedPiece = bPieces.get(r.nextInt(bPieces.size()));
			}
			Move m = selectedPiece.getMoves().get(r.nextInt(selectedPiece.getMoves().size()));
			move(m.getxNew(), m.getyNew());
		}
	}
 */
	//ritorna il valore che determina a chi appartiene il turno
	public boolean isWhiteTurn(){
		return whiteTurn;
	}

	public void selectPiece(int x, int y){
		if (selectedPiece == null && board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == whiteTurn)
			selectedPiece = board.getPiece(x, y);
	}
	//controlla lo scacco matto
	public void timeOut(boolean isWhite){
		switch(language){
			case ENGLISH:
				JOptionPane.showMessageDialog(null, "Time Out! " + (!isWhite ? "White" : "Black") + " wins!");
				break;
			case ITALIANO:
				JOptionPane.showMessageDialog(null, "Tempo scaduto! " + (!isWhite ? "White" : "Black") + " wins!");
			    break;
			default:
				JOptionPane.showMessageDialog(null, "Time Out! " + (!isWhite ? "White" : "Black") + " wins!");
				break;
		}
		SoundsManager.playSound(Sound.GAMEOVER);
		LoadSave.saveData(board.getMoves());
	}
	public void checkMate(){
		switch(language){
			case ENGLISH:
				//controlla se ci sono ancora mosse legali nella lista
				if(whiteTurn){
					for(Piece p : wPieces)
						if(!p.getMoves().isEmpty()) 
							return;
				}else{
					for(Piece p : bPieces) 
						if(!p.getMoves().isEmpty()) 
							return;
				}
				SoundsManager.playSound(Sound.GAMEOVER);
				//controlla se il re è minacciato e non può più muoversi è scacco matto, altrimenti è stallo
				if(whiteTurn){
					if(wk.isInCheck()){
						JOptionPane.showMessageDialog(null, "Checkmate! " + (!whiteTurn ? "White" : "Black") + " wins!");
					}else{
						JOptionPane.showMessageDialog(null, "Stalemate ");
					}
				}else{
					if(bk.isInCheck()){
						JOptionPane.showMessageDialog(null, "Checkmate! " + (!whiteTurn ? "White" : "Black") + " wins!");
					}else{
						JOptionPane.showMessageDialog(null, "Stalemate! ");
					}
				}
				break;
			case ITALIANO:
				if(whiteTurn){
					for(Piece p : wPieces)
						if (!p.getMoves().isEmpty()) 
							return;
				}else{
					for(Piece p : bPieces) 
						if (!p.getMoves().isEmpty()) 
							return;
				}
				SoundsManager.playSound(Sound.GAMEOVER);
				if(whiteTurn){
					if(wk.isInCheck()){
						JOptionPane.showMessageDialog(null, "Scacco matto! " + (!whiteTurn ? "Il bianco" : "Il nero") + " vince!");
					}else{
						JOptionPane.showMessageDialog(null, "Stallo! ");
					}
				}else{
					if(bk.isInCheck()){
						JOptionPane.showMessageDialog(null, "Scacco matto!" + (!whiteTurn ? "Il bianco" : "Il nero") + " vince!");
					}else{
						JOptionPane.showMessageDialog(null, "Stallo!");
					}	
				}
				break;
		}
		gameOver = true;
		LoadSave.saveData(board.getMoves());
	}
	public void checkPlayersLegalMoves() {
		LinkedList<Piece> pieces = null;
		if(whiteTurn) 
			pieces = wPieces;
		else 
			pieces = bPieces;
		for(Piece p : pieces) 
			checkLegalMoves(p);
	}
	//controlla le mosse legali del giocatore creando una scacchiera "virtuale" dopo nel quale vengono salvate le mosse illegali in una lista, il cui verranno sottratte alle mosse possibili di un pezzo
	public void checkLegalMoves(Piece piece){
		//crea un lista delle mosse da rimuovere
		LinkedList<Move> movesToRemove = new LinkedList<Move>();
		//clona il pezzzo scelto e la scacchiera
		Board clonedBoard = board.getNewBoard();
		Piece clonedActive = piece.getClone();
		for(Move move : clonedActive.getMoves()){
			clonedBoard = board.getNewBoard();
			clonedActive = piece.getClone();
			clonedActive.makeMove(move.getxNew(), move.getyNew(), clonedBoard);
			LinkedList<Piece> enemyPieces = new LinkedList<Piece>();
			Piece king = null;
			if(piece.isWhite()){
				enemyPieces = bPieces;
				king = wk;
			}else{
				enemyPieces = wPieces;
				king = bk;
			}
			//controlla le mosse illegali
			for(Piece enemyP : enemyPieces){
				Piece clonedEnemyPiece = enemyP.getClone();
				clonedEnemyPiece.fillAllPseudoLegalMoves(clonedBoard);
				for (Move bMove : clonedEnemyPiece.getMoves()){
					if(!(clonedActive instanceof King) && bMove.getxNew() == king.getXcord() && bMove.getyNew() == king.getYcord()
						&& clonedBoard.getGrid()[enemyP.getXcord()][enemyP.getYcord()] == enemyP.getValueInTheboard()){
						movesToRemove.add(move);
					}else if(clonedActive instanceof King){
						if (bMove.getxNew() == clonedActive.getXcord() && bMove.getyNew() == clonedActive.getYcord() 
							&& clonedBoard.getGrid()[enemyP.getXcord()][enemyP.getYcord()] == enemyP.getValueInTheboard()){
							movesToRemove.add(move);
						}
					}
				}
			}
		}
		//rimuove le mosse illegali dal pezzo
		for(Move move : movesToRemove)
			piece.getMoves().remove(move);
	}
	//disegna il pezzo trascinato con il mouse
	public void drag(Piece piece, int x, int y, Graphics g, JPanel panel){
		if(piece != null && drag == true) 
			piece.drawDragPiece(g, whiteTurn, x, y, panel);
	}
	public King getKing(boolean white){
		if(white)
			return wk;
		else
			return bk;
	}
	//movi il pezzo
	public void move(int x, int y){
		if(selectedPiece != null){
			if(selectedPiece.makeMove(x, y, board)){
				SoundsManager.playSound(Sound.MOVE);
				tryToPromote(selectedPiece);
				changeSide();
				selectedPiece = null;
			}
			drag = false;
		}
	}
	public synchronized boolean isGameOver(){
		return gameOver;
	}
	public synchronized void gameOver(){
		gameOver = true;
	}
	//controlla se il re è sotto matto e lo evidenzia di rosso
	public void drawKingInCheck(boolean isWhite, Graphics g, JPanel panel){
		g.setColor(Color.RED);
		if(isWhite && wk.isInCheck())
			g.drawRect(wk.getXcord() * GameWindow.TILE_SIZE, wk.getYcord() * GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
		else if(bk.isInCheck())
			g.drawRect(bk.getXcord() * GameWindow.TILE_SIZE, bk.getYcord() * GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
		panel.revalidate();
		panel.repaint();
	}
	//disegna la scacchiera
	public void drawBoard(Graphics g){
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++){
				if((i + j) % 2 == 1)
					g.setColor(new Color(118, 150, 86));
				else 
					g.setColor(new Color(238, 238, 210));
				g.fillRect(i * GameWindow.TILE_SIZE, j * GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
			}
	}
	//promozione del pedone che arriva alla fine
	public void tryToPromote(Piece p){
		if(p instanceof Pawn){
			if(((Pawn) p).madeToTheEnd()){
				choosePiece(p, showMessageForPromotion());
				SoundsManager.playSound(Sound.PROMOTE);
			}	
		}
	}
	public int showMessageForPromotion(){
		Object[] optionsE = { "Queen", "Rook", "Knight", "Bishop" };
		Object[] optionsI = { "Regina", "Torre", "Cavallo", "Alfiere" };
		drag = false;
		switch(language){
			case ENGLISH:
				return JOptionPane.showOptionDialog(null, "Choose piece to promote to", null, 
				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, optionsE, optionsE[0]);
			case ITALIANO:
				return JOptionPane.showOptionDialog(null, "Scegli il pezzo da promuovere a", null, 
				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, optionsI, optionsI[0]);
			default:
				return JOptionPane.showOptionDialog(null, "Choose piece to promote to", null, 
				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, optionsE, optionsE[0]);
		}
	}
	//selezionamento in che pezzo promuovere
	public void choosePiece(Piece p, int choice){
		switch(choice){
			case 0:
				allPieces.remove(p);
				p = new Queen(p.getXcord(), p.getYcord(), p.isWhite(), board, p.isWhite() ? 8 : -8);
				allPieces.add(p);
				break;
			case 1:
				allPieces.remove(p);
				p = new Rook(p.getXcord(), p.getYcord(), p.isWhite(), board, p.isWhite() ? 5 : -5);
				allPieces.add(p);
				break;
			case 2:
				allPieces.remove(p);
				p = new Knight(p.getXcord(), p.getYcord(), p.isWhite(), board, p.isWhite() ? 3 : -3);
				allPieces.add(p);
				break;
			case 3:
				allPieces.remove(p);
				p = new Bishop(p.getXcord(), p.getYcord(), p.isWhite(), board, p.isWhite() ? 3 : -3);
				allPieces.add(p);
				break;
			default:
				allPieces.remove(p);
				p = new Queen(p.getXcord(), p.getYcord(), p.isWhite(), board, p.isWhite() ? 8 : -8);
				allPieces.add(p);
				break;
		}
		board.addPromotedPieces(p);
		//System.out.println(p.toString());
		fillPieces();
	}
	//disegna sul pannello le mosse legali del pezzo selezionato
	public void drawPossibleMoves(Graphics g, JPanel panel){
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		if (selectedPiece != null)
			selectedPiece.showMoves(g2, panel);
	}
	//disegna tutti i pezzi sul pannello
	public void drawPiece(Graphics g, JPanel panel){
		for(Piece p : allPieces)
			p.draw(g, panel);
	}
	public Piece getSelectedPiece(){
		return selectedPiece;
	}
	public LinkedList<Move> getAllEnemysMove(){
		return allEnemysMove;
	}
	public void setSelectedPiece(Piece p){
        selectedPiece = p;
    }
	//carica la posizioni dei pezzi data la stringa "FEN (Forsyth-Edwards Notation)", che semplifica le posizoni dei pezzi 
	public void loadFenPosition(String fen){
		String[] parts = fen.split(" ");
		String position = parts[0];
		int row = 0, col = 0;
		for(char c : position.toCharArray()){
			if (c == '/'){
				row++;
				col = 0;
			}
			if(Character.isLetter(c)){
				if(Character.isLowerCase(c))
					addToBoard(col, row, c, false); //se la lettera è maiuscola allora è un pezzo nero
				else 
					addToBoard(col, row, c, true); //se la lettera è maiuscola allora è un pezzo bianco
				col++;
			}
			//se invece è un numero lascia spazi vuoti
			if(Character.isDigit(c)) 
				col += Integer.parseInt(String.valueOf(c));
		}
		//controlla chi inizia
		if(parts[1].equals("w"))
			whiteTurn = true;
		else
			whiteTurn = false;
	}
	//riempe i pezzi nelle liste dei giocatori
	public void fillPieces(){
		wPieces = new LinkedList<Piece>();
		bPieces = new LinkedList<Piece>();
		for(Piece p : allPieces){
			p.setGame(this);
			if (p.isWhite())
				wPieces.add(p);
			else 
				bPieces.add(p);
		}
	}
	//aggiunge i pezzi sulla scacchiera
	public void addToBoard(int x, int y, char c, boolean isWhite){
		switch(String.valueOf(c).toUpperCase()){
		case "R":
			Rook r = new Rook(x, y, isWhite, board, isWhite ? 5 : -5);
			allPieces.add(r);
			break;
		case "N":
			allPieces.add(new Knight(x, y, isWhite, board, isWhite ? 3 : -3));
			break;
		case "B":
			allPieces.add(new Bishop(x, y, isWhite, board, isWhite ? 3 : -3));
			break;
		case "Q":
			allPieces.add(new Queen(x, y, isWhite, board, isWhite ? 8 : -8));
			break;
		case "K":
			King king = new King(x, y, isWhite, board, isWhite ? 10 : -10);
			allPieces.add(king);
			if(isWhite)
				wk = king;
			else
				bk = king;
			break;
		case "P":
			allPieces.add(new Pawn(x, y, isWhite, board, isWhite ? 1 : -1));
			break;
		}
	}
    public Board getBoard() {
        return board;
    }
	public int getLanguage(){
		return language;
	}
	public void closeWindow(){
		gameWindow.dispose();
	}
	public void setTimerPlayer(Timer timer){
		this.timerPlayer = timer;
	}
	public void setTimerOpponent(Timer timer){
		this.timerOpponents = timer;
	}
	public void updateTimer(){
		if(timerPlayer.isWhiteTimer() == isWhiteTurn()){
			timerPlayer.startTimer();
			timerOpponents.stopTimer();
		}else {
			timerPlayer.stopTimer();
			timerOpponents.startTimer();
		}
	}

}