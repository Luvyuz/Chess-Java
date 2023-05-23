package Chess.Board;
import Chess.Pieces.Piece;
import Utils.PieceImages.Rank;
import Utils.PieceImages.Simbol;

public class Move implements Comparable<Move>{

	private int xOld, yOld, xNew, yNew;
	private Piece piece;
	public Move(int xOld, int yOld, int xNew, int yNew, Piece piece){
		this.xOld = xOld;
		this.yOld = yOld;
		this.xNew = xNew;
		this.yNew = yNew;
		this.piece = piece;
	}
	public int getxOld(){
		return xOld;
	}
	public void setxOld(int xOld){
		this.xOld = xOld;
	}
	public int getyOld(){
		return yOld;
	}
	public void setyOld(int yOld){
		this.yOld = yOld;
	}
	public int getxNew(){
		return xNew;
	}
	public void setxNew(int xNew){
		this.xNew = xNew;
	}
	public int getyNew(){
		return yNew;
	}
	public void setyNew(int yNew){
		this.yNew = yNew;
	}
	public Piece getPiece(){
		return piece;
	}
	public void setPiece(Piece piece){
		this.piece = piece;
	}
	@Override
	public int compareTo(Move o) {
		if(xNew == o.getxNew() && yNew == o.getyNew())
			return 0;
		return -1;
	}
	public boolean equals(Object o){
		Move otherM = (Move) o;
		if(this.getxNew() == otherM.getxNew() && this.getyNew() == otherM.getyNew() && this.getxOld() == otherM.getxOld() && this.getyOld() == otherM.getyOld())
			return true;
		return false;
	}
	public String moveToString(){
		String move = "";
		switch(piece.getRank()){
			case Rank.PAWN:
			    move = Simbol.PAWN;
				break;
			case Rank.ROOK:
                move = Simbol.ROOK;
                break;
            case Rank.KNIGHT:
			    move = Simbol.KNIGHT;
				break;
			case Rank.BISHOP:
                move = Simbol.BISHOP;
                break;
            case Rank.QUEEN:
                move = Simbol.QUEEN;
                break;
			case Rank.KING:
                move = Simbol.KING;
                break;
		}
		switch(xNew){
			case 0:
			    move += "a";
				break;
			case 1:
				move += "b";
                break;
            case 2:
			    move += "c";
				break;
			case 3:
				move += "d";
				break;
			case 4:
				move += "e";
				break;
			case 5:
				move += "f";
				break;
			case 6:
				move += "g";
				break;
			case 7:
				move += "h";
				break;
		}
        switch(yNew){
			case 0:
			    move += "8";
				break;
			case 1:
				move += "7";
				break;
			case 2:
				move += "6";
				break;
			case 3:
				move += "5";
				break;
			case 4:
				move += "4";
				break;
			case 5:
				move += "3";
				break;
			case 6:
				move += "2";
				break;
			case 7:
				move += "1";
				break;
			
		}
		return move;
	}
	@Override
	public String toString(){
		return (piece.isWhite() ? "W-" : "B-") + moveToString() + "\n";
	}
}
