package com.orca.orca_sys.param.inventario;

import java.util.List;

import com.orca.orca_sys.model.inventario.RegistroInventario;

public class ListaRegistroInventarioParam {
	
	public List<RegistroInventario> getLista() {
		return lista;
	}

	public void setLista(List<RegistroInventario> lista) {
		this.lista = lista;
	}

	private List<RegistroInventario> lista;

}
