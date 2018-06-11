package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.ProfesorTutor;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.mappers.ProfesorTutorMapper;
@Component
public class ProfesorTutorDao extends AbstractDao<ProfesorTutor>
{
	private ProfesorTutorMapper rowMapper;
	public ProfesorTutorDao()
	{
		this.rowMapper = new ProfesorTutorMapper();
	}
    @Override
    public ProfesorTutor mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	return rowMapper.mapRow(resultSet, rowNum);
    }
	@Override
    public void create(ProfesorTutor model)
    {
        jdbcTemplate.update("INSERT INTO ProfesorTutor (nombre, departamento, despacho, email) VALUES (?,?,?,?)",
        					model.getNombre(), model.getDepartamento(), model.getDespacho(), model.getEmail());
    }
	@Override
    public void update(ProfesorTutor model)
    {
        jdbcTemplate.update("UPDATE ProfesorTutor SET nombre = ?, departamento = ?, despacho = ?, email = ? WHERE id = ?",
							model.getNombre(), model.getDepartamento(), model.getDespacho(), model.getEmail(),
        					model.getId());
    }
}
