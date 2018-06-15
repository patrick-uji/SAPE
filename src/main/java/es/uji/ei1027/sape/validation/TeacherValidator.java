package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.domain.Login;
import es.uji.ei1027.sape.model.ProfesorTutor;

import org.springframework.validation.Errors;
public class TeacherValidator extends BaseValidator<ProfesorTutor>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		ProfesorTutor teacher = (ProfesorTutor)obj;
		if ( Utils.isEmptyString(teacher.getNombre()) )
		{
			errors.rejectValue("nombre", "Error", "Por favor introduce un nombre");
		}
		if ( Utils.isEmptyString(teacher.getDepartamento()) )
		{
			errors.rejectValue("departamento", "Error", "Por favor introduce un departamento");
		}
		if ( Utils.isEmptyString(teacher.getDespacho()) )
		{
			errors.rejectValue("despacho", "Error", "Por favor introduce un despacho");
		}
		if ( Utils.isEmptyString(teacher.getEmail()) )
		{
			errors.rejectValue("email", "Error", "Por favor introduce un email");
		}
	}
}
