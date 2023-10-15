package es.dsw.models;

public class Articulo {
	private int IdArticulo;
	private long CodBarras;
	private String Nombre;
	private String Descripcion;
	private TipoArticulo TArticulo;
	
	public Articulo() {
		super();
		this.IdArticulo = -1;
		this.CodBarras = -1;
		this.Nombre = "";
		this.Descripcion = "";
		this.TArticulo = null;
	}
	
	public int getIdArticulo() {
		return IdArticulo;
	}
	public void setIdArticulo(int idArticulo) {
		IdArticulo = idArticulo;
	}
	public long getCodBarras() {
		return CodBarras;
	}
	public void setCodBarras(long codBarras) {
		CodBarras = codBarras;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public TipoArticulo getTArticulo() {
		return TArticulo;
	}

	public void setTArticulo(TipoArticulo tArticulo) {
		TArticulo = tArticulo;
	}
	
	

	

}
