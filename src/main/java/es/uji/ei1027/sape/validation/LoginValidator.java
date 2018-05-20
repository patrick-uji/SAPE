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
			errors.rejectValue("username", "Error", "Please enter your username");
		}
		if ( Utils.isEmptyString(user.getPassword()) )
		{
			errors.rejectValue("pw", "Error", "Please enter your password");
		}
	}
}
