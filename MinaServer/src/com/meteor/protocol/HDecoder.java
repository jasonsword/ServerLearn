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
	 * ��ǰ��˵����������Ա�������˵,���鷳�������ݷ��͹����Ĺ�ģ,
	 * ��������Ϊ��,һ�� TCP ���ӽ���֮��,��ô��һ��ʱ��ͻ����������ݷ��͹���,
	 * Ҳ���� decode()�����ᱻ�� ������,�������������ͻ�ǳ��鷳��
	 * ��ô Mina ���Һ��ṩ�� CumulativeProtocolDecoder ��,�������Ͽ��Կ����ۻ��Ե�Э�������,
	 * Ҳ����˵ֻҪ�����ݷ��͹���,�����ͻ�ȥ ��ȡ����,Ȼ���ۻ����ڲ��� IoBuffer ������,
	 * ���Ǿ���Ĳ��(���ۻ��������������� ����Ϊ JAVA ����)��������� doDecode()�������,
	 * ʵ���� CumulativeProtocolDecoder ������ decode()�����ĵ��ñ�©������ʵ�ֵ� doDecode()������
	 * ����ִ�й���������ʾ:
	 * A. ��� doDecode()�������� true ʱ,
	 * CumulativeProtocolDecoder �� decode()�����������ж����Ƿ���doDecode()�����д��ڲ���IoBuffer��������ȡ������,
	 * ���û��,
	 * buffer.putString(smsContent, encoder);
	 * buffer.flip();
	 * ����׳��Ƿ���״̬�쳣,Ҳ������� doDecode()�������� true �ͱ�ʾ���Ѿ������˱�������
	 * (�൱����������һ����������Ϣ�Ѿ���ȡ���),
	 * ��һ��˵,Ҳ���Ǵ�ʱ������Ѿ����ѹ��ڲ��� IoBuffer������������(������������һ���ֽڵ�����)��
	 * �����֤��ͨ��,��ô CumulativeProtocolDecoder ���黺�������Ƿ�������δ��ȡ, 
	 * ����оͼ������� doDecode()����,û�о�ֹͣ�� doDecode()�����ĵ���,ֱ������ �����ݱ����塣
	 * 
	 * B. ����� doDecode()�������� false ʱ,CumulativeProtocolDecoder ��ֹͣ�� doDecode() �����ĵ���,
	 * ����ʱ����������ݻ���δ��ȡ���,�ͽ�����ʣ�����ݵ� IoBuffer �� �������浽 IoSession ��,
	 * �Ա���һ�����ݵ���ʱ���Դ� IoSession ����ȡ�ϲ������ ���ֱ�������ȫ����ȡ���,����� IoBuffer ��������
	 * �����֮,������Ϊ��ȡ���������Ѿ���������,��ô�ͷ��� true,����ͷ��� false��
	 * ��� CumulativeProtocolDecoder ��ʵ����Ҫ�Ĺ������ǰ�����������ݵ��ۻ�,��Ϊ��������Ǻܷ����ġ�
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
