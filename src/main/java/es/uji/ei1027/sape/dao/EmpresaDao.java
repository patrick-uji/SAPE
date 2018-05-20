package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Empresa;
import org.springframework.stereotype.Component;
@Component
public class EmpresaDao extends AbstractDao<Empresa>
{
    @Override
    public Empresa mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(resultSet.getInt("id"));
    	empresa.setCIF(resultSet.getString("cif"));
    	empresa.setNombre(resultSet.getString("nombre"));
    	empresa.setDomicilio(resultSet.getString("domicilio"));
    	empresa.setTelefonoPrincipal(resultSet.getString("telefonoPrincipal"));
        return empresa;
    }
    public Empresa get(String cif)
    {
    	return jdbcTemplate.queryForObject("SELECT * FROM Empresa WHERE cif = ?", new Object[] {cif.toUpperCase()}, this);
    }
    @Override
    public void create(Empresa model)
    {
        jdbcTemplate.update("INSERT INTO Empresa VALUES (?,?,?,?,?)",
        					model.getId(), model.getCIF(), model.getNombre(), model.getDomicilio(), model.getTelefonoPrincipal());
    }
    @Override
    public void update(Empresa model)
    {
        jdbcTemplate.update("UPDATE Empresa SET cif = ?, nombre = ?, domicilio = ?, telefonoPrincipal = ? WHERE id = ?",
        					model.getCIF(), model.getNombre(), model.getDomicilio(), model.getTelefonoPrincipal(), 
        					model.getId());
    }
}
