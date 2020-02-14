package cn.itcast.travel.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;

public class FavoriteDaoImpl implements FavoriteDao {

	private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public Favorite findByUidAndRid(int rid, int uid) {
		String sql="select * from tab_favorite where rid=? and uid=?";
		Favorite f=null;
		try {
			f = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class),
					rid,uid);
		} catch (DataAccessException e) {
			System.out.println("没有找到与rid和cid对应的favorite");
		}
		return f;
	}
	@Override
	public int addOne(int rid, int uid) {
		String sql="insert into tab_favorite values(?,?,?)";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		int r=0;
		Date d = null;
		try {
			 d=sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 r=template.update(sql,rid,d,uid);
		return r;
	}
	
	@Override
	public int removeOne(int rid, int uid) {
		String sql="delete from tab_favorite where rid=? and uid=?";
		int r=template.update(sql,rid,uid);
		return r;
	}
	@Override
	public int findCountByRid(int rid) {
		String sql="select count(*) from tab_favorite where rid=?";
		int r=template.queryForObject(sql, Integer.class,rid);
		return r;
	}
	
   
}
