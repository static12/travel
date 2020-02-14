package cn.itcast.travel.dao;

import java.util.List;

import cn.itcast.travel.domain.Category;

public interface CategoryDao {
	/**
	 * 展示所有分类信息
	 * @return
	 */
   public List<Category> findAll();
   
   /**
    * 根据分类id查找数据
    * @param cid
    * @return
    */
   public Category findOne(int cid);
}
