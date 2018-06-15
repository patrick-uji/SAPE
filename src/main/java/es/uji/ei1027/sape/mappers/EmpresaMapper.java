package es.uji.ei1027.sape.mappers;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Empresa;
import org.springframework.jdbc.core.RowMapper;
public class EmpresaMapper extends PrefixableMapper implements RowMapper<Empresa>
{
	public EmpresaMapper() { }
	public EmpresaMapper(String prefix)
	{
		super(prefix);
	}
	@Override
    public Empresa mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
		Empresa empresa = new Empresa();
    	empresa.setId(resultSet.getInt(prefix + "id"));
    	empresa.setCif(resultSet.getString(prefix + "cif"));
    	empresa.setNombre(resultSet.getString(prefix + "nombre"));
    	empresa.setDomicilio(resultSet.getString(prefix + "domicilio"));
    	empresa.setProyectosTotal(resultSet.getInt(prefix + "proyectosTotal"));
    	empresa.setTelefonoPersonal(resultSet.getString(prefix + "telefonoPersonal"));
        return empresa;
    }
}
