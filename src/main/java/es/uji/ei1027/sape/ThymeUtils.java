package es.uji.ei1027.sape;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
public class ThymeUtils
{
	public static boolean canEditOffer(OfertaProyecto offer)
	{
		switch (offer.getEstado())
		{
			case INTRODUCIDA:
			case PENDIENTE_REVISION: return true;
			default: return false;
		}
	}
	public static boolean canDeleteOffer(OfertaProyecto offer)
	{
		switch (offer.getEstado())
		{
			case ACEPTADA:
			case VISIBLE:
			case ASIGNADA:
			case PENDIENTE_ANULACION: return false;
			default: return true;
		}
	}
	public static boolean canCancelOffer(OfertaProyecto offer)
	{
		switch (offer.getEstado())
		{
			case ACEPTADA:
			case VISIBLE:
			case ASIGNADA: return true;
			default: return false;
		}
	}
	public static boolean canAcceptDeclineOffer(OfertaProyecto offer)
	{
		switch (offer.getEstado())
		{
			case INTRODUCIDA:
			case PENDIENTE_REVISION: return true;
			default: return false;
		}
	}
	public static boolean canHideShowOffer(OfertaProyecto offer)
	{
		switch (offer.getEstado())
		{
			case ACEPTADA:
			case VISIBLE: return true;
			default: return false;
		}
	}
	public static boolean canEditAssignment(Asignacion assignment, Usuario user)
	{
		return user.esSuperAdmin() && canEditAssignment(assignment);
	}
	public static boolean canEditAssignment(Asignacion assignment)
	{
		EstadoAsignacion assignmentStatus = assignment.getEstado();
		return assignmentStatus == null || assignmentStatus == EstadoAsignacion.ENVIADA;
	}
	public static boolean canCancelAssignment(Asignacion assignment)
	{
		switch (assignment.getEstado())
		{
			case ENVIADA:
			case ACEPTADA: return true;
			default: return false;
		}
	}
}
