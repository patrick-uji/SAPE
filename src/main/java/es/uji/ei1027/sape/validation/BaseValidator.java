package es.uji.ei1027.sape.validation;
import es.uji.ei1027.sape.Utils;
import org.springframework.validation.Validator;
public abstract class BaseValidator<T> implements Validator
{
	private Class<T> tClass;
	@SuppressWarnings("unchecked")
	public BaseValidator()
	{
		this.tClass = (Class<T>)Utils.getTClass(this);
	}
	@Override
	public boolean supports(Class<?> type)
	{
		return tClass.isAssignableFrom(type);
	}
}
