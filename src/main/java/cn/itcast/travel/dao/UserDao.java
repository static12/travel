package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
	/**
	 * 根据用户名查找用户
	 * @param username 用户名
	 * @return 返回一个用户
	 */
   public User findByUsername(String username);
   
   /**
    * 
    * @param user 要保存的用户信息
    * @return 返回保存的记录数
    */
   public int save(User user);
   
   /**
    * 根据激活码查找用户
    * @param code
    * @return
    */
   public User findByCode(String code);
   
   /**
    * 更新用户的状态（激活/未激活）
    * @param status 用户状态 Y/N
    * @return 保存的记录数
    */
   public int updateStatus(String status,String username);
   
   /**
    * 根据用户名和密码查找用户
    * @param username 用户名
    * @param password 密码
    * @return
    */
   public User findByUsernameAndPassword(String username,String password);
}
