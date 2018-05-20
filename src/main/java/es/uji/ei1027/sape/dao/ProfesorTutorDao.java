package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.ProfesorTutor;
import org.springframework.stereotype.Component;
@Component
public class ProfesorTutorDao extends AbstractDao<ProfesorTutor>
{
	@Override
    public ProfesorTutor mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	ProfesorTutor profesorTutor = new ProfesorTutor();
    	profesorTutor.setId(resultSet.getInt("id"));
    	profesorTutor.setNombre(resultSet.getString("nombre"));
    	profesorTutor.setDepartamento(resultSet.getString("departamento"));
    	profesorTutor.setDespacho(resultSet.getString("despacho"));
    	profesorTutor.setEmail(resultSet.getString("email"));
        return profesorTutor;
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
        jdbcTemplate.update("UPDATE ProfesorTutor SET orden = ?, abierta = ?, fechaUltimoCambio = ? WHERE id = ?",
							model.getNombre(), model.getDepartamento(), model.getDespacho(), model.getEmail(),
        					model.getId());
    }
}
