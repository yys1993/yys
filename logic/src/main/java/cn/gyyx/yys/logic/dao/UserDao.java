package cn.gyyx.yys.logic.dao;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.gyyx.yys.logic.dao.UserMapper;
import cn.gyyx.yys.logic.beans.User;

public class UserDao {

	String resource = "mybatis-config.xml";
	InputStream stream;
	SqlSession session = null;
	public User selectUser(String username) {
		User user = null;
		try {
			stream = Resources.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder()
					.build(stream);
			session = factory.openSession();
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
		try {
			stream = Resources.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder()
					.build(stream);
			session = factory.openSession();
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
		try {
			stream = Resources.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder()
					.build(stream);
			session = factory.openSession();
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
