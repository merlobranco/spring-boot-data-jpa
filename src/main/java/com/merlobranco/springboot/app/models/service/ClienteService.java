package com.merlobranco.springboot.app.models.service;

import java.util.List;

import com.merlobranco.springboot.app.models.entity.Cliente;

public interface ClienteService {
	
	public List<Cliente> findAll();
	
	public Cliente findOne(Long id);
	
	public void save(Cliente cliente);
	
	public void delete(Long id);

}
