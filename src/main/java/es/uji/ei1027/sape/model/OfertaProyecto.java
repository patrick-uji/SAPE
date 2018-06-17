package es.uji.ei1027.sape.model;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.enums.Itinerario;
public class OfertaProyecto extends ObjetoIdentificado
{
	public static final String SELECT_JOIN = "o.id AS o_id, o.numero AS o_numero, o.titulo AS o_titulo, o.id_PersonaContacto AS o_id_PersonaContacto, o.objetivo AS o_objetivo, o.fechaAlta AS o_fechaAlta, " +
											 "o.id_EstadoOferta AS o_id_EstadoOferta, o.id_EstadoOfertaPreAnulacion AS o_id_EstadoOfertaPreAnulacion, o.id_Itinerario AS o_id_Itinerario, o.fechaUltimoCambio AS o_fechaUltimoCambio";
	private int numero;
	private String titulo;
	private int idPersonaContacto;
	private String objetivo;
	private String fechaAlta;
	private EstadoOferta estado;
	private Itinerario itinerario;
	private String fechaUltimoCambio;
	private EstadoOferta estadoPreAnulacion;
	public int getNumero()
	{
		return numero;
	}
	public void setNumero(int numero)
	{
		this.numero = numero;
	}
	public String getTitulo()
	{
		return titulo;
	}
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	public int getIdPersonaContacto()
	{
		return idPersonaContacto;
	}
	public void setIdPersonaContacto(int idPersonaContacto)
	{
		this.idPersonaContacto = idPersonaContacto;
	}
	public String getObjetivo()
	{
		return objetivo;
	}
	public void setObjetivo(String objetivo)
	{
		this.objetivo = objetivo;
	}
	public String getFechaAlta()
	{
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta)
	{
		this.fechaAlta = fechaAlta;
	}
	public EstadoOferta getEstado()
	{
		return estado;
	}
	public void setEstado(EstadoOferta estado)
	{
		this.estado = estado;
	}
	public Itinerario getItinerario()
	{
		return itinerario;
	}
	public void setItinerario(Itinerario itinerario)
	{
		this.itinerario = itinerario;
	}
	public String getFechaUltimoCambio()
	{
		return fechaUltimoCambio;
	}
	public void setFechaUltimoCambio(String fechaUltimoCambio)
	{
		this.fechaUltimoCambio = fechaUltimoCambio;
	}
	public EstadoOferta getEstadoPreAnulacion()
	{
		return estadoPreAnulacion;
	}
	public void setEstadoPreAnulacion(EstadoOferta estadoPreAnulacion)
	{
		this.estadoPreAnulacion = estadoPreAnulacion;
	}
}
