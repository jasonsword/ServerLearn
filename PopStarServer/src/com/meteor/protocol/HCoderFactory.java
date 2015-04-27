package com.meteor.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

//使用适配器模式来简化iohandler带来的代码量
public class HCoderFactory implements ProtocolCodecFactory {

	private final HEncoder encoder;
	private final HDecoder decoder;

	public HCoderFactory(){
		this.encoder = new HEncoder();
		this.decoder = new HDecoder();
	}
	
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
