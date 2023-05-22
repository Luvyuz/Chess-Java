package Chess.Pieces;
import Chess.Board.Board;
import Utils.PieceImages;
import Utils.PieceImages.Rank;

public class Queen extends Piece {
	public static final int RANK = Rank.QUEEN;
	public Queen(int x, int y, boolean iswhite, Board board, int value){
		super(x, y, iswhite, board, value);
	}
	public void intializeSide(int value){
		super.intializeSide(value);
		if(isWhite()) 
			image = PieceImages.getImage(PieceImages.WHITE, RANK);
		else 
			image = PieceImages.getImage(PieceImages.BLACK, RANK);
	}
	@Override
	public boolean canMove(int x, int y, Board board){
		if(board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == isWhite()) 
			return false;
		if(Math.abs(x - xCord) == Math.abs(y - yCord)) 
			return (movesDiagonial(x, y, board));
		if( x == xCord  || y == yCord ) 
			return movesStraight(x, y, board);
		return false;
	}
	//movimento diagonale e controlla che non ci siano pezzi in mezzo
	public boolean movesStraight(int x,int y, Board board){
		if(x == xCord && (y < yCord)){
			for(int i = yCord - 1; i > y; i--) 
				if(board.getXY(x, i) != 0) 
					return false;
			return true;
		}
		if(x == xCord && (y > yCord)){
			for(int i = yCord + 1; i < y;i ++) 
				if(board.getXY(x, i) != 0) 
					return false;
			return true;
		}
		if(y == yCord && (x > xCord)){
			for(int i = xCord + 1; i < x; i++) 
				if(board.getXY(i, y) != 0) 
					return false;
			return true;
		}
		if(y == yCord && (x < xCord)){
			for(int i = xCord - 1; i > x; i--) 
				if(board.getXY(i, y) != 0) 
					return false;
			return true;
		}
		return false;
	}
	//movimento dritto e controlla che non ci siano pezzi in mezzo
	public boolean movesDiagonial(int x,int y, Board board){
		if(x > xCord && y > yCord){
			int j = yCord + 1;
			for(int i = xCord + 1; i < x; i++) {
				if(board.getXY(i, j) != 0) 
					return false;
				j++;
			}
		}else if(x < xCord && y < yCord){
			int j = yCord - 1;
			for(int i = xCord - 1; i > x; i--){
				if(board.getXY(i, j) != 0) 
					return false;
				j--;
			}
			
		}else if(x > xCord && y < yCord){
			int j = yCord - 1;
			for(int i = xCord + 1; i < x; i++){
				if(board.getXY(i, j) != 0) 
					return false;
				j--;
			}
		}else if(x < xCord && y > yCord){
			int j = yCord + 1;
			for(int i = xCord - 1; i > x; i--){
				if(board.getXY(i, j) != 0) 
					return false;
				j++;
			}
		}
		return true;
	}
	@Override
	public int getRank(){
		return RANK;
	}
}
