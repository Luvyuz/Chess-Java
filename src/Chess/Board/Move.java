package Chess.Board;
import Chess.Pieces.Piece;

public class Move{
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
}
