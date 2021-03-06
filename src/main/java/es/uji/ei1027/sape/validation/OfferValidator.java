package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import org.springframework.validation.Errors;
import es.uji.ei1027.sape.model.OfertaProyecto;
public class OfferValidator extends BaseValidator<OfertaProyecto>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		OfertaProyecto offer = (OfertaProyecto)obj;
		if ( Utils.isEmptyString(offer.getTitulo()) )
		{
			errors.rejectValue("titulo", "no_title", "Por favor introduce un titulo");
		}
		if ( Utils.isEmptyString(offer.getObjetivo()) )
		{
			errors.rejectValue("objetivo", "no_objective", "Por favor introduce un objetivo");
		}
	}
}
