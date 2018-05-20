package es.uji.ei1027.sape.dto;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PreferenciaAlumno;
public class PreferenciaAlumnoDTO extends PreferenciaAlumno
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
