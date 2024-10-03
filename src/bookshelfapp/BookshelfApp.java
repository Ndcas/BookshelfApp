package bookshelfapp;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import panels.Login;

public class BookshelfApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public BookshelfApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bookshelf");
        setSize(720, 480);
        setLocationRelativeTo(null);
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
