package es.uji.ei1027.sape.enums;
public enum EstadoAsignacion
{
	ENVIADA(1),
	ACEPTADA(2),
	RECHAZADA(3);
	
	private int id;
	private EstadoAsignacion(int id)
	{
		this.id = id;
	}
	public int getID()
	{
		return id;
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
