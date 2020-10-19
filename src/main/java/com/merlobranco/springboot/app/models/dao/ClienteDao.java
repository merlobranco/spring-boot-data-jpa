package com.merlobranco.springboot.app.models.dao;

import java.util.List;

import com.merlobranco.springboot.app.models.entity.Cliente;

public interface ClienteDao {
	
	public List<Cliente> findAll();
	
	public Cliente findOne(Long id);
	
	public void save(Cliente cliente);
	
	public void delete(Long id);
}
