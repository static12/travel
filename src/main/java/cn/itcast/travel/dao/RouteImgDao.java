package cn.itcast.travel.dao;

import java.util.List;

import cn.itcast.travel.domain.RouteImg;

public interface RouteImgDao {
	/**
	 * 根据rid查找路线的图片
	 * @return
	 */
   public List<RouteImg> findByRid(int rid);
}
