package com.meteor.protocol;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.meteor.entity.Person;

public class HEncoder extends ProtocolEncoderAdapter {
	private final Charset charset;
	
	public HEncoder(Charset set){
		this.charset = set;
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		CharsetEncoder encoder = charset.newEncoder();
		Person person = (Person)message;
		
		String nameString = person.getName();
		
		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
		buffer.putString(nameString, encoder);
		buffer.flip();
		
		out.write(buffer);
	}
	
}
