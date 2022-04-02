package Chatroom;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame implements ActionListener{
	JButton login = new JButton("��¼");
	JButton register = new JButton("ע��");
	JTextField account_num = new JTextField("QQ��",24);
	JPasswordField password = new JPasswordField("����",24);
	JPanel south = new JPanel();
	JPanel center = new JPanel();
	
	//���캯��
	public Login() {
		//���ÿ��
		super("�����¼����");
		setSize(350, 150);  
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("1.jpeg")));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		password.setEchoChar((char)0);
		
		south.setLayout(new FlowLayout());
		south.add(login);
		c.add(south,"South");
		center.setLayout(new FlowLayout(FlowLayout.CENTER,5,10));
		center.add(account_num);
		center.add(password);
		c.add(center,"Center");
		
		//�����������ݿ�

		
		
		login.addActionListener(this);
		
		
		setVisible(true);
		while(true) {
			if (account_num.hasFocus()) {
				if(account_num.getText().equals("QQ��")) {
					account_num.setText("");
				}
			}
			else {
				if(account_num.getText().equals("")) {
					account_num.setText("QQ��");
				}
			}
			if (password.hasFocus()) {
				if(password.getText().equals("����")) {//��ȫ������
					password.setText("");
					password.setEchoChar('*');
				}
			}
			else {
				if(password.getText().equals("")) {
					password.setEchoChar((char)0);
					password.setText("����");
				}
			}
			
		}
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		int port = Integer.parseInt(account_num.getText());
		DatabaseLink databaseLink = DatabaseLink.getLink();
		String writePassword = password.getText();
		String truePassword = databaseLink.SelectPassword(port);
		if(writePassword.equals(truePassword)) {										//Ҫ��д��
			new UserInterface(port);
			setVisible(false);
		}
		else {
			JOptionPane.showMessageDialog(this, "�������");
		}
		
	}

	
	

}
