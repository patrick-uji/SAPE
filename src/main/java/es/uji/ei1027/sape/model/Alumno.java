package es.uji.ei1027.sape.model;

import es.uji.ei1027.sape.enums.Itinerario;

public class Alumno extends ObjetoIdentificado
{
	private String dni;
	private String nombre;
	private float notaMedia;
	private Itinerario itinerario;
	private int numeroCreditos;
	private int asignaturasPendientes;
	private int semestreInicioEstancia;
	public String getDni()
	{
		return dni;
	}
	public void setDni(String dni)
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
	public Itinerario getItinerario()
	{
		return itinerario;
	}
	public void setItinerario(Itinerario itinerario)
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
