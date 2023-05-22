package Utils;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Stack;

import javax.imageio.ImageIO;

import Chess.Board.Move;
import Chess.Frame.GameWindow;
public class LoadSave{
	public static final String LOG_PATH = "src/Logs/";
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
	//salva i movimenti in un file
    public static void saveData(Stack<Move> moves){
		//crea un file con la data della fine della partita
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
        String path = LOG_PATH + date + ".txt";
		//scrittura su file dei movimenti effettuati
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            for(Move move : moves)
                bw.write(move.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
