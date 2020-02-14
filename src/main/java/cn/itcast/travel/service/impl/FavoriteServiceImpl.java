package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

	private FavoriteDao fdao=new FavoriteDaoImpl();
	
	@Override
	public boolean isFlag(int rid, int uid) {
		Favorite f=fdao.findByUidAndRid(rid, uid);
		//Favorite [route=null, date=2019-03-03, user=null]
		if(f!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addOne(int rid, int uid) {
		int r=fdao.addOne(rid, uid);
		if(r>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeOne(int rid, int uid) {
		int r=fdao.removeOne(rid, uid);
		if(r>0) {
			return true;
		}
		return false;
	}

}
