package Chatroom;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.JobAttributes.DefaultSelectionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UserInterface extends JFrame implements ActionListener{
//	Integer userList_1[] = {6666,7777,8888,9999};//更改一下便于实验
	JLabel username = new JLabel("用户名");//后期需要修改
	JPanel north = new JPanel();
	JButton startChat = new JButton("开始聊天");
	JButton receive = new JButton("接收聊天");
	JPanel south = new JPanel();
	String states[] = {"在线"};
	JComboBox state = new JComboBox(states);
	int port = 6666;
	DatabaseLink databaseLink = DatabaseLink.getLink();
//	String allUsername[] = databaseLink.SelectAllUsername(port);
	JList <String> usernameList;
//	JScrollPane usernameLists = new JScrollPane(usernameList);
	
	public UserInterface(int port) {
	super();
	this.port = port;
	String allUsername[] = databaseLink.SelectAllUsername(port);
	this.usernameList = new JList<String>(allUsername);
	JScrollPane usernameLists = new JScrollPane(usernameList);
	String username1 = databaseLink.SelectUsername(port);
	username.setText(username1);
	Container c = getContentPane();
	setSize(300, 500);
	setLocation(900, 100);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	usernameList.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	
	//数据库在Swing中的体现
	
	setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("1.jpeg")));
	north.setLayout(new FlowLayout(FlowLayout.CENTER));
	username.setFont(new Font("宋体",Font.BOLD,25));
	north.add(username);
	north.add(state);
	c.add(north,"North");
	c.add(usernameLists,"Center");
	south.setLayout(new FlowLayout(FlowLayout.CENTER));
	south.add(startChat);
	south.add(receive);
	c.add(south,"South");
	
	startChat.addActionListener(this);
	receive.addActionListener(this);
	
	
	
	setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton jb =(JButton)e.getSource();
		String getState = (String)state.getSelectedItem();
		Boolean state1 = true;
		if(getState.equals("在线")) {
			state1 = true;
		}
		else if(getState.equals("忙碌")) {
			state1 = false;
		}
		Object[] usernamesObject = usernameList.getSelectedValues();
		int ports[] = new int[usernamesObject.length];
		for (int i=0;i<usernamesObject.length;i++) {
			ports[i] = databaseLink.Selectport((String)usernamesObject[i]);
			
		}
		if(jb.equals(startChat)) {
			new Chatroom(state1,port,ports);
		}
		else if(jb.equals(receive)) {
			new Chatroom(state1,port);
		}
		
	}


}
