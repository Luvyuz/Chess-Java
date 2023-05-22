package Chess.Pieces;
import Chess.Board.Board;
import Chess.Board.Move;
import Utils.PieceImages;
import Utils.PieceImages.Rank;

public class King extends Piece {
	private boolean hasMoved = false;
	private boolean justCastled = false;
	private boolean justMoved = false;
	private Rook rook = null;
	public static final int RANK = Rank.KING;
	public King(int x, int y, boolean iswhite, Board board, int value){
		super(x, y, iswhite, board, value);
	}
	@Override
	public void intializeSide(int value){
		super.intializeSide(value);
		if(isWhite())
			image = PieceImages.getImage(PieceImages.WHITE, RANK);
		else
			image = PieceImages.getImage(PieceImages.BLACK, RANK);
	}
	@Override
	public boolean makeMove(int x, int y, Board board){
		Move move = new Move(xCord, yCord, x, y, this);
		if(!alive()) 
			return false;
		for(Move m: moves)
			if(m.compareTo(move) == 0){
				getRook(x, board);
				board.updatePieces(xCord, yCord, x, y,this);
				xCord = x;
				yCord = y;
				if(rook != null && !this.hasMoved && !rook.HasMoved()){
					if(x == rook.getXcord() - 1 || x == rook.getXcord() + 2){
						rook.castleDone(xCord, board);
						justCastled = true;
					}
				}else{
					justCastled = false;
				}
				hasMoved = true;
				return true;
			}
			return false;
	}
	@Override
	public boolean canMove(int x, int y, Board board){
		
		int i = Math.abs(xCord - x);
		int j = Math.abs(yCord - y);

		if( j == 1 && i == 1 || (i + j) == 1) {
			if(board.getPiece(x, y) == null) 
				return true;
			else 
				return board.getPiece(x, y).isWhite() != isWhite();				
		}
		getRook(x, board);
		if(rook != null && (rook.HasMoved() || this.hasMoved)) 
			return false;
		else if(rook != null){
			for(int k = xCord + 1; k < rook.getXcord(); k++){
				if(board.getPiece(k, yCord) != null) 
					return false;
				for(Move m: game.getAllEnemysMove()) 
					if((m.getxNew() == k || m.getxNew() == xCord) && m.getyNew() == yCord) 
						return false;
			}
			if(x == rook.getXcord() - 1 && y == yCord) 
				return true;
			for(int k = xCord - 1; k > rook.getXcord(); k--){
				if(board.getPiece(k, yCord) != null) 
					return false;
				for(Move m: game.getAllEnemysMove()) 
					if((m.getxNew() == k || m.getxNew() == xCord) && m.getyNew() == yCord) 
						return false;
			}
			if(x == rook.getXcord() + 2 && y == yCord) 
				return true;
		}
		return false;
	}
	//seleziona la torre per arroccare
	private void getRook(int x, Board board) {
		if(isWhite()){
			if(x >= xCord){
				if(board.getPiece(7, 7) != null && board.getPiece(7, 7) instanceof Rook)
					rook = (Rook) board.getPiece(7, 7);
			}else
				if(board.getPiece(0, 7) != null && board.getPiece(0, 7) instanceof Rook) 
					rook = (Rook) board.getPiece(0, 7);
		}else{
			if(x >= xCord){
				if(board.getPiece(7, 0) != null && board.getPiece(7, 0) instanceof Rook) 
					rook = (Rook) board.getPiece(7, 0);					
			}else
				if(board.getPiece(0,0) != null && board.getPiece(0,0) instanceof Rook) 
					rook = (Rook) board.getPiece(0, 0);						
		}
	}
	//controlla se il è sotto minaccia
	public boolean isInCheck(){
		for(Move m: game.getAllEnemysMove())
			if(m.getxNew() == xCord && m.getyNew() == yCord)
				return true;
		return false;
	}
	public boolean isJustCastled(){
		return justCastled;
	}
	public void setJustCastled(boolean justCastled){
		this.justCastled = justCastled;
    }
	public boolean hasMoved(){
		return hasMoved;
    }
	public void setMoved(boolean hasMoved){
		this.hasMoved = hasMoved;
	}
}
