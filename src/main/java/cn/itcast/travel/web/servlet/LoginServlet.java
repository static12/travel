package cn.itcast.travel.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取参数
		String checkCode=request.getParameter("checkcode");
		String CHECKCODE_SERVER=(String) request.getSession().getAttribute("CHECKCODE_SERVER");
		ResultInfo info=new ResultInfo();
		if(!checkCode.equalsIgnoreCase(CHECKCODE_SERVER)) {
			//验证码错误的情况
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
			//把结果集对象传到Html
			ObjectMapper mapper=new ObjectMapper();
			String json=mapper.writeValueAsString(info);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			//结束逻辑
			return ;
		}
		//从service层获取结果集对象
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UserService uservice=new UserServiceImpl();
		info=uservice.login(username, password);
		if(info.isFlag()) {
			//登录成功，存储用户名到session域
			request.getSession().setAttribute("username", username);
		}
		//把结果集对象传到Html
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(info);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
