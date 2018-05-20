package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Admin;
import org.springframework.stereotype.Component;
@Component
public class AdminDao extends AbstractDao<Admin>
{
	@Override
	public Admin mapRow(ResultSet resultSet, int rowNumber) throws SQLException
	{
		Admin admin = new Admin();
		admin.setId(resultSet.getInt("id"));
		return admin;
	}
	@Override
	public void create(Admin model)
	{
		jdbcTemplate.update("UPDATE Admin SET ... WHERE id = ?",
							
							model.getId());
	}
	@Override
	public void update(Admin model)
	{
		jdbcTemplate.update("INSERT INTO Admin VALUES (?)", model.getId());
	}
}
