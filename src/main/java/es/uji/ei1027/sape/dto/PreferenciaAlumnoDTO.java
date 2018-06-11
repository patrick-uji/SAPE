package es.uji.ei1027.sape.dto;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PreferenciaAlumno;
public class PreferenciaAlumnoDTO extends PreferenciaAlumno
{
	private Empresa empresa;
	private OfertaProyecto ofertaProyecto;
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public OfertaProyecto getOfertaProyecto()
	{
		return ofertaProyecto;
	}
	public void setOfertaProyecto(OfertaProyecto ofertaProyecto)
	{
		this.ofertaProyecto = ofertaProyecto;
	}
}
