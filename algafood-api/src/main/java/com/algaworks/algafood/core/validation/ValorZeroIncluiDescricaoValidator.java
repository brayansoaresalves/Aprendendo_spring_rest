package com.algaworks.algafood.core.validation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object>{

	private String ValorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		// TODO Auto-generated method stub
		this.ValorField = constraintAnnotation.ValorField();
		this.descricaoField = constraintAnnotation.descricaoField();
		this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		boolean valido = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(value.getClass(), 
					ValorField).getReadMethod().invoke(value);
			
			String descricao = (String) BeanUtils.getPropertyDescriptor(value.getClass(), 
					descricaoField).getReadMethod().invoke(value);
			
			if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0
					&& descricao != null) {
				valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ValidationException(e.getMessage());
		}
		return valido;
	}
	
	

}
