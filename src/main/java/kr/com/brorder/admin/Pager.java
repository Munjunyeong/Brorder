package kr.com.brorder.admin;

import java.util.ArrayList;
import java.util.List;

public class Pager {
	private int page = 1;
	private int perPage = 10;
	private float total = 0;
	private int perGroup = 5;
	
	private int search;
	private String keyword;
	
	
	public String getQuery() {
		String query = "";
		
		if(search > 0)
			query = "&search=" + search + "&keyword=" + keyword;
		
		return query;
	}
	
	public int getSearch() {
		return search;
	}

	public void setSearch(int search) {
		this.search = search;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Integer> getList() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		int startPage = ((page - 1) / perGroup + 0) * perGroup + 1;
		int last = getLast();
		
		for(int i = startPage; i < (startPage + perGroup) && i <= last; i++)
			list.add(i);
		
		if(list.isEmpty())
			list.add(1);
		
		return list;
	}
	
	public int getNext() {
		int next = ((page - 1) / perGroup + 1) * perGroup + 1;
		int last = getLast();
		
		//삼항연산자   조건        참     거짓
		return next < last ? next : last;
	}
	
	public int getPrev() {
		return page <= perGroup ? 1 : ((page - 1) / perGroup - 1) * perGroup + 1;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public int getLast() {
		return (int) Math.ceil(total / perPage);
	}
	
	public int getOffset() {
		return (page - 1) * perPage;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		if(page > 0)
			this.page = page;
	}
	
	public int getPerPage() {
		return perPage;
	}
	
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	
}
