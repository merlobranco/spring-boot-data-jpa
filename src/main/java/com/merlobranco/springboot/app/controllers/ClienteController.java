package com.merlobranco.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.merlobranco.springboot.app.models.entity.Cliente;
import com.merlobranco.springboot.app.models.service.ClienteService;
import com.merlobranco.springboot.app.models.service.UploadFileService;
import com.merlobranco.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final int SIZE = 4;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private UploadFileService uploadFileService;

	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
					.body(recurso);
		} catch (MalformedURLException e) {
			log.error("No ha podido cargar la imagen: " + filename, e);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		if (id <= 0) {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/listar";
		}

		Cliente cliente = clienteService.fecthByIdWithFacturas(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
		model.addAttribute("cliente", cliente);
		return "/ver";
	}

	@GetMapping(value={"/listar", "/"})
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication) {
		if (authentication !=null) {
			log.info("Hola Usuario autenticado, tu username es: ".concat(authentication.getName()));
		}
		
		// Getting the authentication object without injection, just through static access
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth !=null) {
			log.info("Utilizando acceso estático. Hola Usuario autenticado, tu username es: ".concat(auth.getName()));
		}
		
		Pageable pageRequest = PageRequest.of(page, SIZE);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "/listar";
	}

	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("titulo", "Formulario de Cliente");
		model.addAttribute("cliente", cliente);
		return "/form";
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		if (id <= 0) {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/listar";
		}

		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Formulario de Cliente");
		model.addAttribute("cliente", cliente);
		return "/form";
	}

	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}

		if (!foto.isEmpty()) {
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadFileService.delete(cliente.getFoto());
			}
			String uniqueFilename = "";
			try {
				uniqueFilename = uploadFileService.copy(foto);
				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				cliente.setFoto(uniqueFilename);
			} catch (IOException e) {
				flash.addFlashAttribute("error", "No se ha subido correctamente la foto");
			}
		}
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/listar";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);

			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");

			if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con éxito!");
			}
		}
		return "redirect:/listar";
	}

}
