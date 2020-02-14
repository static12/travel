package cn.itcast.travel.dao.impl;

import java.util.ArrayList;

import java.util.List;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;

public class RouteDaoImpl implements RouteDao{

	private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
	
	@Override
	public List<Route> findByPage(int currentPage, int pageSize, int cid,String rname) {
		String sql="select * from tab_route where cid=? ";
		List para = new ArrayList();
		para.add(cid);
		if(rname!=null) {
			sql+=" and rname like ? ";
			para.add("%"+rname+"%");
		}
		 sql+=" limit ?,?";
		 int start=(currentPage-1)*pageSize;
		 para.add(start);
		 para.add(pageSize);
		 List<Route> list=template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),
				para.toArray());
		return list;
	}

	@Override
	public int findTotalCount(int cid,String rname) {
		String sql="select count(*) from tab_route where cid=? ";
		List para=new ArrayList();
		para.add(cid);
		if(rname!=null) {
			sql+=" and rname like ? ";
			para.add("%"+rname+"%");
		}
		return template.queryForObject(sql,Integer.class,para.toArray());
	}

	@Override
	public Route findOne(int rid) {
		String sql="select * from tab_route where rid=?";
		Route r=null;
		try {
			r = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class),rid);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("没有查询对应rid的route");
		}
		return r;
	}
    
	@Test
    public void test() {
    	Route r=findOne(0);
    	System.out.println(r);
    }
}
