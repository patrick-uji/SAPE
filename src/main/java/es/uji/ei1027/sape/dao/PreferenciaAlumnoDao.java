package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.Utils;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.model.PreferenciaAlumno;
import es.uji.ei1027.sape.mappers.PreferenciaAlumnoMapper;
@Component
public class PreferenciaAlumnoDao extends AbstractDao<PreferenciaAlumno>
{
	private PreferenciaAlumnoMapper rowMapper;
	public PreferenciaAlumnoDao()
	{
		this.rowMapper = new PreferenciaAlumnoMapper();
	}
	@Override
    public PreferenciaAlumno mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return rowMapper.mapRow(resultSet, rowNum);
    }
    public List<PreferenciaAlumno> getAllFromOffer(int offerID)
    {
    	return jdbcTemplate.query("SELECT * FROM PreferenciaAlumno WHERE id_OfertaProyecto = ? ORDER BY id DESC", new Object[] {offerID}, this);
    }
    public List<PreferenciaAlumno> getAllFromStudent(int studentID)
    {
    	return jdbcTemplate.query("SELECT * FROM PreferenciaAlumno WHERE id_Alumno = ? ORDER BY orden", new Object[] {studentID}, this);
    }
    @Override
    public void create(PreferenciaAlumno model)
    {
        jdbcTemplate.update("INSERT INTO PreferenciaAlumno (orden, fechaUltimoCambio, id_OfertaProyecto, id_Alumno) VALUES (?,?,?,?)",
        					model.getOrden(), Utils.stringToDate(model.getFechaUltimoCambio()), model.getIdOfertaProyecto(), model.getIDAlumno());
    }
    public void update(PreferenciaAlumno model)
    {
        jdbcTemplate.update("UPDATE PreferenciaAlumno SET orden = ?, fechaUltimoCambio = ?, id_OfertaProyecto = ?, id_Alumno = ? WHERE id = ?",
							model.getOrden(), Utils.stringToDate(model.getFechaUltimoCambio()), model.getIdOfertaProyecto(), model.getIDAlumno(),
        					model.getId());
    }
    public int countFromStudent(int studentID)
    {
    	return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PreferenciaAlumno WHERE id_Alumno = ?", new Object[] {studentID}, Integer.class);
    }
}
