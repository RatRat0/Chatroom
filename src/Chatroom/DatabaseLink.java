package Chatroom;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseLink {
	private String url = "jdbc:mysql://127.0.0.01:3306/account";//连接数据库
	private String username = "root";
	private String userpassword;
	private String password = "3511811a/";
	static DatabaseLink databaseLink = null;
	private Connection con = null;
	private PreparedStatement stat = null;
	private ResultSet rs = null;
	private DatabaseLink() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static DatabaseLink getLink() {
		if (databaseLink == null) {
			databaseLink = new DatabaseLink();
		}
		return databaseLink;
	}
	
	
	//得到用户姓名
	public String SelectUsername(int port) {
		String sql = "select username from account where port = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1,String.valueOf(port));
			rs = stat.executeQuery();
			while(rs.next()) {
				String username = rs.getString(1);
				return username;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int Selectport(String username) {
		String sql = "select port from account where username = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1,username);
			rs = stat.executeQuery();
			while(rs.next()) {
				int port  = rs.getInt(1);
				return port;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;				
	}
	

	public String SelectPassword(int port) {
		String sql = "select password from account where port = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1,String.valueOf(port));
			rs = stat.executeQuery();
			while(rs.next()) {
				String userpassword = rs.getString(1);
				return userpassword;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String[] SelectAllUsername() {
		String sql = "select username from account";
		String[] allUsername = new String[getCount()];
		int i = 0;
		try {
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();
			while(rs.next()) {
				String username = rs.getString(1);
				allUsername[i] = username;
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allUsername;
	}
	public String[] SelectAllUsername(int port) {
		String sql = "select username from account where port != ?";
		String[] allUsername = new String[getCount()-1];
		int i = 0;
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1, ""+port);
			rs = stat.executeQuery();
			while(rs.next()) {
				String username = rs.getString(1);
				allUsername[i] = username;
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allUsername;
	}
	public Integer[] SelectAllPort() {
		String sql = "select port from account";
		Integer[] allPort = {};
		int i = 0;
		try {
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();
			while(rs.next()) {
				int port = rs.getInt(1);
				allPort[i] = port;
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allPort;
	}
	public int getCount() {
		String sql = "select count(*) from account";
		try {
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();
			while(rs.next()) {
				int count = rs.getInt(1);
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public String [] getRecord(String chatRoomNum) {
		String [] records = new String [getRecordCount(chatRoomNum)];
		String sql = "select record from chatrecord where chatroom = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1, chatRoomNum);
			rs = stat.executeQuery();
			int i = 0;
			while(rs.next()) {
				String record  = rs.getString(1);
				records [i] = record;
				i++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return records;
	}
	public int getRecordCount(String chatRoomNum) {
		String sql = "select count(*) from chatrecord where chatroom = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1, chatRoomNum);
			rs = stat.executeQuery();
			while(rs.next()) {
				int count = rs.getInt(1);
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public String [] getSender (String chatRoomNum) {
		String [] senders = new String [getRecordCount(chatRoomNum)];
		String sql = "select sender from chatrecord where chatroom = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1, chatRoomNum);
			rs = stat.executeQuery();
			int i = 0;
			while(rs.next()) {
				String sender  = rs.getString(1);
				senders [i] = sender;
				i++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return senders;
	}
	public void deleteRecord(String ChatRoomNum) {
		String sql = "delete from chatrecord where chatroom = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1, ChatRoomNum);
			stat.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void insertRecord(String record, String chatRoomNum, String sender) {
		String sql = "insert into chatrecord (record,chatroom,sender,time) values(?,?,?,0)";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(3, sender);
			stat.setString(2, chatRoomNum);
			stat.setString(1, record);
//			stat.setString(3, sender);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
