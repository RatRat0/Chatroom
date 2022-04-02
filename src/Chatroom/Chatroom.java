package Chatroom;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javax.swing.*;
/*
 * 
 * 
 * ����3�������������������¼
 * ��������boolean����
 * ��һ����������֮ǰ���Ķ�����Ȼ��һ���Եķ�
 * 
 * ����4���Ͽ����Ӳ��������ж�����
 * 
 * ����5����֪��������˭���͵�
 * ���Դ���һ���û���������
 * 
 * ����6�����ܴ���ͼƬ���ļ�
 * 
 * ����7������ֻ���ڵ����ڴ�������
 * 
 * 
 * 
 * ����
 * 			1������һ�����ɱ������ݿⴴ����������ÿ���û��Ķ˿ں���Ϣ�������ڷ�����������ͨ�������ѡ������Ķ���
 * 					���ݿ⻹�кܴ�ķ���ȥ���(�и�����������Ҫ���)
 * 2���ܴ���ͼ�񡢴���Ƶ
 * 		
 * 3������ʾ�ն���Ϣ�Ϳ����ն˸�����
 *						 4����ģ��д�ɺ��������ڵ���
 * 									���
 * 							5����һ���Ǹ���ַ��Ϣ
 * 									�㲥���ĳһ����ַ	a multicast address
 * 			6��������������ò���Ҫ����һ��
 * 				�ƺ�����ˣ���������
 * 7������Ҫ�Ŀ⺯���Ͱ�д���ĵ����������ˣ����һ��Ҫ��ס��
 * 
 * 8���ܹ������Զ�������Ϣ�Ĺ���
 * 		���Ӹ�æµ�ȵȹ���
 * 		���ף��������Զ��ظ�����ɶ
 * 
 * 		9�������õ�һ��С�顢ȫ�����ѵ�ͨѶģʽ
 * 				����1���Լ�����Ϣ����Ҫ��������
 * 				����2�����򷴹���������
 * 				����3����Ϣ���മ������
 * 
 * 10���������ݿ�Ĺ���	
 * 		��Ҫע��
 * 
 * 		11����������
 * 				��Swing.tree�Ȼ����
 * 
 * 12��������connect������
 */


public class Chatroom extends JFrame implements Runnable,ActionListener{
	//swing������
	private JButton send = new JButton("����");
	private JButton delete = new JButton("���");
	private JButton connect = new JButton("����");
	private JTextArea ince = new JTextArea(2,10);
	private JTextArea inced = new JTextArea(10,40);
	private JScrollPane inces = new JScrollPane(ince);
	private JScrollPane inceds = new JScrollPane(inced);
	private Boolean state = true;
	Thread thread;
	
	//socket����
	InetAddress group;
	MulticastSocket socket;
	int port;
	int ports[];
	boolean getMessage = true;
	static DatabaseLink link = DatabaseLink.getLink();
	
	public Chatroom(Boolean state,int port,int...ports) {
		//������
		super(link.SelectUsername(port)+"��������");
		this.port = port;
		this.ports =ports;
		this.state = state;
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("1.jpeg")));
		setSize(450, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Container c = getContentPane();
		ince.setLineWrap(true);
		inced.setEditable(false);
		inced.setLineWrap(true);
		
		//�������
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
		p2.add(send);
		p2.add(connect);
		p2.add(delete);

		
		c.add(p2,"South");
		c.add(inceds,"North");
		c.add(inces,"Center");
		
		
		
		//��Ӽ�����Ϊ
		send.addActionListener(this);
		connect.addActionListener(this);
		delete.addActionListener(this);
		
		setVisible(true);
	}
	//���ü�������
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton)e.getSource();
		//��յ����
		if(jb == delete) {
			ince.setText("");
		}
					
		//���͵����
		if(jb == send) {
				send(port, ports);					
						
		}
					
				//���ӵ����
			if(jb == connect) {
					if(jb.getText().equals("����")) {
						thread = new Thread(this);
						if(!thread.isAlive()) {
							thread = new Thread(this);
							getMessage = true;
						}
						String chatRoomNum = Function.combineword(port, ports);
						String records[] = link.getRecord(chatRoomNum);
						String senders[] = link.getSender(chatRoomNum);
						for(int i = 0;i<records.length;i++) {
							String sender = link.SelectUsername(Integer.parseInt(senders[i]));
							inced.append(sender+":"+records[i]+"\n");
						}
						connect(port);
						thread.start();
						jb.setText("�Ͽ�����");
						
					}
					else if(jb.getText().equals("�Ͽ�����")) {
						getMessage = false;
						jb.setText("����");
					}
			}
					//������������ò���Ҫ����һ��
						
						
		}
				
	public void notConnect() {
		JDialog jd = new JDialog(this,"������ʾ");
		jd.setSize(30,30);
		jd.setLocationRelativeTo(null);
		JLabel jl = new JLabel("�޷�����ָ���ķ�����");
		jd.add(jl);
		jd.setVisible(true);
	}
	
	//�����л����Ϣ
	public void run() {
		 while (true) {
			 //�߳��еõ���Ϣ����packet
			 if(true) {
				 DatagramPacket packet;
				 try {
					 receive(group);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		 }
		
	}
	
	
	public void send(int port , int...ports) {
		String s = port+"k";
		String chatroomNum = Function.combineword(port, ports);
		s = s + chatroomNum + ":" + ince.getText();
		byte message[] = s.getBytes();
		try {
			group = InetAddress.getByName("224.1.0.255");
			for(int port1:ports) {
				sendPacket(message,port1);
				}
			sendPacket(message,port);
//			DatabaseLink link = DatabaseLink.getLink();
			link.insertRecord(ince.getText(), chatroomNum, ""+port);
//			connect(port);
			ince.setText("");
			ince.requestFocus();
		} catch (IOException e1) {
			e1.printStackTrace();
			}
	}
	public void sendPacket(byte[] message, int port) throws IOException {
		DatagramPacket packet = new DatagramPacket(message, message.length,group,port);
		socket.send(packet);
		connect(port);
	}
	
//		æµ���ֵ�Ӧ����
//	public void send(String s, int...ports) {
//		byte message[] = s.getBytes();
//		try {
//			group = InetAddress.getByName("224.1.0.255");
//			for(int port1:ports) {
//				connect(port1);
//				DatagramPacket packet1 = new DatagramPacket(message, message.length,group,port1);
//				socket.send(packet1);
//				}
//			socket = new MulticastSocket(port);
//			socket.joinGroup(group);
//			ince.setText("");
//			ince.requestFocus();
//		} catch (IOException e1) {
//		e1.printStackTrace();
//			}
//	}
	
	
	//����
	public void connect(int port) {
		try {
			group = InetAddress.getByName("224.1.0.255");
			socket = new MulticastSocket(port);
			socket.joinGroup(group);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

}
	public void receive(InetAddress group) throws IOException {
		byte data[] = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length,group,port);
		socket.receive(packet);
		String oriMessage = new String(packet.getData(),0,packet.getLength());
		int messageIndex = oriMessage.indexOf(":");
		int hostIndex = oriMessage.indexOf("a");
		String message = oriMessage.substring(messageIndex+1);
		String host = oriMessage.substring(0,hostIndex-1);
		int [] newPorts = Function.combine(port,ports);
		String chatNum = oriMessage.substring(hostIndex,messageIndex); 
		String chatRoomNum = Function.combineword(port, ports);
		if(chatRoomNum.equals(chatNum)) {
			String hostname = link.SelectUsername(Integer.parseInt(host));
			inced.append(hostname+":"+message+"\n");
		}
		
	}
		


	

}
