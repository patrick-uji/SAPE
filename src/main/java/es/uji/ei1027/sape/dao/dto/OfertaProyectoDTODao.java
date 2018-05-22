package es.uji.ei1027.sape.dao.dto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import es.uji.ei1027.sape.dto.OfertaProyectoDTO;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.mappers.EmpresaMapper;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
@Component
public class OfertaProyectoDTODao extends AbstractDTODao<OfertaProyectoDTO>
{
	private static final String BASE_QUERY = "SELECT * FROM OfertaProyecto AS o JOIN Estancia AS es ON o.id_Estancia = es.id JOIN Empresa AS em ON es.id_Empresa = em.id";
	private OfertaProyectoMapper offerMapper;
	private EmpresaMapper companyMapper;
	public OfertaProyectoDTODao()
	{
		this.offerMapper = new OfertaProyectoMapper();
		this.companyMapper = new EmpresaMapper();
	}
	@Override
	public OfertaProyectoDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		OfertaProyectoDTO ofertaProyectoDTO = new OfertaProyectoDTO();
		offerMapper.mapRow(ofertaProyectoDTO, resultSet, rowNum);
		ofertaProyectoDTO.setEmpresa(companyMapper.mapRow(resultSet, rowNum));
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
    public List<OfertaProyectoDTO> getAllAccepted()
    {
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_EstadoOferta = ?", new Object[] {EstadoOferta.ACEPTADA.getID()}, this);
    }
	/*
	public List<OfertaProyectoDTO> getAllFromStudent(int studentID)
	{
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_Estudiante = ? ORDER BY orden", new Object[] {studentID}, this);
	}
	*/
}
