package com.example.ingressos.compartilhado;

import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.ingressos.empresa.Empresa;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

public class MyCustomResolver implements HandlerMethodArgumentResolver {

	private ApplicationContext context;

	public MyCustomResolver(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthenticatedPrincipal.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		// Implemente o código para converter o valor do parâmetro de entrada em
		// um objeto MyCustomType
		// Aqui, usamos o HttpServletRequest para obter o valor do parâmetro
		// chamado "myCustomParameter"

		HttpServletRequest request = (HttpServletRequest) webRequest
				.getNativeRequest();
		String value = request.getHeader("empresa-id");

		EntityManager manager = context.getBean(EntityManager.class);

		return manager.find(Empresa.class, Long.valueOf(value));
	}
}
