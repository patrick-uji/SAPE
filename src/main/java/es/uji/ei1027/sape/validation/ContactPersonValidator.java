package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import org.springframework.validation.Errors;
import es.uji.ei1027.sape.model.PersonaContacto;
public class ContactPersonValidator extends BaseValidator<PersonaContacto>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		PersonaContacto contactPerson = (PersonaContacto)obj;
		if ( Utils.isEmptyString(contactPerson.getNombre()) )
		{
			errors.rejectValue("nombre", "no_name", "Por favor introduce un nombre");
		}
		if ( Utils.isEmptyString(contactPerson.getEmail()) )
		{
			errors.rejectValue("email", "no_email", "Por favor introduce un email");
		}
		if ( Utils.isEmptyString(contactPerson.getDescripcionPracticas()) )
		{
			errors.rejectValue("descripcionPracticas", "no_description", "Por favor introduce una descripción");
		}
	}
}
