package cn.itcast.travel.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.domain.User;


@WebFilter("/*")
public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		//获取请求路径
		String path=req.getRequestURI();
		//System.out.println("path:"+path);
		if(path.contains("/css/")||path.contains("/js/")||path.contains("/img/")
				||path.contains("/error/")||path.contains("/images/")||
				path.contains("/fonts/")||path.contains("/register.html")||
				path.contains("/register_ok.html")||path.contains("/header.html")||
				path.contains("/footer.html")||path.contains("/user/register")||
				path.contains("/user/checkCode")||path.contains("/BaseServlet")||
				path.contains("/login.html")
		        ||path.contains("/category/findAll")||path.contains("/user/login")){
			//System.out.println("登录资源，放行");
			chain.doFilter(request, response);
		}else {
			User u=(User) req.getSession().getAttribute("user");
			if(u!=null) {
				//System.out.println("已经登录，放行");
				chain.doFilter(request, response);
			}
			if(u==null) {
				//查看是否存入了cookie信息
				Cookie[] cs=req.getCookies();
				String username = null,password = null;
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
				System.out.println(username+":"+password);
				if(username!=null && password!=null) {
					//转发到servlet处理自动登录
					request.getRequestDispatcher("user/autoLogin").forward(request, response);
				}else {
					//System.out.println("没登录，重定向到登录页面");
					//重定向到登录页面
					resp.sendRedirect(req.getContextPath()+"/login.html");
				}
				
				
			}
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
