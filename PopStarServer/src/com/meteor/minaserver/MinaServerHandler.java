package com.meteor.minaserver;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.meteor.minaserver.MessageBaseProtoBuf.MessageBase.Opcode;
import com.meteor.minaserver.RegisterProtoBuf.RegisterMsg;
import com.meteor.websocket.WebSocketCodecPacket;

//使用适配器模式来简化iohandler带来的代码量
public class MinaServerHandler extends IoHandlerAdapter {

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {	
		IoBuffer buffer = (IoBuffer)message;
		
		byte[] b = new byte[buffer.limit()];  
    	buffer.get(b); 
    	
    	/*StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < b.length; i++)   
        {   
        	stringBuffer.append((char) b[i]);   
        }   
        System.out.println(stringBuffer.toString());   */
        
        String str222 = new String(b);
        System.out.println("客户端发来贺电：" + str222);;
		
		buffer.clear();
		buffer.flip();
		
		String str = "hello back 测试完成！ good!";
		byte[] strBytes = str.getBytes("UTF-8");
		
		int code = 1;
		int len = strBytes.length + 8;
		buffer.putInt(code);
		buffer.putInt(len);
		buffer.put(strBytes);
		
		/*WebSocketCodecPacket packet = WebSocketCodecPacket.buildPacket(buffer);*/
		
		/*RegisterMsg.Builder builder = RegisterMsg.newBuilder();
		builder.getMsgbaseBuilder().setMajorVersion(0);
		builder.getMsgbaseBuilder().setMinurVersion(0);
		builder.getMsgbaseBuilder().setBuildVersion(1);
		builder.getMsgbaseBuilder().setCode(Opcode.REGISTER);
		
		builder.setAccount("test213@163.com");
		builder.setPassword("123456");
		builder.setName("张三");
		builder.setSex(0);
		builder.setTelephone(1234);
		
		RegisterMsg msg = builder.build();
		buffer.put(msg.toByteArray());*/
		
		buffer.flip();
		session.write(buffer);
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		//System.out.println("Server IDLE" + session.getIdleCount(status));
	}

}
