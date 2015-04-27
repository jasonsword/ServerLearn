package com.meteor.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class HEncoder extends ProtocolEncoderAdapter {
	
	public HEncoder(){

	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		out.write(message);
	}
	
}
