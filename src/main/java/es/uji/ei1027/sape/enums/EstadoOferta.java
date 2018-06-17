package es.uji.ei1027.sape.enums;
public enum EstadoOferta
{
	INTRODUCIDA(1, "Introducida"),
	PENDIENTE_REVISION(2, "Pendiente revisión"),
	ACEPTADA(3, "Aceptada"),
	VISIBLE(4, "Visible"),
	ASIGNADA(5, "Asignada"),
	RECHAZADA(6, "Rechazada"),
	PENDIENTE_ANULACION(7, "Pendiente anulación"),
	ANULADA(8, "Anulada");
	
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
