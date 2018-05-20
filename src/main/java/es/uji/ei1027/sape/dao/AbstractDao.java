package es.uji.ei1027.sape.dao;
import java.util.List;
import java.util.Arrays;
import javax.sql.DataSource;
import es.uji.ei1027.sape.Utils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import es.uji.ei1027.sape.model.ObjetoIdentificado;
import org.springframework.beans.factory.annotation.Autowired;
public abstract class AbstractDao<T extends ObjetoIdentificado> implements RowMapper<T>
{
	private String tName;
    protected JdbcTemplate jdbcTemplate;
    public AbstractDao()
	{
    	this.tName = Utils.getTClass(this).getSimpleName();
	}
    @Autowired
    public final void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public final T get(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM " + tName + " WHERE id = ?", new Object[] {id}, this);
    }
    public final List<T> getAll()
    {
        return jdbcTemplate.query("SELECT * FROM " + tName, this);
    }
    public final List<T> getAllWhere(String where, Object... args)
    {
        return jdbcTemplate.query("SELECT * FROM " + tName + " " + where, args, this);
    }
	public final void update(int id, String[] fieldNames, Object... args)
	{
		Object[] queryArgs = Arrays.copyOf(args, args.length + 1);
		StringBuilder paramFields = new StringBuilder();
		paramFields.append(fieldNames[0] + " = ?");
		for (int currFieldIndex = 1; currFieldIndex < fieldNames.length; currFieldIndex++)
		{
			paramFields.append(", " + fieldNames[currFieldIndex] + " = ?");
		}
		queryArgs[args.length] = id;
        jdbcTemplate.update("UPDATE " + tName + " SET " + paramFields.toString() + " WHERE id = ?", queryArgs);
	}
	public final void delete(int id)
	{
        jdbcTemplate.update("DELETE FROM " + tName + " WHERE id = ?", id);
	}
	public final void deleteAll()
	{
        jdbcTemplate.update("DELETE FROM " + tName);
	}
	public abstract void create(T model);
	public abstract void update(T model);
}
