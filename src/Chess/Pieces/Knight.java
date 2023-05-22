package Chess.Pieces;
import Chess.Board.Board;
import Utils.PieceImages;
import Utils.PieceImages.Rank;

public class Knight extends Piece{
	public static final int RANK = Rank.KNIGHT;
	public Knight(int x, int y, boolean iswhite, Board board, int value){
		super(x, y, iswhite, board, value);
	}
	public void intializeSide(int value){
		super.intializeSide(value);
		if(isWhite()) 
			image = PieceImages.getImage(PieceImages.WHITE, RANK);
		else 
			image = PieceImages.getImage(PieceImages.BLACK, RANK);
	}
	//puo muoversi solo ad "L"
	public boolean canMove(int x ,int y, Board board) {
		if((board.getPiece(x, y) != null && board.getPiece(x, y).isWhite() == isWhite()))
				return false;
		if(Math.abs(xCord - x) * Math.abs(yCord - y) == 2)
			return true;
		return false;
	}
}
