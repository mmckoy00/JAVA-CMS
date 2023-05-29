package src.main.java.com.cms.app.views;


import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import src.main.java.com.cms.app.entities.User;

import java.awt.Font;
import java.util.Map;
import java.awt.Color;
import javax.swing.JList;

public class ChatView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JTextArea readMessage, writeMessage;
	private JButton sendMessage;
	private JList<String> list;
	static Map<String, User>allUsers;
	private DefaultListModel<String >listModel;
	private User user;
	

	public ChatView(MainFrame frame, User user) {
		
		this.mainFrame = frame;
		this.user = user;
		this.initializeComponents();
	}
		
	private void initializeComponents()	{
		
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setBounds(50, 0, 449, 365);
		getContentPane().setLayout(null);
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setBounds(28, 36, 87, 249);
		//JScrollPane scrollPane = new JScrollPane(list);
		getContentPane().add(list);

		sendMessage = new JButton("Send");
		sendMessage.setBackground(new Color(255, 255, 255));
		sendMessage.setToolTipText("Send Message");
		sendMessage.setBounds(310, 301, 101, 23);
		getContentPane().add(sendMessage);
		
		JScrollPane scrollPaneTypeMessage = new JScrollPane();
		scrollPaneTypeMessage.setBounds(139, 249, 272, 36);
		getContentPane().add(scrollPaneTypeMessage);
		
		writeMessage = new JTextArea();
		writeMessage.setColumns(2);
		writeMessage.setWrapStyleWord(true);
		writeMessage.setLineWrap(true);
		scrollPaneTypeMessage.setViewportView(writeMessage);
		
		JScrollPane scrollPaneViewMessage = new JScrollPane();
		scrollPaneViewMessage.setBounds(139, 25, 272, 193);
		getContentPane().add(scrollPaneViewMessage);
		
		readMessage = new JTextArea();
		readMessage.setWrapStyleWord(true);
		readMessage.setEditable(false);
		readMessage.setLineWrap(true);
		scrollPaneViewMessage.setViewportView(readMessage);
		
		JLabel lblAdvisorsTitle = new JLabel("Advisors");
		lblAdvisorsTitle.setFont(new Font("Calibri", Font.BOLD, 13));
		lblAdvisorsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdvisorsTitle.setBounds(28, 11, 87, 14);
		getContentPane().add(lblAdvisorsTitle);
		
		JLabel lblEnterMessage = new JLabel("Enter Message");
		lblEnterMessage.setBounds(139, 229, 124, 14);
		getContentPane().add(lblEnterMessage);

		
		JLabel wallpaper = new JLabel("");
		wallpaper.setIcon(new ImageIcon(ChatView.class.getResource("/src/main/resource/image/peakpx.jpg")));
		wallpaper.setBounds(0, 0, 433, 335);
		getContentPane().add(wallpaper);
		
		
	}
	
	
	
	private void createAdvisorList() {

		for(Map.Entry<String, User>activeUser: allUsers.entrySet()) {
			 if (!activeUser.getValue().getUsername().equals(user.getUsername())) {
				listModel.addElement(activeUser.getValue().getUsername());
			}
		}
	}
	
	
	private void updateList() {
		mainFrame.getClient().sendCommand("Get Users");
		allUsers =  mainFrame.getClient().getActiveUsers();
	}
/*
	
	private void btnSendMsgActionPerformed() {
		sendMsg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == sendMsg) {
					String msg = writeMsg.getText();
					
					readMsg.append("You:\n"+msg+"\n");
					writeMsg.setText("");
				}
				
			}
			
		});
	}
	*/
}
