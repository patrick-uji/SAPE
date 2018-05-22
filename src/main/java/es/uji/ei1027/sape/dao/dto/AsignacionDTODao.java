package es.uji.ei1027.sape.dao.dto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.dto.AsignacionDTO;
import es.uji.ei1027.sape.enums.EstadoAsignacion;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.mappers.AsignacionMapper;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
import es.uji.ei1027.sape.model.Asignacion;
@Component
public class AsignacionDTODao extends AbstractDTODao<AsignacionDTO>
{
	private static final String BASE_QUERY = "SELECT * FROM Asignacion AS a JOIN OfertaProyecto AS o ON a.id_OfertaProyecto = o.id";
	private OfertaProyectoMapper offerMapper;
	private AsignacionMapper assignmentMapper;
	public AsignacionDTODao()
	{
		this.offerMapper = new OfertaProyectoMapper();
		this.assignmentMapper = new AsignacionMapper();
	}
	@Override
	public AsignacionDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		AsignacionDTO asignacionDTO = new AsignacionDTO();
		assignmentMapper.mapRow(asignacionDTO, resultSet, rowNum);
		asignacionDTO.setOfertaProyecto(offerMapper.mapRow(resultSet, rowNum));
        return asignacionDTO;
	}
	@Override
	public AsignacionDTO get(int id)
	{
        return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE a.id = ?", new Object[] {id}, this);
	}
    public Asignacion getActiveFromStudent(int studentID)
    {
    	try
		{
        	return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE id_Estudiante = ? AND id_EstadoAsignacion != ?", new Object[] {studentID, EstadoAsignacion.RECHAZADA.getID()}, this);
		}
		catch (EmptyResultDataAccessException ex)
		{
			return null;
		}
    }
	@Override
	public List<AsignacionDTO> getAll()
	{
    	return jdbcTemplate.query(BASE_QUERY, this);
	}
}
