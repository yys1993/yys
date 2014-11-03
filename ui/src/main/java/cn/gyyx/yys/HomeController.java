package cn.gyyx.yys;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.gyyx.yys.logic.beans.Log;
import cn.gyyx.yys.logic.beans.User;
import cn.gyyx.yys.logic.bll.LogBll;
import cn.gyyx.yys.logic.bll.UserBll;
import cn.gyyx.yys.logic.dao.LogDao;
import cn.gyyx.yys.logic.dao.UserDao;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, Model model) {
		Map map = new HashMap();
		/*
		 * name用户名，password密码，hobby爱好，city城市
		 */
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String[] hobby = request.getParameterValues("hobby");
		String city = request.getParameter("city");
		map.put("username", username);
		map.put("password", password);
		map.put("hobby", hobby[0]);
		map.put("city", city);
		/*
		 * 向页面绑定数据
		 */
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setHobby(hobby[0]);
		user.setCity(city);
		UserBll bll = new UserBll();
		String path = bll.insertUser(user, username);
		model.addAttribute("map", map);
		return path;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			HttpServletRequest request,
			HttpServletResponse response,
			@CookieValue(value = "usernameCoo", required = false) String usernameCoo,
			@CookieValue(value = "passwordCoo", required = false) String passwordCoo,
			@CookieValue(value = "hobbyCoo", required = false) String hobbyCoo,
			@CookieValue(value = "user_idCoo", required = false) String user_idCoo,
			@CookieValue(value = "cityCoo", required = false) String cityCoo)
			throws IOException, TimeoutException, InterruptedException,
			MemcachedException {
		String path = "login";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		MemcachedClient client = new XMemcachedClient("192.168.6.195", 20000);
		User user = client.get(username);
		if (usernameCoo != null && usernameCoo.equals(username)) {
			return "denglu";
		} else {
			if (user == null) {
				UserBll bll = new UserBll();
				user = bll.selectUser(username);
				client.set(username, 3600, user);
				if (user == null) {
					return path;
				}
				
				if (password.equals(user.getPassword())) {
					path = "denglu";
				}
			}
		}
		if (username != null && username.equals(user.getUsername())) {
			if (password != null && password.equals(user.getPassword())) {
				path = "denglu";
			}
		}
		
		if (path.equals("denglu")) {
			Cookie cookie = new Cookie("usernameCoo", user.getUsername());
			Cookie cookie1 = new Cookie("passwordCoo", user.getPassword());
			Cookie cookie2 = new Cookie("hobbyCoo", user.getHobby());
			Cookie cookie3 = new Cookie("user_idCoo", user.getUser_id() + "");
			Cookie cookie4 = new Cookie("cityCoo", user.getCity());
			response.addCookie(cookie);
			response.addCookie(cookie1);
			response.addCookie(cookie2);
			response.addCookie(cookie3);
			response.addCookie(cookie4);
			cookie.setMaxAge(10);
			cookie1.setMaxAge(10);
			cookie2.setMaxAge(10);
			cookie3.setMaxAge(10);
			cookie4.setMaxAge(10);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(new Date());
			Log log = new Log();
			LogBll bll = new LogBll();
			int lcode = bll.selectCode();
			log.setCode(lcode+1);
			log.setUsername(username);
			log.setDate(date);
			log.setWork("登录");
			bll.insertLog(log);
		}
		return path;
	}

}
