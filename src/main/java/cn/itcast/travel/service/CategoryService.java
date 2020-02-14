package cn.itcast.travel.service;

import java.util.List;

import cn.itcast.travel.domain.Category;

public interface CategoryService {
	/**
	 * 查找所有分类记录
	 * @return
	 */
   public List<Category> findAll();
}
