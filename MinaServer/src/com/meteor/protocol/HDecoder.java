package com.meteor.protocol;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.meteor.entity.Person;

public class HDecoder extends CumulativeProtocolDecoder {

	private final Charset charset;
	
	public HDecoder(Charset set){
		this.charset = set;
	}
	
	
	/*
	 * 但前面说过解码器相对编码器来说,最麻烦的是数据发送过来的规模,
	 * 以聊天室为例,一个 TCP 连接建立之后,那么隔一段时间就会有聊天内容发送过来,
	 * 也就是 decode()方法会被往 复调用,这样处理起来就会非常麻烦。
	 * 那么 Mina 中幸好提供了 CumulativeProtocolDecoder 类,从名字上可以看出累积性的协议解码器,
	 * 也就是说只要有数据发送过来,这个类就会去 读取数据,然后累积到内部的 IoBuffer 缓冲区,
	 * 但是具体的拆包(把累积到缓冲区的数据 解码为 JAVA 对象)交由子类的 doDecode()方法完成,
	 * 实际上 CumulativeProtocolDecoder 就是在 decode()反复的调用暴漏给子类实现的 doDecode()方法。
	 * 具体执行过程如下所示:
	 * A. 你的 doDecode()方法返回 true 时,
	 * CumulativeProtocolDecoder 的 decode()方法会首先判断你是否在doDecode()方法中从内部的IoBuffer缓冲区读取了数据,
	 * 如果没有,
	 * buffer.putString(smsContent, encoder);
	 * buffer.flip();
	 * 则会抛出非法的状态异常,也就是你的 doDecode()方法返回 true 就表示你已经消费了本次数据
	 * (相当于聊天室中一个完整的消息已经读取完毕),
	 * 进一步说,也就是此时你必须已经消费过内部的 IoBuffer缓冲区的数据(哪怕是消费了一个字节的数据)。
	 * 如果验证过通过,那么 CumulativeProtocolDecoder 会检查缓冲区内是否还有数据未读取, 
	 * 如果有就继续调用 doDecode()方法,没有就停止对 doDecode()方法的调用,直到有新 的数据被缓冲。
	 * 
	 * B. 当你的 doDecode()方法返回 false 时,CumulativeProtocolDecoder 会停止对 doDecode() 方法的调用,
	 * 但此时如果本次数据还有未读取完的,就将含有剩余数据的 IoBuffer 缓 冲区保存到 IoSession 中,
	 * 以便下一次数据到来时可以从 IoSession 中提取合并。如果 发现本次数据全都读取完毕,则清空 IoBuffer 缓冲区。
	 * 简而言之,当你认为读取到的数据已经够解码了,那么就返回 true,否则就返回 false。
	 * 这个 CumulativeProtocolDecoder 其实最重要的工作就是帮你完成了数据的累积,因为这个工作是很烦琐的。
	 * 
	 * */
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		CharsetDecoder decoder = charset.newDecoder();
		
		String name = in.getString(decoder);
		
		Person person = new Person();
		person.setName(name);
		
		out.write(person);
		
		return false;
	}

}
