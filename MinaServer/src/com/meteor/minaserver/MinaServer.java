package com.meteor.minaserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.meteor.protocol.HCoderFactory;

public class MinaServer {

	private static final int PORT = 9029;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//�������ӵ����Ķ��󣬻���TCP/IP������ʹ��socket
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		//���filter���Ѷ��������ݻ�����Э����ص�����ת����һ����Ϣ���󣬷�������Ȼ
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		//�Զ�������
		//acceptor.getFilterChain().addLast("person", 
		//		new ProtocolCodecFilter(new HCoderFactory(Charset.forName("UTF-8"))));
		acceptor.getFilterChain().addLast("person", 
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		
		//���ô�����������Ĵ��������Լ��������أ�ʵ�ֱ�Ҫ�Ĵ�����������ʹ��Ĭ�����ݼ���
		acceptor.setHandler(new MinaServerHandler());
		
		//ÿ�ζ�д��buffer��С
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//����ʱ��ﵽ�ڵ��������Ӧ�Ļص�����λ��
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,	 10);
		//��ʼ������Ӧ�˿ڣ��Դ����Ӧ��socket��������
		acceptor.bind(new InetSocketAddress(PORT));
	}

}

