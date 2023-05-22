package Utils;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import Chess.Frame.GameWindow;
public class LoadSave{

    public static BufferedImage getImage(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream(fileName);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	public static BufferedImage[][] getPiecesImages(){
		//restituisce una matrice di immagini dei pezzi
		BufferedImage img = LoadSave.getImage(PieceImages.CHESS_IMAGE);
		BufferedImage piecesImages[][] = new BufferedImage[2][6];
		for(int col = 0; col < piecesImages.length; col ++ )
			for(int row = 0; row < piecesImages[col].length; row ++)
				piecesImages[col][row] = img.getSubimage(row * GameWindow.TILE_SIZE, col * GameWindow.TILE_SIZE, GameWindow.TILE_SIZE, GameWindow.TILE_SIZE);
		return piecesImages;
	}
}
