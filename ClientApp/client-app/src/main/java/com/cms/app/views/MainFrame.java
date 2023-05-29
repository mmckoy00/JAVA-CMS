package src.main.java.com.cms.app.views;

//import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import src.main.java.com.cms.app.client.Client;
import src.main.java.com.cms.app.entities.User;

import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JDesktopPane;
//import java.awt.Window.Type;
import java.awt.Toolkit;
import java.util.Map;


public class MainFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Client client;
	private JDesktopPane desktopPane;
	private JMenu signInMenu,logoutMenu, profileMenu, chatMenu;
	private LoginView loginFrame;
	private ChatView chatFrame;
	private JLabel lblUsername;
	private JMenuBar mainMenuBar;
	static Map<String, User> result;
	
	


	/**
	 * Create the frame.
	 */
	
	public MainFrame() {
		this.components();
		this.loginFrame = new LoginView(this);	
		
	}
	
	private void components() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/src/main/resource/image/logo.png")));
		setResizable(false);
		setLocationRelativeTo(null);
		setType(Type.POPUP);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 564, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		mainMenuBar = new JMenuBar();
		mainMenuBar.setBounds(0, 0, 548, 22);
		contentPane.add(mainMenuBar);
		
		signInMenu = new JMenu("Login");
		signInMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			 public void mouseClicked(java.awt.event.MouseEvent evt) {
				 signInMenuActionPerformed(evt);
	            }
		});
		
		
		profileMenu = new JMenu("Profile");
		/*profileMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			 public void mouseClicked(java.awt.event.MouseEvent evt) {
				profileMenuActionPerformed(evt);
	            }
		});
		*/
		
		logoutMenu = new JMenu("Logout");
		logoutMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			 public void mouseClicked(java.awt.event.MouseEvent evt) {
				logoutMenuActionPerformed(evt);
	            }
		});
		
		chatMenu = new JMenu("Chat with Advisor");
		chatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			 public void mouseClicked(java.awt.event.MouseEvent evt) {
				chatMenuActionPerformed(evt);
	            }
		});
		chatMenu.setIcon(new ImageIcon(MainFrame.class.getResource("/src/main/resource/image/chat-icon.png")));
		
		
		mainMenuBar.add(signInMenu);
		lblUsername = new JLabel();
		
		desktopPane = new JDesktopPane();
		desktopPane.setDesktopManager(new NoDragDesktopManager());
		desktopPane.setBounds(0, 22, 548, 394);
		contentPane.add(desktopPane);
		
		
		
		
		
		JLabel bg = new JLabel("");
		bg.setBounds(0, 0, 548, 394);
		desktopPane.add(bg);
		bg.setIcon(new ImageIcon(MainFrame.class.getResource("/src/main/resource/image/blue-background.jpg")));
		
		
	}
	
	private void signInMenuActionPerformed(java.awt.event.MouseEvent e) {
			if(loginFrame.isVisible() == false) {
				loginFrame.setVisible(true);
				loginFrame.putClientProperty("dragMode", "fixed");
				desktopPane.add(loginFrame);
				loginFrame.toFront();
		}
	}
	
	
	private void chatMenuActionPerformed(java.awt.event.MouseEvent e) {
		
		if(chatFrame.isVisible() == false) {
			chatFrame.setVisible(true);
			chatFrame.putClientProperty("dragMode", "fixed");
			chatFrame.toFront();
		}
}
	
	private void profileMenuActionPerformed(java.awt.event.MouseEvent e) {
		if(chatFrame.isVisible() == false) {
			chatFrame.setVisible(true);
			chatFrame.putClientProperty("dragMode", "fixed");
			desktopPane.add(chatFrame);
			chatFrame.toFront();
		}
}

	private void logoutMenuActionPerformed(java.awt.event.MouseEvent e) {
		int selection = javax.swing.JOptionPane.showConfirmDialog(rootPane, "Logging out?", "Logout Choice", JOptionPane.YES_NO_OPTION);
		if(selection == JOptionPane.YES_OPTION) {
			dispose();
			client.closeConnection();
		}
}
	
	
	
	
/***
 * 	Set up for user
 * @param user
 */
	public void isLoggedIn(User user, Client client) {
		this.client = client;
		lblUsername.setText("logged as: "+user.getUsername() +" ");
		mainMenuBar.remove(signInMenu);
		mainMenuBar.add(profileMenu);
		profileMenu.setEnabled(false);
		mainMenuBar.add(logoutMenu);
		

		
		getViewBasedOnRole(user.getRole());
		this.chatFrame = new ChatView(this, user);
		desktopPane.add(chatFrame);
		
		mainMenuBar.add(Box.createHorizontalGlue());
		mainMenuBar.add(lblUsername);
	}
	
	
	
	private void getViewBasedOnRole(String role) {
		switch(role) {
		case "student":
			this.setTitle("Student");
			mainMenuBar.add(chatMenu);
			break;
		case "advisor":
			this.setTitle("Student Service Advisor");
			chatMenu.setText("Connect with Students");
			mainMenuBar.add(chatMenu);
			break;
			
		case "supervisor":
			this.setTitle("Student Service Supervisor");
			break;
		case "guest":
			this.setTitle("Guest");
			break;
		default:
			mainMenuBar.add(signInMenu);
			mainMenuBar.remove(profileMenu);
			mainMenuBar.remove(logoutMenu);
			
			lblUsername.setText("");
			mainMenuBar.remove(Box.createHorizontalGlue());
			mainMenuBar.remove(lblUsername);
			javax.swing.JOptionPane.showMessageDialog(null, "Error: Unknown Role");
			this.dispose();
			
		}
	}
	
	
	public Client getClient() {
		return this.client;
	}

// @author mmckoy00

    class NoDragDesktopManager extends DefaultDesktopManager
    {

		private static final long serialVersionUID = 1L;

		public void beginDraggingFrame(JComponent f)
        {
            if (!"fixed".equals(f.getClientProperty("dragMode")))
                super.beginDraggingFrame(f);
        }

        public void dragFrame(JComponent f, int newX, int newY)
        {
            if (!"fixed".equals(f.getClientProperty("dragMode")))
                super.dragFrame(f, newX, newY);
        }

        public void endDraggingFrame(JComponent f)
        {
            if (!"fixed".equals(f.getClientProperty("dragMode")))
                super.endDraggingFrame(f);
        }
    }
}

