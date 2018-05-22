package es.uji.ei1027.sape.dto;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.model.OfertaProyecto;
public class AsignacionDTO extends Asignacion
{
	private OfertaProyecto ofertaProyecto;
	public OfertaProyecto getOfertaProyecto()
	{
		return ofertaProyecto;
	}
	public void setOfertaProyecto(OfertaProyecto ofertaProyecto)
	{
		this.ofertaProyecto = ofertaProyecto;
	}
}
