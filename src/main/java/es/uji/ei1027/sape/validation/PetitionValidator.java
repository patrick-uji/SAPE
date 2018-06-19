package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import org.springframework.validation.Errors;
import es.uji.ei1027.sape.model.PeticionRevision;
public class PetitionValidator extends BaseValidator<PeticionRevision>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		PeticionRevision peticionRevision = (PeticionRevision)obj;
		if ( Utils.isEmptyString(peticionRevision.getTextoPeticion()) )
		{
			errors.rejectValue("textoPeticion", "no_text", "Por favor introduce un mensaje");
		}
	}
}
