package com.meteor.mina01;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

//使用适配器模式来简化iohandler带来的代码量
public class TimeServerHandler extends IoHandlerAdapter {

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String string = message.toString();
		System.out.println(string);
		if (string.trim().equalsIgnoreCase("quit")) {
			session.close(true);
			return;
		}
		
		Date date = new Date();
		session.write(date.toString());
		System.out.println("Message written...");
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("IDLE" + session.getIdleCount(status));
	}

}
