package cn.itcast.travel.service.impl;

import java.util.List;

import org.junit.Test;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {

	private RouteDao rdao=new RouteDaoImpl();
	private SellerDao sdao=new SellerDaoImpl();
	private RouteImgDao rmdao=new RouteImgDaoImpl();
	private CategoryDao cdao=new CategoryDaoImpl();
	private FavoriteDao fdao=new FavoriteDaoImpl();
	
	@Override
	public PageBean findByPage(int cid, int currentPage, int pageSize,String rname) {
		//当前页码默认为第一页，页码记录默认为10条
		currentPage=currentPage>0?currentPage:1;
		pageSize=pageSize>1?pageSize:10;
		//给pageBean大小赋值
		PageBean pb=new PageBean();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		//查询dao层获取总页码
		int totalCount=rdao.findTotalCount(cid,rname);
		//查询dao层获取路线集合
		List<Route>list=rdao.findByPage(currentPage, pageSize, cid,rname);
		int totalPage=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
		pb.setList(list);
		pb.setTotalCount(totalCount);
		pb.setTotalPage(totalPage);
		//TODO
		System.out.println(pb);
		return pb;
	}

	
	@Override
	public Route findOne(int rid) {
		Route route=rdao.findOne(rid);
		if(route==null) {
			return null;		
		}
		Seller seller=sdao.findOne(route.getSid());
		List<RouteImg>imglist=rmdao.findByRid(rid);
		Category category=cdao.findOne(route.getCid());
		//查询dao层获取线路的收藏人数
		int count=fdao.findCountByRid(route.getRid());
		route.setRouteImgList(imglist);
		route.setSeller(seller);
		route.setCategory(category);
		route.setCount(count);
		return route;
	}

}
