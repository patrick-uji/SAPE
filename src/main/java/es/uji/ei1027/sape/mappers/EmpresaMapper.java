package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Empresa;
import org.springframework.jdbc.core.RowMapper;
public class EmpresaMapper implements RowMapper<Empresa>
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
}
