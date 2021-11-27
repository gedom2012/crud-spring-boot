package com.mariath.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mariath.crud.DTO.ClientDTO;
import com.mariath.crud.entities.Client;
import com.mariath.crud.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest request) {
		Page<Client> entity = repository.findAll(request);
		return entity.map(x -> new ClientDTO(x));
	}	

}
