package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event obj = repository.getOne(id);
			copyDTOtoEntity(dto, obj);
			obj = repository.save(obj);
			return new EventDTO(obj);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(
					"Event '" + id + "' not found");
		}		
	}

	private void copyDTOtoEntity(EventDTO dto, Event obj) {
		//obj.setId(dto.getId());
		obj.setName(dto.getName());
		obj.setDate(dto.getDate());
		obj.setUrl(dto.getUrl());
		obj.setCity(cityRepository.getOne(dto.getCityId()));		
	}

}
