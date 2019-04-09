package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.topic.domain.Topic;

public interface CategoryRepository extends Repository<Category, Long>, JpaSpecificationExecutor<Topic>{
	
	List<Category> findByCategoryIsNull();
}
