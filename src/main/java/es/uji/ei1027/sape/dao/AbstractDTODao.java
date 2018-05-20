package es.uji.ei1027.sape.dao;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import es.uji.ei1027.sape.model.ObjetoIdentificado;
public abstract class AbstractDTODao<T extends ObjetoIdentificado> implements RowMapper<T>
{
    protected JdbcTemplate jdbcTemplate;
	@Autowired
    public final void setDataSource(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public abstract T get(int id);
    public abstract List<T> getAll();
}
