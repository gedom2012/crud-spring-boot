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

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Client entity = repository.getOne(id);
		return new ClientDTO(entity);
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
		Client entity = repository.getOne(id);
		copyToDtoEntity(clientDto, entity);
		repository.save(entity);
		return new ClientDTO(entity);

	}
	
	@Transactional
	public void delete(Long id) {
		Client entity = repository.getOne(id);
		repository.delete(entity);
	}

	private void copyToDtoEntity(ClientDTO clientDto, Client entity) {
		entity.setName(clientDto.getName());
		entity.setCpf(clientDto.getCpf());
		entity.setBirthDate(clientDto.getBirthDate());
		entity.setIncome(clientDto.getIncome());
		entity.setChildren(clientDto.getChildren());
	}

}
