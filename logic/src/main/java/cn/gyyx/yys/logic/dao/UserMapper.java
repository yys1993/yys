package cn.gyyx.yys.logic.dao;

import cn.gyyx.yys.logic.beans.Log;
import cn.gyyx.yys.logic.beans.User;

public interface UserMapper {

	User selectUser(String username);
	void insertUser(User user);
	int selectCode();
}
