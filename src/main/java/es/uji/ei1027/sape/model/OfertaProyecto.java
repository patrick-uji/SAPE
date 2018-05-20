package es.uji.ei1027.sape.model;
import es.uji.ei1027.sape.enums.EstadoOferta;
public class OfertaProyecto extends ObjetoIdentificado
{
	private int numero;
	private String tarea;
	private String objetivo;
	private EstadoOferta estado;
	private String itinerario;
	private String fechaAlta;
	private String fechaUltimoCambio;
	private int idEstancia;
	public int getNumero()
	{
		return numero;
	}
	public void setNumero(int numero)
	{
		this.numero = numero;
	}
	public String getTarea()
	{
		return tarea;
	}
	public void setTarea(String tarea)
	{
		this.tarea = tarea;
	}
	public String getObjetivo()
	{
		return objetivo;
	}
	public void setObjetivo(String objetivo)
	{
		this.objetivo = objetivo;
	}
	public EstadoOferta getEstado()
	{
		return estado;
	}
	public void setEstado(EstadoOferta estado)
	{
		this.estado = estado;
	}
	public String getItinerario()
	{
		return itinerario;
	}
	public void setItinerario(String itinerario)
	{
		this.itinerario = itinerario;
	}
	public String getFechaAlta()
	{
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta)
	{
		this.fechaAlta = fechaAlta;
	}
	public String getFechaUltimoCambio()
	{
		return fechaUltimoCambio;
	}
	public void setFechaUltimoCambio(String fechaUltimoCambio)
	{
		this.fechaUltimoCambio = fechaUltimoCambio;
	}
	public int getIdEstancia()
	{
		return idEstancia;
	}
	public void setIdEstancia(int idEstancia)
	{
		this.idEstancia = idEstancia;
	}
}
