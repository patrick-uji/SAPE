package es.uji.ei1027.sape.dao;
import es.uji.ei1027.sape.model.ObjetoIdentificado;
public abstract class OwnedResourceDao<T extends ObjetoIdentificado> extends AbstractDao<T>
{
	public abstract boolean owns(int id, int ownerID);
}
