package Utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CustomButton {
    private String name;
    private BufferedImage img;
    private int xPos, yPos;
    private int width, height;
    private boolean enabled;

    // Questo oggetto deve essere inserito all'interno di una lista
    public CustomButton(String name, String imgPath, int xPos, int yPos, int width, int height, boolean enabled) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.enabled = enabled;
        img = LoadSave.getImage(imgPath);
    }

    /*
     * Questo metodo deve essere richiamato all'interno del metodo paintComponent()
     * in cui per visualizzare le immagini farai for(CustomButton button : buttons)
     * button.displayOnPanel(g);
     */
    public void displayOnPanel(Graphics g) {
        g.drawImage(img, xPos, yPos, width, height, null);
    }

    public String getName() {
        return name;
    }

    public void disable() {
        enabled = false;
    }

    public void enable() {
        enabled = true;
    }

    /*
     * All'interno dell'ActionListener richiamerai il metodo facendo
     * for(CustomButton button : buttons)
     * button.isClicked(e.getX(),e.getY());
     * String btnName = button.getName();
     * 
     * switch(btnName){
     * case:
     * "nomi":
     * ecc.
     * break;
     */
    public boolean isClicked(int x, int y) {
        if ((x >= xPos && x <= xPos + width) && (y >= yPos && y <= yPos + height) && enabled)
            return true;
        return false;
    }
}
