package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.domain.Login;
import es.uji.ei1027.sape.model.PersonaContacto;
import es.uji.ei1027.sape.model.ProfesorTutor;

import org.springframework.validation.Errors;
public class ContactPersonValidator extends BaseValidator<PersonaContacto>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		PersonaContacto contactPerson = (PersonaContacto)obj;
		if ( Utils.isEmptyString(contactPerson.getNombre()) )
		{
			errors.rejectValue("nombre", "Error", "Por favor introduce un nombre");
		}
		if ( Utils.isEmptyString(contactPerson.getEmail()) )
		{
			errors.rejectValue("email", "Error", "Por favor introduce un email");
		}
		if ( Utils.isEmptyString(contactPerson.getDescripcionPracticas()) )
		{
			errors.rejectValue("descripcionPracticas", "Error", "Por favor introduce una descripción");
		}
	}
}
