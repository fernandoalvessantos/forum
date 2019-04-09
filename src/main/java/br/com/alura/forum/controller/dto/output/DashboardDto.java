package br.com.alura.forum.controller.dto.output;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.forum.model.Category;

public class DashboardDto {

	private String categoryName;
	private List<String> subcategories;
	private int allTopics;
	private int lastWeekTopics;
	private int unansweredTopics;
	
	public DashboardDto(String categoryName, List<String> subcategories, int allTopics, int lastWeekTopics,
			int unansweredTopics) {
		this.categoryName = categoryName;
		this.subcategories = new ArrayList<String>();
		this.subcategories = subcategories;
		this.allTopics = allTopics;
		this.lastWeekTopics = lastWeekTopics;
		this.unansweredTopics = unansweredTopics;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	public List<String> getSubcategories() {
		return subcategories;
	}
	public int getAllTopics() {
		return allTopics;
	}
	public int getLastWeekTopics() {
		return lastWeekTopics;
	}
	public int getUnansweredTopics() {
		return unansweredTopics;
	}
	
	
}
