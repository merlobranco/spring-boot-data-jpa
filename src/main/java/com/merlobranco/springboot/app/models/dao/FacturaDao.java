package com.merlobranco.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.merlobranco.springboot.app.models.entity.Factura;

public interface FacturaDao extends CrudRepository<Factura, Long> {

}
