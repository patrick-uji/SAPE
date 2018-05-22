package es.uji.ei1027.sape.dto;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.model.OfertaProyecto;
public class OfertaProyectoDTO extends OfertaProyecto
{
	private Empresa empresa;
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
}
