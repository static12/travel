package cn.itcast.travel.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import redis.clients.jedis.Tuple;

public class CategoryServiceImpl implements CategoryService {

	private CategoryDao cdao=new CategoryDaoImpl();
	private Jedis jedis=JedisUtil.getJedis();
	
	@Override
	public List<Category> findAll(){
		//先从redis中查找
		Set<Tuple> set=jedis.zrangeWithScores("category", 0, -1);
		List<Category>list=new ArrayList<>();
		if(!set.isEmpty()&&set!=null) {
			System.out.println("从redis中读取分类记录...");
			for(Tuple tuple:set) {
				Category category=new Category();
				int cid=(int)Math.floor(tuple.getScore());
				category.setCid(cid);
				category.setCname(tuple.getElement());
				//System.out.println(category);
				list.add(category);
			}
		}else {
			//缓存中没有数据，从数据库中读取,并把数据存到redis
			System.out.println("从数据库中读取分类记录...");
			list=cdao.findAll();
			for(Category category:list) {
				jedis.zadd("category", category.getCid(), category.getCname());
			}
		}
        return list;
	}

}
