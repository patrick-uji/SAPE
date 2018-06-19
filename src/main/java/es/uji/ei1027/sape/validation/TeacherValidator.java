package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import org.springframework.validation.Errors;
import es.uji.ei1027.sape.model.ProfesorTutor;
public class TeacherValidator extends BaseValidator<ProfesorTutor>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		ProfesorTutor teacher = (ProfesorTutor)obj;
		if ( Utils.isEmptyString(teacher.getNombre()) )
		{
			errors.rejectValue("nombre", "no_name", "Por favor introduce un nombre");
		}
		if ( Utils.isEmptyString(teacher.getDepartamento()) )
		{
			errors.rejectValue("departamento", "no_department", "Por favor introduce un departamento");
		}
		if ( Utils.isEmptyString(teacher.getDespacho()) )
		{
			errors.rejectValue("despacho", "no_office", "Por favor introduce un despacho");
		}
		if ( Utils.isEmptyString(teacher.getEmail()) )
		{
			errors.rejectValue("email", "no_email", "Por favor introduce un email");
		}
	}
}
