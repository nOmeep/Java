import javax.swing.*;
import java.awt.*;

public class WindowClass extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public WindowClass() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 320);
        setLocation(screenSize.width / 2, screenSize.height / 4);
        add(new GameField()); // это вызывается первым, иначе будет белое окно
        setVisible(true);
    }
    public static void main(String[] args) {
        WindowClass window = new WindowClass();
    }
}
