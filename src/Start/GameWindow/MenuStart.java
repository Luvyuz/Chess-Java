package Start.GameWindow;

import javax.swing.*;

import Start.Listener.MouseListener;
import Utils.CustomButton;
import Utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class MenuStart extends JPanel {
    private final static String[][] START_MENU_IMAGE_NAME = {
            { "/start/img/title/eng/title.png", "/start/img/eng/two_players.png", "/start/img/eng/vsCPU.png",
                    "/start/img/eng/language.png", "/start/img/eng/exit.png" },
            { "/start/img/title/ita/title.png", "/start/img/ita/due_giocatori.png", "/start/img/ita/vsComputer.png",
                    "/start/img/ita/lingua.png", "/start/img/ita/esci.png" } };
    private final static String BACKGROUND_IMAGE_NAME = "/start/img/background/img.png";
    private final static String[] LANGUAGE_IMAGE_NAME = { "/start/img/eng/english.png", "/start/img/ita/italiano.png" };
    private final static String[] TITLE_STRINGS = { "Chess", "Scacchi" };

    public static BufferedImage BACKGROUND_IMAGE;
    private CustomButton[][] btn_start_menu;
    private CustomButton[] btn_language_menu;
    private LinkedList<CustomButton> customButtons;
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private int language = 0; // 0 if it's equal to english, 1 if it's equal to italian
    private boolean languageSelector = false;
    private JFrame menuWindow;

    public MenuStart(JFrame menuWindow) {
        this.menuWindow = menuWindow;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new MouseListener(this));
        initClasses();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawTitle(g);
        if (languageSelector)
            drawlanguageSelector(g);
        else
            drawButtons(g);
    }

    public void drawBackground(Graphics g) {
        g.drawImage(BACKGROUND_IMAGE, 0, -350, WIDTH, 1000, null);
    }

    public void drawTitle(Graphics g) {
        btn_start_menu[language][0].displayOnPanel(g);
    }

    public void drawButtons(Graphics g) {
        for (int i = 1; i < 5; i++)
            btn_start_menu[language][i].displayOnPanel(g);
    }

    public void drawlanguageSelector(Graphics g) {
        drawBackground(g);
        drawTitle(g);
        for (int i = 0; i < 2; i++)
            btn_language_menu[i].displayOnPanel(g);
    }

    public void initClasses() {
        BACKGROUND_IMAGE = LoadSave.getImage(BACKGROUND_IMAGE_NAME);
        btn_language_menu = new CustomButton[2];
        btn_start_menu = new CustomButton[2][5];
        customButtons = new LinkedList<>();
        for (int i = 0; i < 2; i++)
            btn_start_menu[i][0] = new CustomButton(TITLE_STRINGS[i], START_MENU_IMAGE_NAME[i][0], 100, 100, 300, 150,
                    false);

        btn_start_menu[0][1] = new CustomButton("PvP", START_MENU_IMAGE_NAME[0][1], 75, 300, 150, 50, true);
        btn_start_menu[0][2] = new CustomButton("PvA", START_MENU_IMAGE_NAME[0][2], 275, 300, 150, 50, true);
        btn_start_menu[0][3] = new CustomButton("Language", START_MENU_IMAGE_NAME[0][3], 75, 400, 150, 50, true);
        btn_start_menu[0][4] = new CustomButton("Exit", START_MENU_IMAGE_NAME[0][4], 275, 400, 150, 50, true);

        btn_start_menu[1][1] = new CustomButton("PvP", START_MENU_IMAGE_NAME[1][1], 75, 300, 150, 50, true);
        btn_start_menu[1][2] = new CustomButton("PvA", START_MENU_IMAGE_NAME[1][2], 275, 300, 150, 50, true);
        btn_start_menu[1][3] = new CustomButton("Language", START_MENU_IMAGE_NAME[1][3], 75, 400, 150, 50, true);
        btn_start_menu[1][4] = new CustomButton("Exit", START_MENU_IMAGE_NAME[1][4], 275, 400, 150, 50, true);

        btn_language_menu[0] = new CustomButton("English", LANGUAGE_IMAGE_NAME[0], 75, 300, 150, 50, false);
        btn_language_menu[1] = new CustomButton("Italiano", LANGUAGE_IMAGE_NAME[1], 275, 300, 150, 50, false);

        for (int i = 0; i < 2; i++) {
            for (CustomButton cb : btn_start_menu[i])
                customButtons.add(cb);
            customButtons.add(btn_language_menu[i]);
        }
    }

    public void setLanguage(String languageSelected) {
        switch (languageSelected) {
            case "English":
                language = 0;
                break;
            case "Italiano":
                language = 1;
                break;
        }
    }

    public void setLanguageMenu(boolean languageSelector) {
        this.languageSelector = languageSelector;
    }

    public boolean getLanguageSelector() {
        return languageSelector;
    }

    public LinkedList<CustomButton> getButtons() {
        return customButtons;
    }

    public int getLanguage() {
        return language;
    }

    public void closeWindow() {
        menuWindow.dispose();
    }

}