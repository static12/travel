package cn.itcast.travel.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public List<Category> findAll() {
		String sql="select * from tab_category order by cid";
		List<Category>list=template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));	
		return list;
	}
	@Override
	public Category findOne(int cid) {
		String sql="select * from tab_category where cid=?";
		Category c=null;
		try {
			c=template.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class),cid);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("根据对应的cid没有查询到相应的category");
		}
		return c;
	}
	
	
}
