package cn.itcast.travel.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;

public class RouteImgDaoImpl implements RouteImgDao{

	private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
	
	@Override
	public List<RouteImg> findByRid(int rid) {
		String sql="select * from tab_route_img where rid=?";
		List<RouteImg>imglist=template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
		
		return imglist;
	}
    
}
