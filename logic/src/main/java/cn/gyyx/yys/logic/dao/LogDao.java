package cn.gyyx.yys.logic.dao;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.gyyx.yys.logic.beans.Log;
import cn.gyyx.yys.logic.beans.User;

public class LogDao {


	String resource = "mybatis-config.xml";
	InputStream stream;
	SqlSession session = null;
	public int selectCode() {
		int code = 0;
		try {
			stream = Resources.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder()
					.build(stream);
			session = factory.openSession();
			LogMapper mapper = session.getMapper(LogMapper.class);
			if(mapper.selectCode() == null) {
				code = 0;
			}else{
			code = mapper.selectCode();
		     } 
			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return code;
	}
	public void insertLog(Log log) {
		try {
			stream = Resources.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder()
					.build(stream);
			session = factory.openSession();
			LogMapper mapper = session.getMapper(LogMapper.class);
			mapper.insertLog(log);
			session.commit();
			System.out.println("dddd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
    }
}
