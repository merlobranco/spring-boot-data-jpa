package com.merlobranco.springboot.app.models.dao;

import java.util.List;

import com.merlobranco.springboot.app.models.entity.Cliente;

public interface ClienteDao {
	
	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
}
