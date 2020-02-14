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

@WebServlet("/checkUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取参数
		request.setCharacterEncoding("utf-8");
		String username=request.getParameter("username");
		//查询service层，得到及结果集对象
		UserService uservice=new UserServiceImpl();
		ResultInfo info=uservice.checkUsername(username);
		//把结果集对象转换为json字符串
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(info);
		//把json字符串返回给html页面
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

