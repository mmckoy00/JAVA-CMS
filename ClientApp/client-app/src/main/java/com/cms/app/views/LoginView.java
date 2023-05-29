package src.main.java.com.cms.app.views;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.regex.Pattern;

import javax.swing.SwingConstants;

import src.main.java.com.cms.app.client.Client;
import src.main.java.com.cms.app.entities.User;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JPanel;

public class LoginView extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtPassword;
	private JButton btnLogin,btnGuest;
	private JLabel lblErrorMsg,spinner;
	private JPanel main;
	private Client client;
	MainFrame frame;
	


	public LoginView(MainFrame frame) {
		this.frame = frame;
		components();
		btnLoginActionPerformed();
		btnGuestActionPerformed();
	}
	
	private void components() {
		getContentPane().setBackground(SystemColor.control);
		setForeground(new Color(51, 0, 204));
		setBackground(new Color(204, 204, 204));
		setBounds(100, 10, 313, 260);
		getContentPane().setLayout(null);
		
		main = new JPanel();
		main.setBounds(0, 0, 297, 230);
		getContentPane().add(main);
		main.setLayout(null);
		
		spinner = new JLabel("");
		spinner.setHorizontalAlignment(SwingConstants.CENTER);
		spinner.setIcon(new ImageIcon(LoginView.class.getResource("/src/main/resource/image/spinner1.gif")));
		spinner.setBounds(184, 11, 89, 52);
		main.add(spinner);
		
		JLabel lblUserIcon = new JLabel("");
		lblUserIcon.setIcon(new ImageIcon(LoginView.class.getResource("/src/main/resource/image/black-user-icon.png")));
		lblUserIcon.setBounds(21, 78, 47, 36);
		main.add(lblUserIcon);
		
		JLabel lblLockIcon = new JLabel("");
		lblLockIcon.setIcon(new ImageIcon(LoginView.class.getResource("/src/main/resource/image/black-lock-icon.png")));
		lblLockIcon.setBounds(21, 125, 47,  36);
		main.add(lblLockIcon);
		
		txtId = new JTextField();
		txtId.setBounds(68, 94, 192, 20);
		main.add(txtId);
		txtId.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(68, 129, 192, 20);
		main.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblTitle = new JLabel("LOGIN");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Yu Gothic UI", Font.BOLD, 17));
		lblTitle.setBounds(10, 22, 83, 31);
		main.add(lblTitle);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(48, 184, 89, 20);
		main.add(btnLogin);
		
		lblErrorMsg = new JLabel("");
		lblErrorMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorMsg.setForeground(Color.RED);
		lblErrorMsg.setBounds(68, 74, 202, 14);
		main.add(lblErrorMsg);
		
		btnGuest = new JButton("Guest");	
		btnGuest.setEnabled(false);
		btnGuest.setBounds(171, 184, 89, 20);
		main.add(btnGuest);
		
	}
	
	private void btnGuestActionPerformed() {
		btnGuest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnGuest) {
					User user = new User("guest", "Guest", "guest");
					frame.isLoggedIn(user, client);
					setVisible(false);
				}
			}
			
		});
	}
	
	
	/***
	 * login button action
	 */
	
	private void btnLoginActionPerformed() {
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				String password = txtPassword.getText();
				checkUser(id, password);
			}
			
		});
	}
	
	/***
	 * validate user credentials
	 * @param id
	 * @param password
	 */
	private void checkUser(String id, String password) {

		this.spinner.setVisible(true);
		if(id.isEmpty() || password.isEmpty() || password.length() < 4) {
			lblErrorMsg.setText("Please valid enter Id or Password");
			this.spinner.setVisible(false);
			txtId.setText("");
			txtPassword.setText("");
			
		}else {
			
			if(isUserIdValid(id)) {
				this.client = new Client();
				boolean isRunning = client.pingServer();
			
				
				if(isRunning==true) {
					// Check if the server is running before accessing it 
					this.client.sendCommand("login");
					this.client.sendUserCredentials(id, password);
					Object response = this.client.authenticationResponse();
						
					if(response.equals("is logged in")) {
						lblErrorMsg.setText("User already logged in");
						txtId.setText("");
						txtPassword.setText("");
					}
					
					if(response.equals("none")) {
						lblErrorMsg.setText("ID or Password is incorrect");
						txtId.setText("");
						txtPassword.setText("");
						this.spinner.setVisible(false);
					}else {
						User user = (User) response;
						this.frame.isLoggedIn(user,client);
						this.setVisible(false);
					}
						
				}else {
					javax.swing.JOptionPane.showMessageDialog(this, "We couldn't complete your request", "We're sorry", JOptionPane.ERROR_MESSAGE);
				}
				
			}else {
				lblErrorMsg.setText("Id must be 7 digits");
				txtId.setText("");
				txtPassword.setText("");
				this.spinner.setVisible(false);
			}
		}
	}
	
	
/***
 * validates checks entered id 
 * @param id
 * @return
 */
	private boolean isUserIdValid(String id) {
		java.util.regex.Pattern pattern = Pattern.compile("^[0-9]{7}");
		java.util.regex.Matcher matcher = pattern.matcher(id);
		if(matcher.matches()) {
			return true;
		}
		return false; 
	}
}



