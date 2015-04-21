package com.meteor.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.meteor.protocol.HCoderFactory;

public class MyClient {

	private static final int PORT = 9029;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IoConnector connector = new NioSocketConnector();
		//设置过滤规则
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		//connector.getFilterChain().addLast("person", 
		//		new ProtocolCodecFilter(new HCoderFactory(Charset.forName("UTF-8"))));
		connector.getFilterChain().addLast("person", 
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		//设置连接处理器
		connector.setHandler(new ClientHandler());
		
		//默认超时时间1分钟
		//connector.setConnectTimeoutMillis(100);
		
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", PORT));	
		//等待连接创建完成
		cf.awaitUninterruptibly();
		//等待连接传输完成
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		//释放连接
		connector.dispose();
	}

}
