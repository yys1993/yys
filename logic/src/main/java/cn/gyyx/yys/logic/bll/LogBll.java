package cn.gyyx.yys.logic.bll;

import cn.gyyx.yys.logic.beans.Log;
import cn.gyyx.yys.logic.dao.LogDao;

public class LogBll {

	LogDao dao = new LogDao();
	public void insertLog(Log log) {
		dao.insertLog(log);
	}
	public int selectCode() {
		return dao.selectCode();
	}
}
