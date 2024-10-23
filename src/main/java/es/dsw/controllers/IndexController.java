package es.dsw.controllers;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.dsw.daos.ArticuloDAO;
import es.dsw.models.Articulo;
import es.dsw.models.TipoArticulo;


@Controller
public class IndexController {

	@GetMapping(value= {"/","/index"})
	public String index(Model objModel) {
		
		ArticuloDAO objArticuloDAO = new ArticuloDAO();
		ArrayList<Articulo> objListaArticulos = objArticuloDAO.getAll();
		
		
		objModel.addAttribute("Articulos", objListaArticulos);
		return "index";
	}
	
	@GetMapping(value= {"/eliminar"})
	public String eliminar(@RequestParam(name="idArticulo") int IdArt) {
		
		Articulo objArticulo = new Articulo();
		objArticulo.setIdArticulo(IdArt);
		ArticuloDAO objArticuloDAO = new ArticuloDAO();
		objArticuloDAO.delete(objArticulo);
		
		return "redirect:/index";
	}
	
	@PostMapping(value= {"/nuevo"})
	public String nuevo(@RequestParam String nombre,
						@RequestParam long codbarras,
						@RequestParam String descrip,
						@RequestParam String desctipoarticulo
			    ) {
		
		//Se recoge la información del nuevo artículo en su objeto.
		Articulo objArticulo = new Articulo();
		objArticulo.setNombre(nombre);
		objArticulo.setCodBarras(codbarras);
		objArticulo.setDescripcion(descrip);
		
		//Se recoge la información del nuevo tipo de artículo en su objeto.
		TipoArticulo objTipoArticulo = new TipoArticulo();
		objTipoArticulo.setDescripcion(desctipoarticulo);
		
		//Se asocia al objeto artículo el objeto tipo de artículo.
		objArticulo.setTArticulo(objTipoArticulo);
		
		//Se crea una instancia al DAO de Articulo.
		//Se indica con true que se desea operar en modo transaccional.
		ArticuloDAO objArticuloDAO = new ArticuloDAO(true);
		
		//Se solicita consolidar la información en la base de datos.
		//Atención: Esté método inicia una transacción compuesta de dos operaciones
		//          de inserción.
		objArticuloDAO.setArticulo(objArticulo);
		
		//Se redirecciona al principio para forzar refrescar la tabla de datos de artículos.
		return "redirect:/index";
	}
	
}
