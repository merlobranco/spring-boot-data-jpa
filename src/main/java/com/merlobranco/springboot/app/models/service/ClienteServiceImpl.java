package com.merlobranco.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merlobranco.springboot.app.models.dao.ClienteDao;
import com.merlobranco.springboot.app.models.dao.IProductoDao;
import com.merlobranco.springboot.app.models.entity.Cliente;
import com.merlobranco.springboot.app.models.entity.Producto;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;

	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		return (List<Cliente>)clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional
	public List<Producto> findByNombre(String term) {
		return productoDao.buscarPorNombre(term);
	}
}
