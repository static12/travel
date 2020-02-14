package cn.itcast.travel.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet{
	
	private FavoriteService fservice=new FavoriteServiceImpl();
	
	public void addOne(HttpServletRequest request,HttpServletResponse response) 
			   throws ServletException, IOException{
		int rid=Integer.parseInt(request.getParameter("rid"));
	    User u=(User) request.getSession().getAttribute("user");
	    if(u==null) {
	    	return;
	    }
	    int uid=u.getUid();
	    boolean r=fservice.addOne(rid, uid);
	    String str=(r==true)?"true":"false";
	    response.setContentType("text/html;charset=utf-8");
	    response.getWriter().write(str);
	}
	public void removeOne(HttpServletRequest request,HttpServletResponse response) 
			   throws ServletException, IOException{
		int rid=Integer.parseInt(request.getParameter("rid"));
	    User u=(User) request.getSession().getAttribute("user");
	    if(u==null) {
	    	return;
	    }
	    int uid=u.getUid();
	    boolean r=fservice.removeOne(rid, uid);
	    String str=(r==true)?"true":"false";
	    response.setContentType("text/html;charset=utf-8");
	    response.getWriter().write(str);
	}

}
