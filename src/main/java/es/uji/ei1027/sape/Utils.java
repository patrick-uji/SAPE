package es.uji.ei1027.sape;
import java.sql.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import java.lang.reflect.ParameterizedType;
import java.security.NoSuchAlgorithmException;
import org.springframework.validation.Validator;
import org.springframework.validation.BindingResult;
import es.uji.ei1027.sape.validation.LoginValidator;
public class Utils
{
	private static final boolean DEBUG = true;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static boolean isEmptyString(String string)
	{
		return string != null && string.trim().equals("");
	}
	public static Date stringToDate(String string)
	{
		try
		{
			return new Date(DATE_FORMAT.parse(string).getTime());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public static String now()
	{
		Date nowDate = new Date(Calendar.getInstance().getTime().getTime());
		return nowDate.toString();
	}
	public static Date nowDate()
	{
		return stringToDate(now());
	}
	public static Class<?> getTClass(Object genericInstance)
	{
    	/* Example explanation:
    	 * getClass() => Class<AsignacionDao>
    	 * getClass().getSuperclass() => Class<AbstractDao>
    	 * getClass().getGenericSuperclass() => Class<AbstractDao<Asignacion>>
    	*/
    	//Idea from: https://stackoverflow.com/a/17767068
    	ParameterizedType genericSuperClass = (ParameterizedType)genericInstance.getClass().getGenericSuperclass();
		return (Class<?>)genericSuperClass.getActualTypeArguments()[0];
	}
	public static String hashPassword(String password)
	{
		try
		{
			return bytesToHex( MessageDigest.getInstance("MD5").digest(password.getBytes()) );
		}
		catch (NoSuchAlgorithmException e) { }
		return null;
	}
	private static String bytesToHex(byte[] bytes)
	{
		StringBuilder hexBytes = new StringBuilder();
		for (byte currByte : bytes)
		{
			hexBytes.append(String.format("%02x", currByte)); //Print byte as hex with @least 2 chars by prepending a leading 0 if necessary
		}
		return hexBytes.toString();
	}
	public static boolean isStudent(HttpSession session)
	{
		Usuario user = getUser(session);
		return user != null && user.esEstudiante();
	}
	public static boolean isCompany(HttpSession session)
	{
		Usuario user = getUser(session);
		return user != null && user.esEmpresa();
	}
	public static boolean isAdmin(HttpSession session)
	{
		Usuario user = getUser(session);
		return user != null && user.esAdmin();
	}
	public static Usuario getUser(HttpSession session)
	{
		return (Usuario)session.getAttribute("user");
	}
	public static boolean validateUser(Usuario user, BindingResult bindingResult)
	{
		return validate(new LoginValidator(), user, bindingResult);
	}
	public static boolean validate(Validator validator, Object obj, BindingResult bindingResult)
	{
		validator.validate(obj, bindingResult);
		return !bindingResult.hasErrors();
	}
	public static void debugLog(String message)
	{
		if (DEBUG)
		{
			System.out.println("[DEBUG] " + message);
		}
	}
}
