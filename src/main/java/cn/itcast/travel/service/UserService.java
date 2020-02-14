package cn.itcast.travel.service;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;

public interface UserService {
  
	/**
	 * 注册用户方法
	 * @param u 要注册的用户信息
	 * @return  返回一个实体类
	 */
	public ResultInfo registerUser(User u);

	/**
	 * 激活用户
	 * @param code
	 * @return
	 */
    public ResultInfo active(String code);
    
    /**
     * 注册时检查用户名是否已经存在
     * @param username 用户名
     * @return 结果集
     */
    public ResultInfo checkUsername(String username);
    
    /**
     * 登录
     * @param username用户名
     * @param password密码
     * @return
     */
    public ResultInfo login(String username,String password);
    
    /**
     * 处理自动登录的情况
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public User autoLogin(String username,String password);
}
