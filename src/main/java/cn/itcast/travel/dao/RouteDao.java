package cn.itcast.travel.dao;

import java.util.List;

import cn.itcast.travel.domain.Route;

public interface RouteDao {
	/**
	 * 根据路线分类id查找分页路线数据
	 * @param currentPage 当前页码
	 * @param pageSize 每页记录数
	 * @param cid 路线分类id
	 * @return
	 */
   public List<Route> findByPage(int currentPage,int pageSize,int cid,String rname);
   
   /**
    * 查找总记录数
    * @return 总记录数
    */
   public int findTotalCount(int cid,String rname);
   
   /**
    * 根据rid查询route对象
    * @param rid
    * @return
    */
   public Route findOne(int rid);
}
