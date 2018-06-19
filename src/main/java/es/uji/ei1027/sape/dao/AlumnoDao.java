package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Alumno;
import es.uji.ei1027.sape.mappers.AlumnoMapper;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
@Component
public class AlumnoDao extends AbstractDao<Alumno>
{
	private AlumnoMapper rowMapper;
	public AlumnoDao()
	{
		this.rowMapper = new AlumnoMapper();
	}
	@Override
	public Alumno mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
		return rowMapper.mapRow(resultSet, rowNum);
    }
    public Alumno get(String dni)
    {
    	return jdbcTemplate.queryForObject("SELECT * FROM Alumno WHERE dni = ?", new Object[] {dni.toUpperCase()}, this);
    }
	public List<Alumno> getAllPending()
	{
		return jdbcTemplate.query("SELECT * FROM Alumno WHERE id NOT IN (SELECT id_Alumno FROM Asignacion WHERE id_EstadoAsignacion IN (?,?)) AND " +
														     "id IN (SELECT a.id FROM Alumno AS a JOIN PreferenciaAlumno AS p ON p.id_Alumno = a.id GROUP BY a.id HAVING COUNT(p.id) >= 5) GROUP BY id",
								  new Object[] {EstadoAsignacion.ENVIADA.getID(), EstadoAsignacion.ACEPTADA.getID()}, this);
	}
	@Override
    public void create(Alumno model)
    {
        jdbcTemplate.update("INSERT INTO Alumno VALUES (?,?,?,?,?,?,?,?)",
        					model.getId(), model.getDni(), model.getNombre(), model.getNotaMedia(),
        					model.getItinerario().getID(), model.getNumeroCreditos(), model.getAsignaturasPendientes(), model.getSemestreInicioEstancia());
    }
	@Override
    public void update(Alumno model)
    {
        jdbcTemplate.update("UPDATE Alumno SET dni = ?, nombre = ?, notaMedia = ?, itinerario = ?, numeroCreditos = ?, asignaturasPendientes = ?, semestreInicioEstancia = ? WHERE id = ?",
        					model.getDni(), model.getNombre(), model.getNotaMedia(), model.getItinerario().getID(),
        					model.getNumeroCreditos(), model.getAsignaturasPendientes(), model.getSemestreInicioEstancia(),
        					model.getId());
    }
}
