package es.uji.ei1027.sape.domain;
public class VisibleOffer
{
	private int id;
	private boolean ignore;
	private boolean visible;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public boolean getIgnore()
	{
		return ignore;
	}
	public void setIgnore(boolean ignore)
	{
		this.ignore = ignore;
	}
	public boolean getVisible()
	{
		return visible;
	}
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}
