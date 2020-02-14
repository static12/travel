package cn.itcast.travel.service;

public interface FavoriteService {
	/**
	 * 根据路线id和用户id判断路线是否被用户收藏
	 * @param rid 路线id
	 * @param uid 用户id
	 * @return
	 */
    public boolean isFlag(int rid,int uid);
    
    public boolean addOne(int rid,int uid);
    
    public boolean removeOne(int rid,int uid);
}
