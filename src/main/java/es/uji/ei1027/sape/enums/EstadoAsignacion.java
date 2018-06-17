package es.uji.ei1027.sape.enums;
public enum EstadoAsignacion
{
	ENVIADA(1, "Enviada"),
	ACEPTADA(2, "Aceptada"),
	RECHAZADA(3, "Rechazada"),
	ANULADA(4, "Anulada");
	
	private int id;
	private String name;
	private EstadoAsignacion(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	public int getID()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public static EstadoAsignacion fromID(int id)
	{
		for (EstadoAsignacion currEnum : values())
		{
			if (currEnum.id == id)
			{
				return currEnum;
			}
		}
		return null;
	}
}
