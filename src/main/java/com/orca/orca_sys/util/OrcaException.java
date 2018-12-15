package com.orca.orca_sys.util;

public class OrcaException extends Exception{
	
	private String mensaje;

	public OrcaException(String mensaje){
		super();
		this.mensaje = mensaje;		
	}
	
	@Override
	public String getMessage(){
		return "PANDA ERROR: "+mensaje;
	}
}
