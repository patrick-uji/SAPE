package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.PersonaContacto;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.mappers.PersonaContactoMapper;
@Component
public class PersonaContactoDao extends AbstractDao<PersonaContacto>
{
	private PersonaContactoMapper rowMapper;
	public PersonaContactoDao()
	{
		this.rowMapper = new PersonaContactoMapper();
	}
	@Override
    public PersonaContacto mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
		return rowMapper.mapRow(resultSet, rowNum);
    }
    public List<PersonaContacto> getAllFromCompany(int companyID)
    {
    	return jdbcTemplate.query("SELECT * FROM PersonaContacto WHERE id_Empresa = ? ORDER BY id DESC", new Object[] {companyID}, this);
    }
	@Override
    public void create(PersonaContacto model)
    {
        jdbcTemplate.update("INSERT INTO PersonaContacto (nombre, email, descripcionPracticas, id_Empresa) VALUES (?,?,?,?)",
        					model.getNombre(), model.getEmail(), model.getDescripcionPracticas(), model.getIDEmpresa());
    }
	@Override
    public void update(PersonaContacto model)
    {
        jdbcTemplate.update("UPDATE PersonaContacto SET nombre = ?, email = ?, descripcionPracticas = ?, id_Empresa = ? WHERE id = ?",
        					model.getNombre(), model.getEmail(), model.getDescripcionPracticas(), model.getIDEmpresa(),
        					model.getId());
    }
}
