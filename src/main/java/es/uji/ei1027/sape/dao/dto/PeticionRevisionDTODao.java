package es.uji.ei1027.sape.dao.dto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.uji.ei1027.sape.model.Usuario;
import org.springframework.stereotype.Component;
import es.uji.ei1027.sape.mappers.UsuarioMapper;
import es.uji.ei1027.sape.dto.PeticionRevisionDTO;
import es.uji.ei1027.sape.mappers.PeticionRevisionMapper;
@Component
public class PeticionRevisionDTODao extends AbstractDTODao<PeticionRevisionDTO>
{
	private static final String BASE_QUERY = "SELECT p.*, " + Usuario.SELECT_JOIN + " FROM PeticionRevision AS p JOIN Usuario AS u ON p.id_Admin = u.id";
	private PeticionRevisionMapper petitionMapper;
	private UsuarioMapper userMapper;
	public PeticionRevisionDTODao()
	{
		this.petitionMapper = new PeticionRevisionMapper();
		this.userMapper = new UsuarioMapper("u_");
	}
	@Override
	public PeticionRevisionDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		PeticionRevisionDTO peticionRevisionDTO = new PeticionRevisionDTO();
		peticionRevisionDTO.setUsuario(userMapper.mapRow(resultSet, rowNum));
		petitionMapper.mapRow(peticionRevisionDTO, resultSet, rowNum);
        return peticionRevisionDTO;
	}
	@Override
	public PeticionRevisionDTO get(int id)
	{
        return jdbcTemplate.queryForObject(BASE_QUERY + " WHERE id = ?", new Object[] {id}, this);
	}
	@Override
	public List<PeticionRevisionDTO> getAll()
	{
    	return jdbcTemplate.query(BASE_QUERY + " ORDER BY id DESC", this);
	}
	public List<PeticionRevisionDTO> getAllFromOffer(int offerID)
	{
    	return jdbcTemplate.query(BASE_QUERY + " WHERE id_OfertaProyecto = ? ORDER BY id DESC", new Object[] {offerID}, this);
	}
}
