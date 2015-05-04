package com.meteor.databse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteor.entity.User;
import com.meteor.minaserver.RegisterProtoBuf.RegisterMsg;

public final class HibernateTest {

	private static final Logger logger = LoggerFactory.getLogger(HibernateTest.class); 
	private static final Configuration cfg = new Configuration().configure();
	private static final StandardServiceRegistry sr = new StandardServiceRegistryBuilder()
			.applySettings(cfg.getProperties()).build();
	private static final SessionFactory sf = cfg.buildSessionFactory(sr);
	
	/*public static void main(String[] args) {
		generateUserData();
	}*/
	
	public static void resumeMessage(RegisterMsg msg){
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();  
		
		try { 
			User user = new User();
			user.setUsername(msg.getAccount());
			user.setPassword(msg.getPassword());
			user.setName(msg.getName());
			user.setAge(msg.getAge());
			user.setSex(msg.getSex());
			user.setIdcard(msg.getIdcard());
			user.setTelephone(msg.getTelephone());
			user.setLocked(false);	
			
			logger.info("register msg : " + user.toString());
			
			session.save(user);  
			t.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
		
		logger.info("register complete!!!");
	}
	
	public static void generateUserData() {
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();  
		
		try { 
			for(int i = 0; i < 100; i++){
				User user = new User();
				user.setUsername("test" + i);
				user.setPassword(""+ i);
				user.setName("张三");
				user.setAge(i);
				user.setSex(i%2);
				user.setIdcard("500111222333444" + String.format("%03d", i));
				user.setTelephone("138666677" + String.format("%02d", i));
				user.setLocked(i%2 == 1);	
				session.save(user);  
			}
			t.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			t.rollback();
		} finally {
			session.close();
		}
		
		logger.info("register complete!!!");
		
	}

}
