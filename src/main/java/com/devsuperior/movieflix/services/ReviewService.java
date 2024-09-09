package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewService {
	
	@Autowired
	ReviewRepository repository;
	
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private AuthService authService;
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		
		User user = authService.authenticated();
		//inserir a review
		try {
			Review entity = new Review();
			entity.setMovie(movieRepository.getReferenceById(dto.getMovieId()));
			entity.setUser(user);
			entity.setText(dto.getText());
			entity = repository.save(entity);
			return new ReviewDTO(entity);
		
		}
		catch (EntityNotFoundException e){
			throw new ResourceNotFoundException ("Filme não encontrado" + dto.getMovieId());
		}
		
		
	}

}
