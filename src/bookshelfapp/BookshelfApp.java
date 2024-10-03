package bookshelfapp;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import panels.Login;

public class BookshelfApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public BookshelfApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bookshelf");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 720) / 2;
        int y = (screenSize.height - 480) / 2;
        setBounds(x, y, 720, 480);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        //Thêm các panel ở đây
        Login loginPanel = new Login(cardLayout, cardPanel);
        cardPanel.add(loginPanel, "login");
        add(cardPanel);
        //Mặc định show login
        cardLayout.show(cardPanel, "login");
        
    }
    
    public static void main(String[] args) {
        BookshelfApp app = new BookshelfApp();
        app.setVisible(true);
    }
    
}
