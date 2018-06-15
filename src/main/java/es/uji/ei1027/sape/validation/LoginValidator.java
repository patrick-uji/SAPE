package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.domain.Login;
import org.springframework.validation.Errors;
public class LoginValidator extends BaseValidator<Login>
{
	@Override
	public void validate(Object obj, Errors errors)
	{
		Login user = (Login)obj;
		if ( Utils.isEmptyString(user.getEmail()) )
		{
			errors.rejectValue("email", "Error", "Por favor introduce tu email");
		}
		if ( Utils.isEmptyString(user.getPassword()) )
		{
			errors.rejectValue("password", "Error", "Por favor introduce tu password");
		}
	}
}
