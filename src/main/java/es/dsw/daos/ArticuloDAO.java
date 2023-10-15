package es.dsw.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.Articulo;

public class ArticuloDAO {

	private MySqlConnection objMySqlConnection;

	
	public ArticuloDAO() {
		objMySqlConnection = new MySqlConnection();
	}
	
	public ArticuloDAO(boolean IniciarTransaccion) {
		//Si IniciarTransaccion es true entonces indico al objeto de conexión
		//que no deseo que haga él auto-comit. Ya que el commit lo controlamos
		//nosotros.
		objMySqlConnection = new MySqlConnection(!IniciarTransaccion);
	
	}
	
	public ArrayList<Articulo> getAll(){
		
		ArrayList<Articulo> objListaArticulos = new ArrayList<Articulo>();
		objMySqlConnection.open();
		
		if(!objMySqlConnection.isError()) {
			ResultSet result = objMySqlConnection.executeSelect("SELECT * FROM stockdb.articulo");
			
			try {
				while (result.next()) {
					Articulo objArticulo = new Articulo();
					objArticulo.setIdArticulo(result.getInt("idarticulo_art"));
					objArticulo.setCodBarras(result.getLong("codbarras_art"));
					objArticulo.setNombre(result.getString("nombre_art"));
					objArticulo.setDescripcion(result.getString("descripcion_art"));
					objListaArticulos.add(objArticulo);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		objMySqlConnection.close();
		
		return objListaArticulos;
	}
	
	public int delete(Articulo objArticulo) {
		int NumRowsDelete = 0;
		
		objMySqlConnection.open();
		if (!objMySqlConnection.isError()) {
			String SQL = "DELETE FROM stockdb.articulo WHERE idarticulo_art = " + objArticulo.getIdArticulo();
			NumRowsDelete = objMySqlConnection.executeUpdateOrDelete(SQL);
		}
		objMySqlConnection.close();
		
		return NumRowsDelete;
	}
	
	public boolean setArticulo(Articulo objArticulo) {
		//Se guarda el resultado de la ejecución
		boolean resultado = false;
		
		//Se abre la conexión.
		objMySqlConnection.open();
		//Si no hay error tras abrir la conexión.
		if (!objMySqlConnection.isError()){
			//Antes de insertar un artículo, debemos insertar el nuevo Tipo de Artículo
			//Se recurre al DAO de Tipo de Artículo. Se comparte la conexión, para si se
			//ejecuta con autocomit=no, se comparta la transacción:
			TipoArticuloDAO objTipoArticuloDAO = new TipoArticuloDAO(objMySqlConnection);
			objTipoArticuloDAO.setTipoArticulo(objArticulo.getTArticulo());
			try {
					String SQL = "INSERT INTO stockdb.articulo "
					         + "(codbarras_art,"
					         + " nombre_art,"
					         + " descripcion_art,"
					         + " idtipoarticulo_art,"
					         + " finsercion_art,"
					         + " fultmodf_art) "
					   + "VALUES "
					   + "("+objArticulo.getCodBarras()+","
					   + " '"+objArticulo.getNombre()+"',"
					   + " '"+objArticulo.getDescripcion()+"',"
					   + " "+objArticulo.getTArticulo().getIdTipoArticulo()+","
					   + " CURRENT_TIMESTAMP,"
					   + " CURRENT_TIMESTAMP)";
					
					   ResultSet Result = objMySqlConnection.executeInsert(SQL);
					   
					   //Se recogerá la clave primaria del nuevo registro si hay exito en la operación.
					   int IdArticulo;
						
					   if ((Result != null) && (Result.next())) {
							IdArticulo=Result.getInt(1);
							objMySqlConnection.commit();
							resultado = true;
					   } else {
						   objMySqlConnection.rollback();
						   IdArticulo = -1;
					   }
					  
					   objArticulo.setIdArticulo(IdArticulo);
					
			} catch(Exception ex) {
				objMySqlConnection.rollback();
			} finally {
				objMySqlConnection.close();
			}
		}
		
		return resultado;
		
	}
}
