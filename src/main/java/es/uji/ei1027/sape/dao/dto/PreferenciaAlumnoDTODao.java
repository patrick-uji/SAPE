package es.uji.ei1027.sape.dao.dto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.dto.PreferenciaAlumnoDTO;
import es.uji.ei1027.sape.mappers.EmpresaMapper;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
import es.uji.ei1027.sape.mappers.PreferenciaAlumnoMapper;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.model.OfertaProyecto;
@Component
public class PreferenciaAlumnoDTODao extends AbstractDTODao<PreferenciaAlumnoDTO>
{
	//https://stackoverflow.com/questions/2504887/dao-design-pattern-and-using-it-across-multiple-tables
	private static final String BASE_QUERY = "SELECT pa.*, " + OfertaProyecto.SELECT_JOIN + ", " + Empresa.SELECT_JOIN +
											" FROM PreferenciaAlumno AS pa JOIN OfertaProyecto AS o ON pa.id_OfertaProyecto = o.id " +
											  Empresa.FROM_OFFER_JOIN;
	private PreferenciaAlumnoMapper preferenceMapper;
	private OfertaProyectoMapper offerMapper;
	private EmpresaMapper companyMapper;
	public PreferenciaAlumnoDTODao()
	{
		this.preferenceMapper = new PreferenciaAlumnoMapper();
		this.offerMapper = new OfertaProyectoMapper("o_");
		this.companyMapper = new EmpresaMapper("e_");
	}
	@Override
	public PreferenciaAlumnoDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		PreferenciaAlumnoDTO preferenciaAlumnoDTO = new PreferenciaAlumnoDTO();
		preferenciaAlumnoDTO.setOfertaProyecto(offerMapper.mapRow(resultSet, rowNum));
		preferenciaAlumnoDTO.setEmpresa(companyMapper.mapRow(resultSet, rowNum));
		preferenceMapper.mapRow(preferenciaAlumnoDTO, resultSet, rowNum);
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
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_Alumno = ? ORDER BY orden", new Object[] {studentID}, this);
	}
}
