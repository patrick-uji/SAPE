package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Estancia;
import org.springframework.stereotype.Component;
@Component
public class EstanciaDao extends AbstractDao<Estancia>
{
	@Override
    public Estancia mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	Estancia estancia = new Estancia();
    	estancia.setId(resultSet.getInt("id"));
    	estancia.setPersonaContacto(resultSet.getString("personaContacto"));
    	estancia.setEmailPersonaContacto(resultSet.getString("emailPersonaContacto"));
    	estancia.setDescripcionPracticas(resultSet.getString("descripcionPracticas"));
    	estancia.setIDEmpresa(resultSet.getInt("id_Empresa"));
        return estancia;
    }
    public List<Estancia> getAllFromCompany(int companyID)
    {
    	return jdbcTemplate.query("SELECT * FROM Estancia WHERE id_Empresa = ?", new Object[] {companyID}, this);
    }
	@Override
    public void create(Estancia model)
    {
        jdbcTemplate.update("INSERT INTO Estancia (personaContacto, emailPersonaContacto, descripcionPracticas, id_Empresa) VALUES (?,?,?,?)",
        					model.getPersonaContacto(), model.getEmailPersonaContacto(), model.getDescripcionPracticas(), model.getIDEmpresa());
    }
	@Override
    public void update(Estancia model)
    {
        jdbcTemplate.update("UPDATE Estancia SET personaContacto = ?, emailPersonaContacto = ?, descripcionPracticas = ?, id_Empresa = ? WHERE id = ?",
        					model.getPersonaContacto(), model.getEmailPersonaContacto(), model.getDescripcionPracticas(), model.getIDEmpresa(),
        					model.getId());
    }
}
