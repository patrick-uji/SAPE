package es.uji.ei1027.sape.enums;
public enum EstadoOferta
{
	SIN_DEFINIR(1, "Sin definir"),
	INTRODUCIDA(2, "Introducida"),
	PENDIENTE_REVISION(3, "Pendiente revision"),
	ACEPTADA(4, "Aceptada"),
	RECHAZADA(5, "Rechazada"),
	VISIBLE(6, "Visible"),
	ASIGNADA(7, "Asignada");
	
	private int id;
	private String name;
	private EstadoOferta(int id, String name)
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
	public static EstadoOferta fromID(int id)
	{
		for (EstadoOferta currEnum : values())
		{
			if (currEnum.id == id)
			{
				return currEnum;
			}
		}
		return null;
	}
}
