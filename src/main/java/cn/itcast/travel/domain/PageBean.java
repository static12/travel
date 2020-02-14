package cn.itcast.travel.domain;

import java.util.List;

public class PageBean {
	private int totalCount;
	private int totalPage;
	private int currentPage;
	private int pageSize;
	private List<Route> list;

	public PageBean() {
		super();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Route> getList() {
		return list;
	}

	public void setList(List<Route> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PageBean [totalCount=" + totalCount + ", totalPage=" + totalPage + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + ", list=" + list + "]";
	}

}
