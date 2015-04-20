package com.meteor.mina01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {

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
		//TextLine��������Դ�������ı�����Ϣ���㲻��Ҫ�Լ���ʵ�ֱ���룩
		acceptor.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		//���ô�����������Ĵ��������Լ��������أ�ʵ�ֱ�Ҫ�Ĵ�����������ʹ��Ĭ�����ݼ���
		acceptor.setHandler(new TimeServerHandler());
		
		//ÿ�ζ�д��buffer��С
		acceptor.getSessionConfig().setReadBufferSize(2048);
		//����ʱ��ﵽ�ڵ��������Ӧ�Ļص�����λ��
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,	 10);
		//��ʼ������Ӧ�˿ڣ��Դ����Ӧ��socket��������
		acceptor.bind(new InetSocketAddress(PORT));
	}

}
