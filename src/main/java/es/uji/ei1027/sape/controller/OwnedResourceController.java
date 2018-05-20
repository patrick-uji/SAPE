package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.model.ObjetoIdentificado;
public abstract class OwnedResourceController<T extends ObjetoIdentificado> extends AbstractController<T>
{
	public abstract boolean ownsResource(int id);
}
