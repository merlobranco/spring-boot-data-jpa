package com.merlobranco.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.merlobranco.springboot.app.models.entity.Cliente;

public interface ClienteDao extends PagingAndSortingRepository<Cliente, Long>{
}
