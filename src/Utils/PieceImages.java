package Utils;
import java.awt.image.BufferedImage;

public class PieceImages{
	public static class Rank{
		public static final int KING = 0;
		public static final int QUEEN = 1;
		public static final int BISHOP = 2;
		public static final int KNIGHT = 3;
		public static final int ROOK = 4;
		public static final int PAWN = 5;
	}
	public static class Simbol{
		public static String PAWN = "♟";
		public static String ROOK = "♜";
		public static String KNIGHT = "♞";
		public static String BISHOP = "♝";
		public static String QUEEN = "♛";
		public static String KING = "♚";
	}
	public static final String CHESS_IMAGE = "/Chess/Images/Chess_Piece_576x192.png";
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static BufferedImage[][] img;
	public PieceImages(){
		img = LoadSave.getPiecesImages();
	}
	public static BufferedImage getImage(int color, int rank){
        return img[color][rank];
    }
}
