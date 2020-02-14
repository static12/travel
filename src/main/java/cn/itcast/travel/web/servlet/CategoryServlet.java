package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{
	
	private CategoryService cservice=new CategoryServiceImpl();
	
    public void findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	//向service层获取分类数据
    	List<Category>list=cservice.findAll();
    	//转换为json字符串
    	String json=new ObjectMapper().writeValueAsString(list);
    	System.out.println("category:"+json);
    	//写回到展示页面
    	response.setContentType("application/json;charset=utf-8");
    	response.getWriter().write(json);
    }
}
