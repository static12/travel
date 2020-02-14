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

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取参数
		request.setCharacterEncoding("utf-8");
		String code=request.getParameter("activeCode");
		UserService uservice=new UserServiceImpl();
		ResultInfo info=uservice.active(code);
		response.setContentType("text/html;charset=utf-8");
		if(info.isFlag()) {
			//如果激活成功，跳转到登录页面
			response.sendRedirect(request.getContextPath()+"/login.html");
		}else {
			//激活失败，显示错误信息
			response.getWriter().write(info.getErrorMsg());
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

