package cn.itcast.travel.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;

public class SellerDaoImpl implements SellerDao{

	private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
	
	@Override
	public Seller findOne(int sid) {
		String sql="select * from tab_seller where sid=?";
		Seller seller=null;
		try {
			seller=template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class),sid);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("没有和sid相对应的seller");
		}
		return seller;
	}
  
}
