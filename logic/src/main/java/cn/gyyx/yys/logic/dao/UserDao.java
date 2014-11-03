package cn.gyyx.yys.logic.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.gyyx.yys.logic.dao.UserMapper;
import cn.gyyx.yys.logic.beans.User;

public class UserDao {
	static String resource = "mybatis-config.xml";
	SqlSessionFactory factory = null;
/*
 * 创建session
 * */
	static{
	try {
		InputStream	stream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder()
		.build(stream);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}
	public User selectUser(String username) {
		User user = null;
		SqlSession session = factory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			user = mapper.selectUser(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}
	
	public int selectCode() {
		int code = 0;
		SqlSession session = factory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			code = mapper.selectCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return code;
	}

    public void insertUser(User user) {
    	SqlSession session = factory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.insertUser(user);
			session.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
    }
}
