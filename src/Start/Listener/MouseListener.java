package Start.Listener;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import Chess.Game.Game;
import Start.GameWindow.MenuStart;
import Utils.CustomButton;

public class MouseListener implements MouseInputListener {
    MenuStart pnl;
    boolean sel = false;

    public MouseListener(MenuStart pnl) {
        this.pnl = pnl;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // System.out.println("x: " + x + " y: " + y);
        LinkedList<CustomButton> buttonsList;
        buttonsList = pnl.getButtons();
        CustomButton button = null;

        for (CustomButton cb : buttonsList) {
            if (cb.isClicked(x, y))
                button = cb;
        }
        if (button != null)
            switch (button.getName()) {
                case "PvP":
                    new Game(pnl.getLanguage());
                    pnl.closeWindow();
                    break;
                case "PvA":
                    if (pnl.getLanguage() == 0)
                        JOptionPane.showMessageDialog(null, "To implement");
                    else
                        JOptionPane.showMessageDialog(null, "Da implementare");
                    break;
                case "Language":
                    for (CustomButton cb : buttonsList) {
                        if (cb.getName().equalsIgnoreCase("English") || cb.getName().equalsIgnoreCase("Italiano"))
                            cb.enable();
                        else
                            cb.disable();
                    }
                    pnl.setLanguageMenu(true);
                    pnl.repaint();
                    break;
                case "Exit":
                    pnl.closeWindow();
                    break;
                case "English":
                case "Italiano":
                    for (CustomButton cb : buttonsList) {
                        if (cb.getName().equalsIgnoreCase("English") || cb.getName().equalsIgnoreCase("Italiano"))
                            cb.disable();
                        else
                            cb.enable();
                    }
                    pnl.setLanguage(button.getName());
                    pnl.setLanguageMenu(false);
                    pnl.repaint();
                    break;
                default:
                    break;
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}