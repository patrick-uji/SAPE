package es.uji.ei1027.sape.dao.dto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Alumno;
import es.uji.ei1027.sape.model.Empresa;
import es.uji.ei1027.sape.model.Asignacion;
import es.uji.ei1027.sape.dto.AsignacionDTO;
import es.uji.ei1027.sape.model.ProfesorTutor;
import es.uji.ei1027.sape.mappers.AlumnoMapper;
import es.uji.ei1027.sape.model.OfertaProyecto;
import es.uji.ei1027.sape.model.PersonaContacto;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.mappers.EmpresaMapper;
import es.uji.ei1027.sape.enums.EstadoAsignacion;
import es.uji.ei1027.sape.mappers.AsignacionMapper;
import es.uji.ei1027.sape.mappers.ProfesorTutorMapper;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
import es.uji.ei1027.sape.mappers.PersonaContactoMapper;
import org.springframework.dao.EmptyResultDataAccessException;
@Component
public class AsignacionDTODao extends AbstractDTODao<AsignacionDTO>
{
	private static final String BASE_QUERY = "SELECT asi.*, " + OfertaProyecto.SELECT_JOIN + ", " +
											 PersonaContacto.SELECT_JOIN + ", " + Empresa.SELECT_JOIN + ", " + ProfesorTutor.SELECT_JOIN + ", " + Alumno.SELECT_JOIN +
											" FROM Asignacion AS asi JOIN OfertaProyecto AS o ON asi.id_OfertaProyecto = o.id " + Empresa.FROM_OFFER_JOIN + " JOIN ProfesorTutor AS pt ON asi.id_ProfesorTutor = pt.id" +
											" JOIN Alumno AS a ON asi.id_Alumno = a.id";
	private PersonaContactoMapper contactPersonMapper;
	private AsignacionMapper assignmentMapper;
	private ProfesorTutorMapper teacherMapper;
	private OfertaProyectoMapper offerMapper;
	private EmpresaMapper companyMapper;
	private AlumnoMapper studentMapper;
	public AsignacionDTODao()
	{
		this.studentMapper = new AlumnoMapper("a_");
		this.companyMapper = new EmpresaMapper("e_");
		this.assignmentMapper = new AsignacionMapper();
		this.offerMapper = new OfertaProyectoMapper("o_");
		this.teacherMapper = new ProfesorTutorMapper("pt_");
		this.contactPersonMapper = new PersonaContactoMapper("p_");
	}
	@Override
	public AsignacionDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		AsignacionDTO asignacionDTO = new AsignacionDTO();
		assignmentMapper.mapRow(asignacionDTO, resultSet, rowNum);
		asignacionDTO.setAlumno(studentMapper.mapRow(resultSet, rowNum));
		asignacionDTO.setEmpresa(companyMapper.mapRow(resultSet, rowNum));
		asignacionDTO.setOfertaProyecto(offerMapper.mapRow(resultSet, rowNum));
		asignacionDTO.setProfesorTutor(teacherMapper.mapRow(resultSet, rowNum));
		asignacionDTO.setPersonaContacto(contactPersonMapper.mapRow(resultSet, rowNum));
        return asignacionDTO;
	}
	@Override
	public AsignacionDTO get(int id)
	{
        return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE a.id = ?", new Object[] {id}, this);
	}
    public Asignacion getActiveFromStudent(int studentID)
    {
    	try
		{
        	return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE id_Alumno = ? AND id_EstadoAsignacion IN (?,?)",
        									   new Object[] {studentID, EstadoAsignacion.ENVIADA.getID(), EstadoAsignacion.ACEPTADA.getID()}, this);
		}
		catch (EmptyResultDataAccessException ex)
		{
			return null;
		}
    }
	@Override
	public List<AsignacionDTO> getAll()
	{
    	return jdbcTemplate.query(BASE_QUERY + " ORDER BY id DESC, id_EstadoAsignacion", this);
	}
}
