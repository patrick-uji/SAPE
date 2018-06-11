package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import es.uji.ei1027.sape.enums.EstadoAsignacion;
import es.uji.ei1027.sape.enums.Itinerario;
import es.uji.ei1027.sape.model.Alumno;
import org.springframework.stereotype.Component;
@Component
public class AlumnoDao extends AbstractDao<Alumno>
{
	@Override
	public Alumno mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	Alumno alumno = new Alumno();
    	alumno.setId(resultSet.getInt("id"));
    	alumno.setDni(resultSet.getString("dni"));
    	alumno.setNombre(resultSet.getString("nombre"));
    	alumno.setNotaMedia(resultSet.getFloat("notaMedia"));
    	alumno.setItinerario( Itinerario.fromID(resultSet.getInt("id_Itinerario")) );
    	alumno.setNumeroCreditos(resultSet.getInt("numeroCreditos"));
    	alumno.setAsignaturasPendientes(resultSet.getInt("asignaturasPendientes"));
    	alumno.setSemestreInicioEstancia(resultSet.getInt("semestreInicioEstancia"));
        return alumno;
    }
    public Alumno get(String dni)
    {
    	return jdbcTemplate.queryForObject("SELECT * FROM Alumno WHERE dni = ?", new Object[] {dni.toUpperCase()}, this);
    }
	public List<Alumno> getAllPending()
	{
		return jdbcTemplate.query("SELECT * FROM Alumno WHERE id NOT IN (SELECT id_Alumno FROM Asignacion WHERE id_EstadoAsignacion != ?)", new Object[] {EstadoAsignacion.RECHAZADA.getID()}, this);
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
