package es.uji.ei1027.sape.enums;
public enum Itinerario
{
	INGENIERIA_SOFTWARE(1, "Ingeniería del Software"),
	SISTEMAS_INFORMACION(2, "Sistemas de Información"),
	TECNOLOGIAS_INFORMACION(3, "Tecnologías de la Información"),
	INGENIERIA_COMPUTADORES(4, "Ingeniería de computadores");
	
	private int id;
	private String name;
	private Itinerario(int id, String name)
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
	public static Itinerario fromID(int id)
	{
		for (Itinerario currEnum : values())
		{
			if (currEnum.id == id)
			{
				return currEnum;
			}
		}
		return null;
	}
}
