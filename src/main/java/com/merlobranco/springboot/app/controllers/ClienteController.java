package com.merlobranco.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.merlobranco.springboot.app.models.dao.ClienteDao;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteDao clienteDao;
	
	@GetMapping("listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}

}
