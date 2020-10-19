package com.merlobranco.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.merlobranco.springboot.app.models.entity.Cliente;

public interface ClienteDao extends CrudRepository<Cliente, Long>{
}
