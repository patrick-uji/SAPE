package es.uji.ei1027.sape;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import java.lang.reflect.ParameterizedType;
import java.security.NoSuchAlgorithmException;
import org.springframework.validation.Validator;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import es.uji.ei1027.sape.validation.LoginValidator;
public class Utils
{
	private static final boolean DEBUG = true;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	public static boolean isEmptyString(String string)
	{
		return string != null && string.trim().equals("");
	}
	public static String safeFormatDate(Date date)
	{
		return date != null ? formatDate(date) : null;
	}
	public static String formatDate(Date date)
	{
		return DATE_FORMAT.format(date);
	}
	public static Date stringToDate(String string)
	{
		if (string != null && string != "")
		{
			try
			{
				return new Date(DATE_FORMAT.parse(string).getTime());
			}
			catch (ParseException ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}
	public static String now()
	{
		return formatDate(nowDate());
	}
	public static Date nowDate()
	{
		return new Date(Calendar.getInstance().getTime().getTime());
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
		catch (NoSuchAlgorithmException ex) { }
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
	public static <T> T[] appendArray(T[] array, T value)
	{
		T[] newArray = Arrays.copyOf(array, array.length + 1);
		newArray[array.length] = value;
		return newArray;
	}
	@SafeVarargs
	public static <T> T[] appendArray(T[] array, T... appendArray)
	{
		int currNewIndex = array.length;
		T[] newArray = Arrays.copyOf(array, array.length + appendArray.length);
		for (int currIndex = 0; currIndex < appendArray.length; currIndex++)
		{
			newArray[currNewIndex] = appendArray[currIndex];
			currNewIndex++;
		}
		return newArray;
	}
	public static boolean isStudent(HttpSession session)
	{
		return isStudent(getUser(session));
	}
	public static boolean isStudent(Usuario user)
	{
		return user != null && user.esAlumno();
	}
	public static boolean isCompany(HttpSession session)
	{
		return isCompany(getUser(session));
	}
	public static boolean isCompany(Usuario user)
	{
		return user != null && user.esEmpresa();
	}
	public static boolean isAdmin(HttpSession session)
	{
		return isAdmin(getUser(session));
	}
	public static boolean isAdmin(Usuario user)
	{
		return user != null && user.esAdmin();
	}
	public static boolean isSuperAdmin(HttpSession session)
	{
		return isSuperAdmin(getUser(session));
	}
	public static boolean isSuperAdmin(Usuario user)
	{
		return user != null && user.esSuperAdmin();
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
	public static void setupCreateModel(Model model)
	{
        model.addAttribute("action", "Crear");
        model.addAttribute("target", "");
	}
	public static void setupUpdateModel(Model model, int id)
	{
        model.addAttribute("target", "/" + id + "/update");
        model.addAttribute("action", "Actualizar");
	}
	public static void debugLog(String message)
	{
		if (DEBUG)
		{
			System.out.println("[DEBUG] " + message);
		}
	}
}
