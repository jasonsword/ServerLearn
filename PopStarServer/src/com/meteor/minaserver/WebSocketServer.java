package com.meteor.minaserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class WebSocketServer {
	public static final int PORT = 9029;

	public static void main(String[] args) throws IOException {

		WebSocketIoHandler handler = new WebSocketIoHandler();

		NioSocketAcceptor acceptor = new NioSocketAcceptor();
	        
		acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );   
		
		acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
		
		acceptor.setHandler(handler);

		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("Step 1 server is listenig at port " + PORT);
	}
	

}
