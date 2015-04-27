package com.meteor.socketserver;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.meteor.entity.Person;
import com.meteor.minaserver.MessageBaseProtoBuf.MessageBase.Opcode;
import com.meteor.minaserver.RegisterProtoBuf.RegisterMsg;
import com.meteor.websocket.WebSocketCodecPacket;

//使用适配器模式来简化iohandler带来的代码量
public class SocketServerHandler extends IoHandlerAdapter {

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {	
		/*IoBuffer buffer = (IoBuffer)message;
		
		byte[] b = new byte[buffer.limit()];  
    	buffer.get(b); 
    	
    	StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < b.length; i++)   
        {   
        	stringBuffer.append((char) b [i]);   
        }
        
		
		buffer.clear();
		String str = "hello back 你妹";
		buffer.put(str.getBytes("UTF-8"));
		buffer.flip();
		session.write(buffer);
		buffer.free();*/
		
		System.out.println((String)message);   
		String str = "hello back 你妹";
		session.write(str);
		
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		//System.out.println("Server IDLE" + session.getIdleCount(status));
	}

}
