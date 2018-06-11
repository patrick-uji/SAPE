package es.uji.ei1027.sape.mappers;
public abstract class PrefixableMapper
{
	protected String prefix;
	public PrefixableMapper()
	{
		this.prefix = "";
	}
	public PrefixableMapper(String prefix)
	{
		this.prefix = prefix;
	}
}
