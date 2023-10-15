package es.dsw.daos;

import java.sql.ResultSet;

import es.dsw.connections.MySqlConnection;
import es.dsw.models.TipoArticulo;

public class TipoArticuloDAO {
	
	private MySqlConnection objMySqlConnection;
	private boolean transaccionExterna;
	
	public TipoArticuloDAO() {
		objMySqlConnection = new MySqlConnection();
		transaccionExterna = false;
	}
	
	public TipoArticuloDAO(MySqlConnection objConexion) {
		objMySqlConnection = objConexion;
		transaccionExterna = true;
	}
	
	public TipoArticulo setTipoArticulo(TipoArticulo objTipoArticulo) {
		//Se recogerá la clave primaria del nuevo registro si hay exito en la operación.
		int IdTipoArticulo = -1;
		//Se abre el objeto de conexión (el método ya detecta si estaba abierto dejandolo así).
		objMySqlConnection.open();
		//Si no hay error tras abrir la conexión.
		if (!objMySqlConnection.isError()){
			try {
				       //Se prepara la SQL y parametriza.
					   String SQL = "INSERT INTO stockdb.tipoarticulo (descripcion_tar) "+
			                     "VALUE ('"+objTipoArticulo.getDescripcion()+"')";
					  
					   //Se ejecuta la operación.
					   ResultSet Result = objMySqlConnection.executeInsert(SQL);
					   //Si la operación fue correcta, en Result se recoge las laves primarias
					   //del registro. En nuestro caso, solo tenemos una única clave primaria.
					   if ((Result != null) && (Result.next())) {
						   IdTipoArticulo=Result.getInt(1);
					   } 
					   //Se actualiza el Id del artículo.
					   objTipoArticulo.setIdTipoArticulo(IdTipoArticulo);
					
			} catch(Exception ex) {} 
			  finally {
				//Si el método setTipoArticulo es invocado dentro de una transacción, no podemos
				//cerrar la conexión.
				if (!transaccionExterna)
				     objMySqlConnection.close();
			  }
		}
		
		return objTipoArticulo;
	}
}
