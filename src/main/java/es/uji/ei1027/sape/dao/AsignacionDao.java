package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.model.Asignacion;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.mappers.AsignacionMapper;
@Component
public class AsignacionDao extends AbstractDao<Asignacion>
{
	private AsignacionMapper rowMapper;
	public AsignacionDao()
	{
		this.rowMapper = new AsignacionMapper();
	}
    @Override
    public Asignacion mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
    	return rowMapper.mapRow(resultSet, rowNum);
    }
    public List<Asignacion> getAllFromOffer(int offerID)
    {
    	return jdbcTemplate.query("SELECT * FROM Asignacion WHERE id_OfertaProyecto = ?", new Object[] {offerID}, this);
    }
    public List<Asignacion> getAllFromStudent(int studentID)
    {
    	return jdbcTemplate.query("SELECT * FROM Asignacion WHERE id_Estudiante = ?", new Object[] {studentID}, this);
    }
    public List<Asignacion> getAllFromTutor(int tutorID)
    {
    	return jdbcTemplate.query("SELECT * FROM Asignacion WHERE id_ProfesorTutor = ?", new Object[] {tutorID}, this);
    }
	@Override
	public void create(Asignacion model)
	{
        jdbcTemplate.update("INSERT INTO Asignacion (fechaPropuesta, fechaAceptacion, fechaRechazo, fechaTraspasoIGLU, comentarioCambio, id_EstadoAsignacion, id_OfertaProyecto, id_Estudiante, id_ProfesorTutor) VALUES (?,?,?,?,?,?,?,?,?)",
							Utils.stringToDate(model.getFechaPropuesta()), Utils.stringToDate(model.getFechaAceptacion()), Utils.stringToDate(model.getFechaRechazo()),
							Utils.stringToDate(model.getFechaTraspasoIGLU()), model.getComentarioCambio(), model.getEstado().getID(),
							model.getIdOfertaProyecto(), model.getIDEstudiante(), model.getIdProfesorTutor());
	}
	@Override
    public void update(Asignacion model)
    {
        jdbcTemplate.update("UPDATE Asignacion SET fechaPropuesta = ?, fechaAceptacion = ?, fechaRechazo = ?, fechaTraspasoIGLU = ?, comentarioCambio = ?, id_EstadoAsignacion = ?, id_OfertaProyecto = ?, id_Estudiante = ?, id_ProfesorTutor = ? WHERE id = ?",
							Utils.stringToDate(model.getFechaPropuesta()), Utils.stringToDate(model.getFechaAceptacion()), Utils.stringToDate(model.getFechaRechazo()),
							Utils.stringToDate(model.getFechaTraspasoIGLU()), model.getComentarioCambio(), model.getEstado().getID(),
							model.getIdOfertaProyecto(), model.getIDEstudiante(), model.getIdProfesorTutor(),
        					model.getId());
    }
}
