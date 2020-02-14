package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
	
	private UserService uservice=new UserServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//先获取数据
		request.setCharacterEncoding("utf-8");
		HashMap map=new HashMap(request.getParameterMap());
		User user=new User();
		//先对比验证码
		String checkCode=request.getParameter("check");
		String CHECKCODE_SERVER=(String) request.getSession().getAttribute("CHECKCODE_SERVER");
		if(!checkCode.equalsIgnoreCase(CHECKCODE_SERVER)) {
			ResultInfo info=new ResultInfo();
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
			String json=writeValueAsString(info);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			return;
		}
		if(map.size()>0) {
			//封装数据
			try {
				BeanUtils.populate(user, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("封装后的数据："+user);
			ResultInfo info=uservice.registerUser(user);
			String json=writeValueAsString(info);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

	private String writeValueAsString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(obj);				
		System.out.println("json:"+json);
		return json;
	}
}
