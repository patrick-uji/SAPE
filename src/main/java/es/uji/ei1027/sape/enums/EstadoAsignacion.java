package es.uji.ei1027.sape.enums;
public enum EstadoAsignacion
{
	ACEPTADA(1),
	RECHAZADA(2),
	TRASPASADA(3);
	
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
