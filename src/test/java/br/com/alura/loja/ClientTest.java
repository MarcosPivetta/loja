package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import junit.framework.Assert;

public class ClientTest {

	private HttpServer server;
	private Client client;
	private WebTarget target;

	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		this.client = ClientBuilder.newClient(config);
		this.target = client.target("http://localhost:8081");
	}

	@After
	public void mataServidor() {
		server.stop();
	}

	@Test
	public void testaQueBuscaUmCarrinhoTrazOCarrinhoEsperado() {

		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		System.out.println(conteudo);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
		
	}
	
	@Test
	public void testaInclusaoDoCarrinho() {

		Carrinho carrinho2 = new Carrinho();
        carrinho2.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho2.setRua("Rua Vergueiro");
        carrinho2.setCidade("Sao Paulo");
        String xml = carrinho2.toXML();
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response response = target.path("/carrinhos").request().post(entity);

        Assert.assertEquals(200, response.getStatus());
        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Micronfone"));
	}

}
