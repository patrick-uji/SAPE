package es.uji.ei1027.sape.dao.dto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import es.uji.ei1027.sape.dto.OfertaProyectoDTO;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.mappers.EmpresaMapper;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
import es.uji.ei1027.sape.mappers.PersonaContactoMapper;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.model.PersonaContacto;
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
    	return jdbcTemplate.query(BASE_QUERY, this);
	}
    public List<OfertaProyectoDTO> getAllPending()
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta IN (?,?)", new Object[] {EstadoOferta.INTRODUCIDA.getID(), EstadoOferta.PENDIENTE_REVISION.getID()}, this);
    }
    public List<OfertaProyectoDTO> getAllCancelRequested()
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta = ?", new Object[] {EstadoOferta.PENDIENTE_ANULACION.getID()}, this);
    }
    public List<OfertaProyectoDTO> getAllAccepted()
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta NOT IN (?,?,?)", new Object[] {EstadoOferta.INTRODUCIDA.getID(), EstadoOferta.PENDIENTE_REVISION.getID(), EstadoOferta.RECHAZADA.getID()}, this);
    }
    public List<OfertaProyectoDTO> getAllChoosable(int studentID)
    {
    	//TODO: show the ones related to the student's itinerary
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta = ? AND p.id NOT IN (SELECT id_OfertaProyecto FROM PreferenciaAlumno WHERE id_Alumno = ?)",
    							  new Object[] {EstadoOferta.ACEPTADA.getID(), studentID}, this);
    }
}
