package Start.GameWindow;

import javax.swing.*;

public class StartWindow extends JFrame {
    private MenuStart start;

    public StartWindow() {
        super("Chess");
        start = new MenuStart(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(start);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
