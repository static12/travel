package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取请求路径
		String uri=request.getRequestURI();
		String methodName=uri.substring(uri.lastIndexOf("/")+1);
		//System.out.println("methodName:"+methodName);
		try {
			//获取方法
			//System.out.println("this:"+this);
			Method method=this.getClass().getMethod(methodName, 
					HttpServletRequest.class,HttpServletResponse.class);
			//执行方法
			method.invoke(this, request,response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

    public String writeValueAsString(Object obj) {
    	ObjectMapper mapper=new ObjectMapper();
    	String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return json;
    }
}
