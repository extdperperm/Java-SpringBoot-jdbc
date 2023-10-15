package es.dsw.models;

//Definición de la clase correspondiente al modelo TipoArtículo
public class TipoArticulo {
	
	private int IdTipoArticulo;
	private String Descripcion;
	
	public TipoArticulo() {
		IdTipoArticulo = -1;
		Descripcion = "";
	}

	public int getIdTipoArticulo() {
		return this.IdTipoArticulo;
	}
	
	public void setIdTipoArticulo(int id) {
		this.IdTipoArticulo = id;
	}

	public String getDescripcion() {
		return this.Descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion;
	}

}
	
	