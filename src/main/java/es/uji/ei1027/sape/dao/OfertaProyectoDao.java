package es.uji.ei1027.sape.dao;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.Utils;
import es.uji.ei1027.sape.enums.EstadoOferta;
import es.uji.ei1027.sape.mappers.OfertaProyectoMapper;
import es.uji.ei1027.sape.model.OfertaProyecto;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
@Component
public class OfertaProyectoDao extends OwnedResourceDao<OfertaProyecto>
{
	private EstanciaDao estanciaDao;
	private OfertaProyectoMapper rowMapper;
	public OfertaProyectoDao()
	{
		this.rowMapper = new OfertaProyectoMapper();
	}
	@Autowired
	public void setEstanciaDao(EstanciaDao estanciaDao)
	{
		this.estanciaDao = estanciaDao;
	}
    @Override
    public OfertaProyecto mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return rowMapper.mapRow(resultSet, rowNum);
    }
    public OfertaProyecto getFromBusinessStay(int businessStayID)
    {
    	return jdbcTemplate.queryForObject("SELECT * FROM OfertaProyecto WHERE id_Estancia = ?", new Object[] {businessStayID}, this);
    }
    public List<OfertaProyecto> getAllFromCompany(int companyID)
    {
    	return jdbcTemplate.query("SELECT o.* FROM Estancia AS e JOIN OfertaProyecto AS o ON e.id = o.id_estancia WHERE e.id_Empresa = ?", new Object[] {companyID}, this);
    }
    public List<OfertaProyecto> getAllChoosable(int studentID)
    {
    	//TODO: show the ones related to the student's itinerary
    	return jdbcTemplate.query("SELECT * FROM OfertaProyecto WHERE id_EstadoOferta = ? AND id NOT IN (SELECT id_OfertaProyecto FROM PreferenciaAlumno WHERE id_Estudiante = ?)",
    							  new Object[] {EstadoOferta.ACEPTADA.getID(), studentID}, this);
    }
    @Override
    public void create(OfertaProyecto model)
    {
        jdbcTemplate.update("INSERT INTO OfertaProyecto (numero, tarea, objetivo, id_EstadoOferta, itinerario, fechaAlta, fechaUltimoCambio, id_Estancia) VALUES (?,?,?,?,?,?,?,?)",
        					model.getNumero(), model.getTarea(), model.getObjetivo(), model.getEstado().getID(),
        					model.getItinerario(), Utils.stringToDate(model.getFechaAlta()), Utils.stringToDate(model.getFechaUltimoCambio()), model.getIdEstancia());
    }
    @Override
    public void update(OfertaProyecto model)
    {
        jdbcTemplate.update("UPDATE OfertaProyecto SET numero = ?, tarea = ?, objetivo = ?, id_EstadoOferta = ?, itinerario = ?, fechaAlta = ?, fechaUltimoCambio = ?, id_Estancia = ? WHERE id = ?",
        					model.getNumero(), model.getTarea(), model.getObjetivo(), model.getEstado().getID(),
        					model.getItinerario(), Utils.stringToDate(model.getFechaAlta()), Utils.stringToDate(model.getFechaUltimoCambio()), model.getIdEstancia(),
        					model.getId());
    }
	@Override
	public boolean owns(int id, int ownerID)
	{
		int idEstancia = this.get(id).getIdEstancia();
		return estanciaDao.get(idEstancia).getIDEmpresa() == ownerID;
	}
}
