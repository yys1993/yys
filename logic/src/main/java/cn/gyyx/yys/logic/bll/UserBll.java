package cn.gyyx.yys.logic.bll;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.gyyx.yys.logic.beans.Log;
import cn.gyyx.yys.logic.beans.User;
import cn.gyyx.yys.logic.dao.LogDao;
import cn.gyyx.yys.logic.dao.UserDao;

public class UserBll {

	UserDao dao = new UserDao();
	public String insertUser(User user,String username) {
		String path;
		User user1 = dao.selectUser(username);
		if(user1 != null) {
			path = "home";
		}
		int code = dao.selectCode();
		user.setUser_id(code + 1);
		dao.insertUser(user);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		Log log = new Log();
		LogBll bll = new LogBll();
		int lcode = bll.selectCode();
		log.setCode(lcode+1);
		log.setUsername(user.getUsername());
		log.setDate(date);
		log.setWork("注册");
		bll.insertLog(log);
		path = "success";
		return path;
	}
	public User selectUser(String username) {
		return dao.selectUser(username);
	}
}
