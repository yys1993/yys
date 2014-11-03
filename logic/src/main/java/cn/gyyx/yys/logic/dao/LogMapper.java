package cn.gyyx.yys.logic.dao;

import cn.gyyx.yys.logic.beans.Log;


public interface LogMapper {

	void insertLog(Log log);
	Integer selectCode();
	
}
