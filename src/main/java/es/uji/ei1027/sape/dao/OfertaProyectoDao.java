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
	private PersonaContactoDao personaContactoDao;
	private OfertaProyectoMapper rowMapper;
	public OfertaProyectoDao()
	{
		this.rowMapper = new OfertaProyectoMapper();
	}
	@Autowired
	public void setPersonaContactoDao(PersonaContactoDao personaContactoDao)
	{
		this.personaContactoDao = personaContactoDao;
	}
    @Override
    public OfertaProyecto mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return rowMapper.mapRow(resultSet, rowNum);
    }
    public List<OfertaProyecto> getAllFromContactPerson(int contactPersonID)
    {
    	return jdbcTemplate.query("SELECT * FROM OfertaProyecto WHERE id_PersonaContacto = ?", new Object[] {contactPersonID}, this);
    }
    public List<OfertaProyecto> getAllFromCompany(int companyID)
    {
    	return jdbcTemplate.query("SELECT o.* FROM PersonaContacto AS p JOIN OfertaProyecto AS o ON p.id = o.id_PersonaContacto WHERE p.id_Empresa = ?", new Object[] {companyID}, this);
    }
    @Override
    public void create(OfertaProyecto model)
    {
        jdbcTemplate.update("INSERT INTO OfertaProyecto (numero, titulo, objetivo, id_EstadoOferta, id_Itinerario, fechaAlta, fechaUltimoCambio, id_PersonaContacto) VALUES (?,?,?,?,?,?,?,?)",
        					model.getNumero(), model.getTitulo(), model.getObjetivo(), model.getEstado().getID(),
        					model.getItinerario().getID(), Utils.stringToDate(model.getFechaAlta()), Utils.stringToDate(model.getFechaUltimoCambio()), model.getIdPersonaContacto());
    }
    @Override
    public void update(OfertaProyecto model)
    {
        jdbcTemplate.update("UPDATE OfertaProyecto SET numero = ?, titulo = ?, objetivo = ?, id_EstadoOferta = ?, id_Itinerario = ?, fechaAlta = ?, fechaUltimoCambio = ?, id_PersonaContacto = ? WHERE id = ?",
        					model.getNumero(), model.getTitulo(), model.getObjetivo(), model.getEstado().getID(),
        					model.getItinerario().getID(), Utils.stringToDate(model.getFechaAlta()), Utils.stringToDate(model.getFechaUltimoCambio()), model.getIdPersonaContacto(),
        					model.getId());
    }
	@Override
	public boolean owns(int id, int ownerID)
	{
		int contactPersonID = this.get(id).getIdPersonaContacto();
		return personaContactoDao.get(contactPersonID).getIDEmpresa() == ownerID;
	}
}
