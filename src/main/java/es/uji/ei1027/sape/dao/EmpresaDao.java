package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uji.ei1027.sape.mappers.EmpresaMapper;
import es.uji.ei1027.sape.model.Empresa;
import org.springframework.stereotype.Component;
@Component
public class EmpresaDao extends AbstractDao<Empresa>
{
	private EmpresaMapper rowMapper;
	public EmpresaDao()
	{
		this.rowMapper = new EmpresaMapper();
	}
    @Override
    public Empresa mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	return rowMapper.mapRow(resultSet, rowNum);
    }
    public Empresa get(String cif)
    {
    	return jdbcTemplate.queryForObject("SELECT * FROM Empresa WHERE cif = ?", new Object[] {cif.toUpperCase()}, this);
    }
    @Override
    public void create(Empresa model)
    {
        jdbcTemplate.update("INSERT INTO Empresa VALUES (?,?,?,?,?)",
        					model.getId(), model.getCIF(), model.getNombre(), model.getDomicilio(), model.getTelefonoPersonal());
    }
    @Override
    public void update(Empresa model)
    {
        jdbcTemplate.update("UPDATE Empresa SET cif = ?, nombre = ?, domicilio = ?, telefonoPersonal = ? WHERE id = ?",
        					model.getCIF(), model.getNombre(), model.getDomicilio(), model.getTelefonoPersonal(), 
        					model.getId());
    }
}
