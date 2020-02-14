package cn.itcast.travel.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{
   //TODO 改变访问路径
   private UserService uservice=new UserServiceImpl();
	
   public void active(HttpServletRequest request,HttpServletResponse response) 
		   throws ServletException, IOException{
	 //获取参数
	request.setCharacterEncoding("utf-8");
	String code=request.getParameter("activeCode");
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
   public void checkCode(HttpServletRequest request,HttpServletResponse response) 
		   throws ServletException, IOException{
	       //服务器通知浏览器不要缓存
	 		response.setHeader("pragma","no-cache");
	 		response.setHeader("cache-control","no-cache");
	 		response.setHeader("expires","0");
	 		
	 		//在内存中创建一个长80，宽30的图片，默认黑色背景
	 		//参数一：长
	 		//参数二：宽
	 		//参数三：颜色
	 		int width = 80;
	 		int height = 30;
	 		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	 		
	 		//获取画笔
	 		Graphics g = image.getGraphics();
	 		//设置画笔颜色为灰色
	 		g.setColor(Color.GRAY);
	 		//填充图片
	 		g.fillRect(0,0, width,height);
	 		
	 		//产生4个随机验证码，12Ey
	 		String checkCode = getCheckCode();
	 		//将验证码放入HttpSession中
	 		request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);
	 		
	 		//设置画笔颜色为黄色
	 		g.setColor(Color.YELLOW);
	 		//设置字体的小大
	 		g.setFont(new Font("黑体",Font.BOLD,24));
	 		//向图片上写入验证码
	 		g.drawString(checkCode,15,25);
	 		
	 		//将内存中的图片输出到浏览器
	 		//参数一：图片对象
	 		//参数二：图片的格式，如PNG,JPG,GIF
	 		//参数三：图片输出到哪里去
	 		ImageIO.write(image,"PNG",response.getOutputStream());
	 	}
	 	/**
	 	 * 产生4位随机字符串 
	 	 */
	 	private String getCheckCode() {
	 		String base = "0123456789ABCDEFGabcdefg";
	 		int size = base.length();
	 		Random r = new Random();
	 		StringBuffer sb = new StringBuffer();
	 		for(int i=1;i<=4;i++){
	 			//产生0到size-1的随机值
	 			int index = r.nextInt(size);
	 			//在base字符串中获取下标为index的字符
	 			char c = base.charAt(index);
	 			//将c放入到StringBuffer中去
	 			sb.append(c);
	 		}
	 		return sb.toString();
   }
   public void checkUsername(HttpServletRequest request,HttpServletResponse 
		   response) throws ServletException, IOException{
	        //获取参数
	 		request.setCharacterEncoding("utf-8");
	 		String username=request.getParameter("username");
	 		//查询service层，得到及结果集对象
	 		ResultInfo info=uservice.checkUsername(username);
	 		//把结果集对象转换为json字符串
	 		String json=writeValueAsString(info);
	 		//把json字符串返回给html页面
	 		response.setContentType("application/json;charset=utf-8");
	 		response.getWriter().write(json);
}
   public void exit(HttpServletRequest request,HttpServletResponse 
		   response) throws ServletException, IOException{
	       //退出，销毁session域
	 		HttpSession session=request.getSession();
	 		session.invalidate();
	 		//销毁cookie
	 		Cookie[] cs=request.getCookies();
	 		for(Cookie c:cs) {
	 			if(c.getName().equals("username") || c.getName().equals("password")) {
	 				if(c.getValue()!=null) {
	 					c.setValue(null);
	 					c.setMaxAge(0);
	 					response.addCookie(c);
	 				}
	 			}
	 		}
	 		//跳转到登录页面
	 		response.sendRedirect(request.getContextPath()+"/login.html");
   }
   public void login(HttpServletRequest request,HttpServletResponse 
		   response) throws ServletException, IOException{
	        //获取参数
	 		String checkCode=request.getParameter("checkcode");
	 		String CHECKCODE_SERVER=(String) request.getSession().getAttribute("CHECKCODE_SERVER");
	 		ResultInfo info=new ResultInfo();
	 		if(!checkCode.equalsIgnoreCase(CHECKCODE_SERVER)) {
	 			//验证码错误的情况
	 			info.setFlag(false);
	 			info.setErrorMsg("验证码错误");
	 			//把结果集对象传到Html
	 			String json=writeValueAsString(info);
	 			response.setContentType("application/json;charset=utf-8");
	 			response.getWriter().write(json);
	 			//结束逻辑
	 			return ;
	 		}
	 		//从service层获取结果集对象
	 		String username=request.getParameter("username");
	 		String password=request.getParameter("password");
	 		info=uservice.login(username, password);
	 		if(info.isFlag()) {
	 			//用户成功登录
	 			User u=new UserDaoImpl().findByUsername(username);
	 			//如果用户勾选了自动登录
		 		String auto=request.getParameter("auto");
		 		System.out.println("auto:"+auto);
		 		if(auto!=null) {
					if(auto.equals("auto")) {
						System.out.println("保存cookie域");
						//新建两个cookie URLEncoder.encode(name, "utf-8");
						Cookie c1=new Cookie("username",u.getUsername());
						Cookie c2=new Cookie("password",u.getPassword());
						//设置cookie的保存时间--一周
						c1.setMaxAge(60*60*24*7);
						c2.setMaxAge(60*60*24*7);
						//添加cookie到响应头
						response.addCookie(c1);
						response.addCookie(c2);
					}
		 		}
				System.out.println("不是自动登录");
				
	 			//登录成功，存储用户名到session域
	 			request.getSession().setAttribute("user",u);
	 		}
	 		//把结果集对象传到Html
	 		ObjectMapper mapper=new ObjectMapper();
	 		String json=mapper.writeValueAsString(info);
	 		response.setContentType("application/json;charset=utf-8");
	 		response.getWriter().write(json);
   }
   public void register(HttpServletRequest request,HttpServletResponse 
		   response) throws ServletException, IOException{
	        //获取数据
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
   public void showUsername(HttpServletRequest request,HttpServletResponse 
		   response) throws ServletException, IOException{
	        //展示首页的已登录用户
	 		User u=(User) request.getSession().getAttribute("user");
	 		if(u!=null) {
	 			System.out.println("username:"+u.getUsername());
	 			response.setContentType("application/json;charset=utf-8");
	 			response.getWriter().write(writeValueAsString(u));
	 		}
   }
   public void autoLogin(HttpServletRequest request,HttpServletResponse response) 
		   throws ServletException, IOException{
	   //获取cookie
	   Cookie[]cs=request.getCookies();
	   String username=null;
	   String password=null;
	   if(cs.length>0) {
		   for(Cookie c:cs) {
			   if(c.getName().equals("username")) {
				   username=c.getValue();
			   }
			   if(c.getName().equals("password")) {
				   password=c.getValue();
			   }
		   }
	   }
	   //cookie数据不为空
	   if(username!=null && password!=null) {
		   User user=uservice.autoLogin(username, password);
		   if(user!=null) {
			   //cookie域的值正确，存储用户信息到session域
			   request.getSession().setAttribute("user", user);
			   //写回信息到html
			   response.setContentType("text/html;charset=utf-8");
			   response.getWriter().write("已登录");
		   }else {
			   //cookie域的值不正确，让cookie域失效
			   for(Cookie c:cs) {
				   if(c.getName().equals("username") || c.getName().equals("password")) {
					   c.setMaxAge(0);
					   c.setValue(null);
					   response.addCookie(c);
				   }
			   }
		      //重定向到登录页面
			   response.sendRedirect(request.getContextPath()+"/login.html");
		   }
	   }
	   
   }
}
