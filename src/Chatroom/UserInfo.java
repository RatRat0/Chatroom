package Chatroom;

import java.net.SocketAddress;

public class UserInfo {
	private String username = "";
	private String password = "";
	//���û���Multicastsocket ���ڵ�IP�Ͷ˿�
	private int port;
	private UserInterface userinterface = new UserInterface(port);
	
	//���캯��
	public UserInfo(String username , String password , int port) {
		this.username = username;
		this.password = password;
		this.port = port;
	}
	
	//����Ա������get��password(password��get)
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPort() {
		return port;
	}
	public UserInterface getUserInterface() {
		return userinterface;
	}
	

	
}
