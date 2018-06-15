package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.domain.Login;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PersonaContacto;
import es.uji.ei1027.sape.model.ProfesorTutor;

import org.springframework.validation.Errors;
public class OfferValidator extends BaseValidator<OfertaProyecto>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		OfertaProyecto offer = (OfertaProyecto)obj;
		if ( Utils.isEmptyString(offer.getTitulo()) )
		{
			errors.rejectValue("titulo", "Error", "Por favor introduce un titulo");
		}
		if ( Utils.isEmptyString(offer.getObjetivo()) )
		{
			errors.rejectValue("objetivo", "Error", "Por favor introduce un objetivo");
		}
	}
}
