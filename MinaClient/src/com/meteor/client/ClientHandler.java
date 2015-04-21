package com.meteor.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.meteor.entity.Person;


public class ClientHandler extends IoHandlerAdapter {

	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("Client Session Created");
		
		Person person = new Person();
		person.setName("张三");
		person.setAccount("test123@163.com");
		person.setPassword("123456");
		person.setAge(18);
		person.setAction(0);
		person.setSex(0);
		session.write(person);
		System.out.println("Client Send One");
	}

	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Client Session Opened");
		//super.sessionOpened(session);
	}

	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("client closed");
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("Client IDLE" + session.getIdleCount(status));
	}

	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if(message instanceof Person){
			Person person = (Person)message;
			
			System.out.println("Client Received " + person.toString());
			
			if (person.getAction() == 2) {
				System.out.println("Client Received close request");
				session.close(true);
			}
		}
	}

}
