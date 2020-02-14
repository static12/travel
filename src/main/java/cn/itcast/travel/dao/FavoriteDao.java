package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
	/**
	 * 根据rid和uid查找数据库中的记录
	 * @param rid
	 * @param uid
	 */
   public Favorite findByUidAndRid(int rid,int uid);
   
   public int addOne(int rid,int uid);
   
   public int removeOne(int rid,int uid);
   
   /**
    * 查询线路的收藏人数
    * @param rid 线路id
    * @return 收藏人数
    */
   public int findCountByRid(int rid);
}
