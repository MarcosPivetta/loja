package br.com.alura.loja.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String busca() {
		Projeto projeto = new ProjetoDAO().busca(1l);
		return projeto.toJSON();

	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String conteudo) {
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		new ProjetoDAO().adiciona(projeto);
		return "<status>sucesso</status>";
	}

	@DELETE
	public Response removeProjeto(@PathParam("id") long id, @PathParam("projetoId") long projetoId) {
		Projeto projeto = new ProjetoDAO().busca(id);
		projeto.remove(projetoId);
		return Response.ok().build();

	}
}
