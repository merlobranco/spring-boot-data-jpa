package com.merlobranco.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.merlobranco.springboot.app.models.entity.Cliente;

@Repository
public class ClienteDaoImpl implements ClienteDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		em.persist(cliente);
	}

}
