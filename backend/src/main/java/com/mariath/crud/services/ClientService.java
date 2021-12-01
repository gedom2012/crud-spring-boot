package com.mariath.crud.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mariath.crud.DTO.ClientDTO;
import com.mariath.crud.entities.Client;
import com.mariath.crud.repositories.ClientRepository;
import com.mariath.crud.services.exceptions.DataBaseException;
import com.mariath.crud.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(Pageable pageable) {
		Page<Client> entity = repository.findAll(pageable);
		return entity.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		try {
			Client entity = repository.getOne(id);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resource not found " + id);
		}

	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyToDtoEntity(dto, entity);
		repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDto) {
		try {
			Client entity = repository.getOne(id);
			copyToDtoEntity(clientDto, entity);
			repository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resource not found " + id);
		}

	}

	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Resource not found exception " + id);
		}

	}

	private void copyToDtoEntity(ClientDTO clientDto, Client entity) {
		entity.setName(clientDto.getName());
		entity.setCpf(clientDto.getCpf());
		entity.setBirthDate(clientDto.getBirthDate());
		entity.setIncome(clientDto.getIncome());
		entity.setChildren(clientDto.getChildren());
	}

}
