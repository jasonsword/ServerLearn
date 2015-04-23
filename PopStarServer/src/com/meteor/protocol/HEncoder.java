package com.meteor.protocol;

import java.nio.charset.Charset;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.meteor.minaserver.MessageBaseProtoBuf.MessageBase.Opcode;
import com.meteor.minaserver.RegisterProtoBuf.RegisterMsg;

public class HEncoder extends ProtocolEncoderAdapter {
	private final Charset charset;
	
	public HEncoder(Charset set){
		this.charset = set;
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		
		RegisterMsg.Builder builder = RegisterMsg.newBuilder();
		builder.getMsgbaseBuilder().setMajorVersion(0);
		builder.getMsgbaseBuilder().setMinurVersion(0);
		builder.getMsgbaseBuilder().setBuildVersion(1);
		builder.getMsgbaseBuilder().setCode(Opcode.REGISTER);
		
		builder.setAccount("test213@163.com");
		builder.setPassword("123456");
		builder.setName("张三");
		builder.setSex(0);
		builder.setTelephone(1234);
		
		RegisterMsg msg = builder.build();
		int size = msg.getSerializedSize();
		IoBuffer buffer = IoBuffer.allocate(size).setAutoExpand(true);
		buffer.put(msg.toByteArray());
		buffer.flip();
		
		out.write(buffer);
	}
	
}
