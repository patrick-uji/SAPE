package es.uji.ei1027.sape.dao.dto;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import es.uji.ei1027.sape.model.ObjetoIdentificado;
import org.springframework.beans.factory.annotation.Autowired;
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
