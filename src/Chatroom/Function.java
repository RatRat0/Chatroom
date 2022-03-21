 package Chatroom;

import java.net.DatagramPacket;

public class Function {
	public static int [] combine(int port, int [] ports) {
		int [] newPorts = new int [ports.length+1];
		int i = 0;
		while(true) {
			if (i<ports.length && ports[i]<port) {
				newPorts[i]=ports[i];
				i++;
			}
			else {
				newPorts[i] = port;
				break;
			}
		}
		while(i < ports.length) {
			newPorts[i+1] = ports[i];
			i++;
		}
		return newPorts;
	}
	public static String combineword(int port , int [] ports) {
		int [] chatRoomNum1 = Function.combine(port, ports);
		String chatRoomNum = "";
		for(int i = 0;i<chatRoomNum1.length;i++) {
			chatRoomNum += "a"+chatRoomNum1[i];
		}
		chatRoomNum += "a";
		return chatRoomNum;
	}

}
