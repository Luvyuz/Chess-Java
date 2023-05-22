package Chess.Board;

import java.util.*;

import Chess.Pieces.*;
import Chess.Game.*;

public class Board implements Cloneable{
	public static final int ROWS = 8;
	public static final int COLUMNS = 8;
	
	private int[][] grid;
	private Piece[][] pieces;
	private Piece lastPieceMoved;
	private Move lastMove;
	private Piece died;
	
	private Stack<Move> lastMoves;
	private Stack<Piece> deadPieces;
	private Stack<Piece> promotedPieces;
	private LinkedList<Piece> piecesList;
	private Game game;
	public Board(Game game){
		grid = new int[ROWS][COLUMNS];
		pieces = new Piece[ROWS][COLUMNS];
		lastMoves = new Stack<>();
		deadPieces = new Stack<>();
		piecesList = new LinkedList<Piece>();
		promotedPieces = new Stack<Piece>();
		this.game = game;
	}
	public void setPieceIntoBoard(int x, int y, Piece piece){
		if(piece != null){
			grid[x][y] = piece.getValueInTheboard();
			pieces[x][y] = piece;
			piecesList.add(piece);			
		}else{
			grid[x][y] = 0;
			pieces[x][y] = null;
		}
	}
	//Aggiorna i pezzi sulla scacchiera
	public void updatePieces(int fromX, int fromY, int toX, int toY, Piece piece){
		lastMove = new Move(fromX, fromY, toX, toY, piece);
		lastMoves.add(lastMove);
		//se nella posizione in cui si muove il è presente un pezzo dell'avversario, lo cattura e lo rimuove dalla scacchiera
		if(pieces[toX][toY] != null){
			died = pieces[toX][toY];
			deadPieces.add(died);
			piecesList.remove(died);
			game.getAllPieces().remove(died);
			game.fillPieces();
		}else{
			deadPieces.add(null);
		}
		//aggiorna il pezzo nella nuova posizione
		grid[fromX][fromY] = 0;
		grid[toX][toY] =  piece.getValueInTheboard();
		pieces[fromX][fromY] = null;
		pieces[toX][toY] = piece;
		lastPieceMoved = piece;
	}
	public void undoPromotion(Move move){
		Piece promotedPiece = promotedPieces.pop();
					
		piecesList.remove(promotedPiece);
		piecesList.add(move.getPiece());
		game.getAllPieces().remove(promotedPiece);
		game.getAllPieces().add(move.getPiece());
		game.fillPieces();
	}
	//serve ad annullare la mossa precedente eseguita
	public void undoMove(){
		//controlla se la pila delle mosse è vuota 
		if(!lastMoves.isEmpty()){
			Move move = lastMoves.pop();
			Piece dead = deadPieces.pop();
			lastPieceMoved = move.getPiece();
			//riposiziona il pezzo nella posizione originale
			grid[move.getxOld()][move.getyOld()] = lastPieceMoved.getValueInTheboard();
			pieces[move.getxOld()][move.getyOld()] = lastPieceMoved;
			lastPieceMoved.setXcord(move.getxOld());
			lastPieceMoved.setYcord(move.getyOld());
			//ripristina il pezzo se è stato promosso
			if(move.getPiece() instanceof Pawn){
				if(move.getPiece().getYcord() == (move.getPiece().isWhite() ? 6 : 1)){
					((Pawn)	move.getPiece()).setFirstMove(true);
				}else if(move.getPiece().getYcord() == (move.getPiece().isWhite() ? 7 : 0)){
					undoPromotion(move);
				}
			}
			//ripristina la torre che è appena mosso
			if(move.getPiece() instanceof Rook){
				if(((Rook) lastPieceMoved).isJustMoved()){
					((Rook) lastPieceMoved).setHasMoved(false);
					((Rook) lastPieceMoved).setJustMoved(false);
				}
				//ripristina il re arroccato
				Move secondLastMove = lastMoves.pop();
				Piece castledKing = secondLastMove.getPiece();
				if(castledKing instanceof King){
					if(((King)castledKing).hasMoved()){
						((King) castledKing).setJustCastled(false);
						((King) castledKing).setMoved(false);
					}
					grid[secondLastMove.getxOld()][secondLastMove.getyOld()] = castledKing.getValueInTheboard();
					pieces[secondLastMove.getxOld()][secondLastMove.getyOld()] = castledKing;
					castledKing.setXcord(secondLastMove.getxOld());
					castledKing.setYcord(secondLastMove.getyOld());
				}
			}
			//ripristina il re che si è appena mosso
			if(lastPieceMoved instanceof King){
				if(((King) lastPieceMoved).hasMoved()){
					((King) lastPieceMoved).setJustCastled(false);
					((King) lastPieceMoved).setMoved(false);
				}
			}
			//ripristana il pezzo catturato
			if(dead != null){
				game.getAllPieces().add(dead);
				game.fillPieces();
				grid[move.getxNew()][move.getyNew()] = dead.getValueInTheboard();
				pieces[move.getxNew()][move.getyNew()] = dead;
				dead.setXcord(move.getxNew());
				dead.setYcord(move.getyNew());
			}
			else{
				grid[move.getxNew()][move.getyNew()] = 0;
				pieces[move.getxNew()][move.getyNew()] = dead;
			}
			//cambio turno
			game.changeSide(move.getPiece().isWhite());
		}
	}
	public Piece getLastPieceMoved(){
		return lastPieceMoved;
	}
	public Piece getPiece(int x,int y){
		return pieces[x][y];
	}
	public int[][] getGrid(){
		return grid;
	}
	public void setGrid(int[][] grid){
		this.grid = grid;
	}
	public int getXY(int x,int y){
		return grid[x][y];
	}
	public void setXY(int x,int y,int value){
		 grid[x][y] = value ;
	}
	//Serve a clonare la scacchiera per poi essere controllata
	public Board getNewBoard(){
		Board b = new Board(game);
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++) 
				if(this.getPiece(i, j) != null) 
					b.setPieceIntoBoard(i, j, this.getPiece(i, j).getClone());
		return b;
	}
	public void printBoard(){
		for(int i=0; i<8; i++){
			System.out.println("");
			for(int j=0; j<8; j++){
				System.out.print(grid[j][i] + " ");
			}
				
		}
	}
	public void addDeadPieces(Piece p){
		deadPieces.add(p);
    }
	public Stack<Piece> getDeadPieces(){
		return deadPieces;
    }
	public void addPromotedPieces(Piece p){
		promotedPieces.push(p);
	}
}
