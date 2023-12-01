package com.primax.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("EmailValidator")
public class EmailValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object convertedValue) {
		if (convertedValue != null) {
			matcher = pattern.matcher(convertedValue.toString());
			if (!matcher.matches()) {
				String label = (String) component.getAttributes().get("label");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO, "Error de validación",
						"El valor de " + label + " no es válido"));
			}
		}
	}
}
