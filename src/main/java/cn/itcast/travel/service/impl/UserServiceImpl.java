package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.Md5Util;

public class UserServiceImpl implements UserService {

	private UserDao udao=new UserDaoImpl();
	@Override
	public ResultInfo registerUser(User u) {
		//调用dao根据用户名查询用户，存在直接返回false,不存在保存信息
		User temp=udao.findByUsername(u.getUsername());
		ResultInfo info=new ResultInfo();
		if(temp==null) {
			//用户名不存在，保存信息
			int result=udao.save(u);
			//获取保存后的用户
			temp=udao.findByUsername(u.getUsername());
			if(result==1) {
				//保存成功
				info.setFlag(true);
				//发邮件
				MailUtils.sendMail(temp.getEmail(), 
			     "欢迎注册本网站，请<a href='http://localhost/travel/user/active?activeCode="+temp.getCode()+"'>点我</a>激活账号", "激活邮件");
			}
			if(result!=1) {
				info.setFlag(false);
				info.setErrorMsg("服务器繁忙，请稍后重试...");
			}
		}else {
			info.setFlag(false);
			info.setErrorMsg("用户名已存在");
		}
		return info;
	}
	@Override
	public ResultInfo active(String code) {
		//从数据库中根据激活码查找用户，若该用户的状态为n，改为y
		System.out.println("code:"+code);
		User u=udao.findByCode(code);
		System.out.println(u);
		ResultInfo info=new ResultInfo();
		if(u==null) {
			info.setFlag(false);
			info.setErrorMsg("该用户不存在，请联系管理员");
		}else {
			if("Y".equals(u.getStatus())){
				info.setErrorMsg("该账户已经被激活，请联系管理员");
				info.setFlag(false);
			}
			if("N".equals(u.getStatus())) {
				info.setFlag(true);
				//保存用户--更新用户的状态
				udao.updateStatus("Y",u.getUsername());			
			}
		}
		return info;
	}
	@Override
	public ResultInfo checkUsername(String username) {
		User u=udao.findByUsername(username);
		ResultInfo info=new ResultInfo();
		if(u==null) {
			//用户名可用
			info.setFlag(true);
		}else {
			//用户名已存在，不可用
			info.setFlag(false);
			info.setErrorMsg("该用户已存在");
		}
		return info;
	}
	@Override
	public ResultInfo login(String username, String password) {
		String sql_pw=null;
		ResultInfo info=new ResultInfo();
		try {
			 sql_pw=Md5Util.encodeByMd5(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User u=udao.findByUsernameAndPassword(username, sql_pw);
		if(u==null) {
			info.setFlag(false);
			info.setErrorMsg("用户名或密码错误");
		}else {
			if(u.getStatus().equalsIgnoreCase("Y")) {
				//用户已激活
				info.setFlag(true);
			}
			if(u.getStatus().equalsIgnoreCase("N")) {
				info.setFlag(false);
				info.setErrorMsg("该账户尚未激活，请登录您的注册邮箱进行激活");
			}
		}
		return info;
	}
	@Override
	public User autoLogin(String username, String password) {
		return udao.findByUsernameAndPassword(username, password);
	}
    
}
