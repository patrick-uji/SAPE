package es.uji.ei1027.sape.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import es.uji.ei1027.sape.enums.EstadoAsignacion;
import es.uji.ei1027.sape.model.Estudiante;
import org.springframework.stereotype.Component;
@Component
public class EstudianteDao extends AbstractDao<Estudiante>
{
	@Override
	public Estudiante mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	Estudiante estudiante = new Estudiante();
    	estudiante.setId(resultSet.getInt("id"));
    	estudiante.setDni(resultSet.getString("dni"));
    	estudiante.setNombre(resultSet.getString("nombre"));
    	estudiante.setNotaMedia(resultSet.getFloat("notaMedia"));
    	estudiante.setItinerario(resultSet.getString("itinerario"));
    	estudiante.setNumeroCreditos(resultSet.getInt("numeroCreditos"));
    	estudiante.setAsignaturasPendientes(resultSet.getInt("asignaturasPendientes"));
    	estudiante.setSemestreInicioEstancia(resultSet.getInt("semestreInicioEstancia"));
        return estudiante;
    }
    public Estudiante get(String dni)
    {
    	return jdbcTemplate.queryForObject("SELECT * FROM Estudiante WHERE dni = ?", new Object[] {dni.toUpperCase()}, this);
    }
	public List<Estudiante> getAllPending()
	{
		return jdbcTemplate.query("SELECT * FROM Estudiante WHERE id NOT IN (SELECT id_Estudiante FROM Asignacion WHERE id_EstadoAsignacion != ?)", new Object[] {EstadoAsignacion.RECHAZADA.getID()}, this);
	}
	@Override
    public void create(Estudiante model)
    {
        jdbcTemplate.update("INSERT INTO Estudiante VALUES (?,?,?,?,?,?,?,?)",
        					model.getId(), model.getDni(), model.getNombre(), model.getNotaMedia(),
        					model.getItinerario(), model.getNumeroCreditos(), model.getAsignaturasPendientes(), model.getSemestreInicioEstancia());
    }
	@Override
    public void update(Estudiante model)
    {
        jdbcTemplate.update("UPDATE Estudiante SET dni = ?, nombre = ?, notaMedia = ?, itinerario = ?, numeroCreditos = ?, asignaturasPendientes = ?, semestreInicioEstancia = ? WHERE id = ?",
        					model.getDni(), model.getNombre(), model.getNotaMedia(), model.getItinerario(),
        					model.getNumeroCreditos(), model.getAsignaturasPendientes(), model.getSemestreInicioEstancia(),
        					model.getId());
    }
}
