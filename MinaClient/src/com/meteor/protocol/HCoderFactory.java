package com.meteor.protocol;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class HCoderFactory implements ProtocolCodecFactory {

	private final HEncoder encoder;
	private final HDecoder decoder;
	
	public HCoderFactory(){
		this(Charset.defaultCharset());
	}
	
	public HCoderFactory(Charset set){
		this.encoder = new HEncoder(set);
		this.decoder = new HDecoder(set);
	}
	
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
