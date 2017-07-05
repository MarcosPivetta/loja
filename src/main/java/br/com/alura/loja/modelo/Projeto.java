package br.com.alura.loja.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Projeto {
	private List<Projeto> projetos = new ArrayList<Projeto>();
	private String nome;
	private long id;
	private int anoDeInicio;

	public Projeto(long id, String nome, int anoDeInicio) {
		super();
		this.nome = nome;
		this.id = id;
		this.anoDeInicio = anoDeInicio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public int getAnoDeInicio() {
		return anoDeInicio;
	}

	public Projeto() {
		super();
	}

	public String toXML() {
		return new XStream().toXML(this);
	}

	public String toJSON() {
		return new Gson().toJson(this);
	}
	
	public void remove(long id) {
		for (Iterator<Projeto> iterator = projetos.iterator(); iterator.hasNext();) {
			Projeto projeto = (Projeto) iterator.next();
			if(projeto.getId() == id) {
				iterator.remove();
			}
		}
	}

}
