package cn.itcast.travel.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{
	
	private RouteService rservice=new RouteServiceImpl();
	private FavoriteService fservice=new FavoriteServiceImpl();
	
	//展示分页数据
    public void findByPage(HttpServletRequest request,HttpServletResponse response)
    		throws ServletException, IOException {
    	int currentPage,cid,pageSize;
    	//request.setCharacterEncoding("utf-8");
    	//获取参数
    	try {
			 currentPage=Integer.parseInt(request.getParameter("currentPage"));
			 cid=Integer.parseInt(request.getParameter("cid"));
			 pageSize=Integer.parseInt(request.getParameter("pageSize"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("请求参数为空");
			currentPage=1;
			cid=5;
			pageSize=10;	
		}
    	String rname=request.getParameter("rname");
    	System.out.println("currentPage="+currentPage+",cid="+cid+",pageSize="+pageSize);
    	// rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
    	 try {
			  if("null".equals(rname) || rname.length()<=0 ||"".equals(rname)) {
				   rname=null;
			   }else {
				   rname=new String(rname.getBytes("iso-8859-1"),"utf-8");
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("字符串空指针异常");
			rname=null;
		}  
    	System.out.println("rname:"+rname);
    	PageBean pb=rservice.findByPage(cid, currentPage, pageSize,rname);
    	//对象转换为json字符串
    	String json=writeValueAsString(pb);
    	//把转换的json字符串返回给html页面
    	response.setContentType("application/json;charset=utf-8");
    	response.getWriter().write(json);
    }
   
    //查询详情数据
    public void findOne(HttpServletRequest request,HttpServletResponse response)
    		throws ServletException, IOException {
    	int rid;
		try {
			rid = Integer.parseInt(request.getParameter("rid"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("rid转换类型错误");
			rid=0;
		}
		System.out.println("rid="+rid);
    	Route r=rservice.findOne(rid);
    	String json=writeValueAsString(r);
    	System.out.println(json);
    	response.setContentType("application/json;charset=utf-8");
    	response.getWriter().write(json);
    			
    }

    //判断当前线路是否被当前用户收藏
    public void isFavorite(HttpServletRequest request,HttpServletResponse response)
    		throws ServletException, IOException {
    	//获取参数
    	int rid=Integer.parseInt(request.getParameter("rid"));
    	User u=(User)request.getSession().getAttribute("user");
    	if(u==null) {
    		return;
    	}
    	int uid=u.getUid();
    	boolean flag=fservice.isFlag(rid, uid);
    	String str=flag==true?"true":"false";
    	System.out.println("str:"+flag);
    	response.setContentType("text/html;charset=utf-8");
    	response.getWriter().write(str);
    }

}
