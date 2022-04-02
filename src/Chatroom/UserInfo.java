package Chatroom;

import java.net.SocketAddress;

public class UserInfo {
	private String username = "";
	private String password = "";
	//该用户的Multicastsocket 所在的IP和端口
	private int port;
	private UserInterface userinterface = new UserInterface(port);
	
	//构造函数
	public UserInfo(String username , String password , int port) {
		this.username = username;
		this.password = password;
		this.port = port;
	}
	
	//各成员函数的get和password(password无get)
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
