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
 * 问题3：不能做到保存聊天记录
 * 设置两个boolean来做
 * 放一个东西保存之前发的东西，然后一股脑的发
 * 
 * 问题4：断开连接不能立刻中断连接
 * 
 * 问题5：不知道内容是谁发送的
 * 可以创建一个用户的类来做
 * 
 * 问题6：不能传播图片等文件
 * 
 * 问题7：还是只能在电脑内传递数据
 * 
 * 
 * 
 * 需求：
 * 			1、建立一个表（由本地数据库创立），控制每个用户的端口和信息，类似于服务器，可以通过这个来选择聊天的对象
 * 					数据库还有很大的方面去解决(有个代码问题需要解决)
 * 2、能传输图像、传视频
 * 		
 * 3、能显示终端信息和控制终端个数，
 *						 4、把模块写成函数，便于调控
 * 									完成
 * 							5、查一下那个地址信息
 * 									广播组的某一个地址	a multicast address
 * 			6、读表操作和设置操作要绑在一块
 * 				似乎完成了？？？？？
 * 7、把需要的库函数和包写成文档，发给别人（这个一定要记住）
 * 
 * 8、能够设置自动发送消息的功能
 * 		增加个忙碌等等功能
 * 		进阶：能设置自动回复的是啥
 * 
 * 		9、能设置单一、小组、全部好友的通讯模式
 * 				问题1：自己的信息内容要接收两次
 * 				问题2：方向反过来的现象
 * 				问题3：信息互相串的现象
 * 
 * 10、好友数据库的管理	
 * 		需要注册
 * 
 * 		11、界面问题
 * 				用Swing.tree等会更好
 * 
 * 12、多想想connect的问题
 */


public class Chatroom extends JFrame implements Runnable,ActionListener{
	//swing面板组成
	private JButton send = new JButton("发送");
	private JButton delete = new JButton("清空");
	private JButton connect = new JButton("连接");
	private JTextArea ince = new JTextArea(2,10);
	private JTextArea inced = new JTextArea(10,40);
	private JScrollPane inces = new JScrollPane(ince);
	private JScrollPane inceds = new JScrollPane(inced);
	private Boolean state = true;
	Thread thread;
	
	//socket部分
	InetAddress group;
	MulticastSocket socket;
	int port;
	int ports[];
	boolean getMessage = true;
	static DatabaseLink link = DatabaseLink.getLink();
	
	public Chatroom(Boolean state,int port,int...ports) {
		//面板设计
		super(link.SelectUsername(port)+"的聊天室");
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
		
		//网格设计
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
		p2.add(send);
		p2.add(connect);
		p2.add(delete);

		
		c.add(p2,"South");
		c.add(inceds,"North");
		c.add(inces,"Center");
		
		
		
		//添加监听行为
		send.addActionListener(this);
		connect.addActionListener(this);
		delete.addActionListener(this);
		
		setVisible(true);
	}
	//设置监听动作
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton)e.getSource();
		//清空的情况
		if(jb == delete) {
			ince.setText("");
		}
					
		//发送的情况
		if(jb == send) {
				send(port, ports);					
						
		}
					
				//连接的情况
			if(jb == connect) {
					if(jb.getText().equals("连接")) {
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
						jb.setText("断开连接");
						
					}
					else if(jb.getText().equals("断开连接")) {
						getMessage = false;
						jb.setText("连接");
					}
			}
					//读表操作和设置操作要绑在一块
						
						
		}
				
	public void notConnect() {
		JDialog jd = new JDialog(this,"错误提示");
		jd.setSize(30,30);
		jd.setLocationRelativeTo(null);
		JLabel jl = new JLabel("无法连接指定的服务器");
		jd.add(jl);
		jd.setVisible(true);
	}
	
	//进程中获得信息
	public void run() {
		 while (true) {
			 //线程中得到消息就收packet
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
	
//		忙碌部分的应答语
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
	
	
	//连接
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
