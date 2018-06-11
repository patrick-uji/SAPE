package es.uji.ei1027.sape.enums;
public enum TipoUsuario
{
	ALUMNO(1),
	EMPRESA(2),
	CCD(3),
	BTC(4);
	
	private int id;
	private TipoUsuario(int id)
	{
		this.id = id;
	}
	public int getID()
	{
		return id;
	}
	public static TipoUsuario fromID(int id)
	{
		for (TipoUsuario currEnum : values())
		{
			if (currEnum.id == id)
			{
				return currEnum;
			}
		}
		return null;
	}
}
