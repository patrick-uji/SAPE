package es.uji.ei1027.sape.dao.dto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.enums.Itinerario;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.model.PersonaContacto;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.dto.OfertaProyectoDTO;
import es.uji.ei1027.sape.mappers.EmpresaMapper;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
import es.uji.ei1027.sape.mappers.PersonaContactoMapper;
@Component
public class OfertaProyectoDTODao extends AbstractDTODao<OfertaProyectoDTO>
{
	private static final String BASE_QUERY = "SELECT o.*, " + PersonaContacto.SELECT_JOIN + ", " + Empresa.SELECT_JOIN + 
											" FROM OfertaProyecto AS o " + Empresa.FROM_OFFER_JOIN;
	private PersonaContactoMapper contactPersonMapper;
	private OfertaProyectoMapper offerMapper;
	private EmpresaMapper companyMapper;
	public OfertaProyectoDTODao()
	{
		this.contactPersonMapper = new PersonaContactoMapper("p_");
		this.offerMapper = new OfertaProyectoMapper();
		this.companyMapper = new EmpresaMapper("e_");
	}
	@Override
	public OfertaProyectoDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		OfertaProyectoDTO ofertaProyectoDTO = new OfertaProyectoDTO();
		ofertaProyectoDTO.setPersonaContacto(contactPersonMapper.mapRow(resultSet, rowNum));
		ofertaProyectoDTO.setEmpresa(companyMapper.mapRow(resultSet, rowNum));
		offerMapper.mapRow(ofertaProyectoDTO, resultSet, rowNum);
        return ofertaProyectoDTO;
	}
	@Override
	public OfertaProyectoDTO get(int id)
	{
        return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE o.id = ?", new Object[] {id}, this);
	}
	@Override
	public List<OfertaProyectoDTO> getAll()
	{
    	return jdbcTemplate.query(BASE_QUERY + " ORDER BY id DESC", this);
	}
    public List<OfertaProyectoDTO> getAllPending()
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta IN (?,?) ORDER BY id DESC", new Object[] {EstadoOferta.INTRODUCIDA.getID(), EstadoOferta.PENDIENTE_REVISION.getID()}, this);
    }
    public List<OfertaProyectoDTO> getAllCancelRequested()
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta = ? ORDER BY id DESC", new Object[] {EstadoOferta.PENDIENTE_ANULACION.getID()}, this);
    }
    public List<OfertaProyectoDTO> getAllAccepted()
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta IN (?,?,?) ORDER BY id DESC", new Object[] {EstadoOferta.ACEPTADA.getID(), EstadoOferta.VISIBLE.getID(), EstadoOferta.ASIGNADA.getID()}, this);
    }
    public List<OfertaProyectoDTO> getAllChoosable(int studentID, Itinerario itinerary)
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta = ? AND id_Itinerario = ? AND o.id NOT IN (SELECT id_OfertaProyecto FROM PreferenciaAlumno WHERE id_Alumno = ?) ORDER BY id DESC",
    							  new Object[] {EstadoOferta.VISIBLE.getID(), itinerary.getID(), studentID}, this);
    }
}
