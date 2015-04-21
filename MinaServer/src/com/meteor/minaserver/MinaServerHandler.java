package com.meteor.minaserver;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.meteor.entity.Person;

//使用适配器模式来简化iohandler带来的代码量
public class MinaServerHandler extends IoHandlerAdapter {

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		if(message instanceof Person){
			Person person = (Person)message;
			System.out.println("Client Say" + person.toString());
			
			
			if (person.getAction() == 2) {
				System.out.println("connection closed!");
				session.close(true);
				return;
			}
			System.out.println("Message written...");
			
			person.setName("李四");
			person.setAction(2);
			session.write(person);
		}
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("Server IDLE" + session.getIdleCount(status));
	}

}
