package com.meteor.socketserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.meteor.protocol.HCoderFactory;
import com.meteor.websocket.WebSocketCodecFactory;

public class SocketServer {

	private static final int PORT = 9029;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//监听链接到来的对象，基于TCP/IP，所以使用socket
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		//添加filter，把二进制数据或者是协议相关的数据转换成一个消息对象，反过来亦然
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		//自定义编解码
		acceptor.getFilterChain().addLast("text", 
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		//acceptor.getFilterChain().addLast("person", 
		//		new ProtocolCodecFilter(new HCoderFactory(Charset.forName("UTF-8"))));
		//acceptor.getFilterChain().addLast("person", 
		//		new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		
		//设置处理链接请求的处理器，自己进行重载，实现必要的处理，其它部分使用默认内容即可
		acceptor.setHandler(new SocketServerHandler());
		
		//每次读写的buffer大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//空闲时间达到节点后会产生相应的回调，单位秒
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,	 10);
		//开始监听相应端口，以处理对应的socket链接请求
		acceptor.bind(new InetSocketAddress(PORT));
	}

}

