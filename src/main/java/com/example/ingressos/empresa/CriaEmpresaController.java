package com.example.ingressos.empresa;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.persistence.EntityManager;

@RestController
public class CriaEmpresaController {
  @PersistenceContext
	private EntityManager manager;

	public CriaEmpresaController(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@PostMapping("/api/empresas")
	@Transactional
	public void executa(@Valid @RequestBody NovaEmpresaRequest request) {
		manager.persist(new Empresa(request.nome));
	}
}
