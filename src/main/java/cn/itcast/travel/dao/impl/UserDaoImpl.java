package cn.itcast.travel.dao.impl;


import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.Md5Util;
import cn.itcast.travel.util.UuidUtil;

public class UserDaoImpl implements UserDao {

	private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
	//根据用户名查找用户
	@Override
	public User findByUsername(String username) {
		String sql="select * from tab_user where username = ?";
		User u=null;
		try {
			u = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("用户名对应的用户为空");
		}
		return u;
	}

	//保存用户
	@Override
	public int save(User user) {
		String sql="insert into tab_user values (null,?,?,?,?,?,?,?,?,?)";
		//把明文密码转为加密串
		String pw = null;
		try {
			pw = Md5Util.encodeByMd5(user.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("pw:"+pw);
		int i=template.update(sql,user.getUsername(),pw,user.getName(),
				user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),
				"N",UuidUtil.getUuid());
		return i;
	}

	@Override
	public User findByCode(String code) {
		String sql="select * from tab_user where code=?";
		User user=null;
		try {
			user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("激活码对应的用户为空");
		}	
		return user;
	}

	@Override
	public int updateStatus(String status,String username) {
		// TODO Auto-generated method stub
		String sql="update tab_user set status=? where username=?";
		int result=template.update(sql, status,username);
		System.out.println("更新了"+result+"条记录");
		return result;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		String sql="select * from tab_user where username=? and password=?";
		User u=null;
		try {
			u=template.queryForObject(sql, new BeanPropertyRowMapper<User>
			          (User.class),username,password);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("该用户名和密码对应的用户为空");
		}
		return u;
	}
	
	
	
}
