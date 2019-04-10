package br.com.alura.forum.controller;


import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.input.NewTopicInputDto;
import br.com.alura.forum.controller.dto.input.TopicSearchInputDto;
import br.com.alura.forum.controller.dto.output.DashboardDto;
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDto;
import br.com.alura.forum.controller.dto.output.TopicOutputDto;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;

@RestController
@RequestMapping(value="/api/topics")
public class TopicController {

	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
	public Page<TopicBriefOutputDto> listTopics(TopicSearchInputDto topicSearch, 
			@PageableDefault(sort="creationInstant", direction=Sort.Direction.DESC) Pageable pageRequest) {

		Specification<Topic> topicSearchSpecification = topicSearch.build();
		Page<Topic> topics = topicRepository.findAll(topicSearchSpecification, pageRequest);
		
		return TopicBriefOutputDto.listFromTopics(topics);
	}
	
	@GetMapping(value="/dashboard", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<DashboardDto> dashboard(){
		List<DashboardDto> listDash = new ArrayList<DashboardDto>();
		List<Category> categorias = categoryRepository.findByCategoryIsNull();
		
		for (Category c : categorias) {
			int allTopics = topicRepository.countTopicsByCategory(c);
			Instant lastWeek = Instant.now().minus(8, ChronoUnit.DAYS);
			int lastWeekTopics = topicRepository.countLastWeekTopicsByCategory(c, lastWeek);
			int unansweredTopics = topicRepository.countUnansweredTopicsByCategory(c);
			DashboardDto dashCategory = new DashboardDto(c.getName(), 
					c.getSubcategoryNames(), 
					allTopics, 
					lastWeekTopics, 
					unansweredTopics);
			listDash.add(dashCategory);
		}
		
		return listDash;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TopicOutputDto> createTopic(@RequestBody NewTopicInputDto newTopicInputDto,
			@AuthenticationPrincipal User loggedUser,
			UriComponentsBuilder uriBuilder){
		Topic topic = newTopicInputDto.build(loggedUser, courseRepository);
		this.topicRepository.save(topic);
		
		URI path = uriBuilder.path("/api/topics/{id}")
				.buildAndExpand(topic.getId()).toUri();
		
		return ResponseEntity.created(path).body(new TopicOutputDto(topic));
		
	}
	
}
