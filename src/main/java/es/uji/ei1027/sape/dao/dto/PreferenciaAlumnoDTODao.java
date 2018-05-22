package es.uji.ei1027.sape.dao.dto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.dto.PreferenciaAlumnoDTO;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
import es.uji.ei1027.sape.mappers.PreferenciaAlumnoMapper;
@Component
public class PreferenciaAlumnoDTODao extends AbstractDTODao<PreferenciaAlumnoDTO>
{
	//https://stackoverflow.com/questions/2504887/dao-design-pattern-and-using-it-across-multiple-tables
	private static final String BASE_QUERY = "SELECT * FROM PreferenciaAlumno AS p JOIN OfertaProyecto AS o ON p.id_OfertaProyecto = o.id";
	private PreferenciaAlumnoMapper studentMapper;
	private OfertaProyectoMapper offerMapper;
	public PreferenciaAlumnoDTODao()
	{
		this.studentMapper = new PreferenciaAlumnoMapper();
		this.offerMapper = new OfertaProyectoMapper();
	}
	@Override
	public PreferenciaAlumnoDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		PreferenciaAlumnoDTO preferenciaAlumnoDTO = new PreferenciaAlumnoDTO();
		preferenciaAlumnoDTO.setOfertaProyecto(offerMapper.mapRow(resultSet, rowNum));
		studentMapper.mapRow(preferenciaAlumnoDTO, resultSet, rowNum);
        return preferenciaAlumnoDTO;
	}
	@Override
	public PreferenciaAlumnoDTO get(int id)
	{
        return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE id = ?", new Object[] {id}, this);
	}
	@Override
	public List<PreferenciaAlumnoDTO> getAll()
	{
    	return jdbcTemplate.query(BASE_QUERY, this);
	}
	public List<PreferenciaAlumnoDTO> getAllFromStudent(int studentID)
	{
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_Estudiante = ? ORDER BY orden", new Object[] {studentID}, this);
	}
}
