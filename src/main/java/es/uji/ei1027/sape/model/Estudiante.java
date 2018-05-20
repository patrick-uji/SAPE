package es.uji.ei1027.sape.model;
public class Estudiante extends ObjetoIdentificado
{
	private String dni;
	private String nombre;
	private float notaMedia;
	private String itinerario;
	private int numeroCreditos;
	private int asignaturasPendientes;
	private int semestreInicioEstancia;
	public String getDNI()
	{
		return dni;
	}
	public void setDNI(String dni)
	{
		this.dni = dni;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public float getNotaMedia()
	{
		return notaMedia;
	}
	public void setNotaMedia(float notaMedia)
	{
		this.notaMedia = notaMedia;
	}
	public String getItinerario()
	{
		return itinerario;
	}
	public void setItinerario(String itinerario)
	{
		this.itinerario = itinerario;
	}
	public int getNumeroCreditos()
	{
		return numeroCreditos;
	}
	public void setNumeroCreditos(int numeroCreditos)
	{
		this.numeroCreditos = numeroCreditos;
	}
	public int getAsignaturasPendientes()
	{
		return asignaturasPendientes;
	}
	public void setAsignaturasPendientes(int asignaturasPendientes)
	{
		this.asignaturasPendientes = asignaturasPendientes;
	}
	public int getSemestreInicioEstancia()
	{
		return semestreInicioEstancia;
	}
	public void setSemestreInicioEstancia(int semestreInicioEstancia)
	{
		this.semestreInicioEstancia = semestreInicioEstancia;
	}
}
