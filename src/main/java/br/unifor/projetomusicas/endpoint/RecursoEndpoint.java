package br.unifor.projetomusicas.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.trabalho.spring_boot_soap.*;

import br.unifor.projetomusicas.service.RecursoService;

@Endpoint
public class RecursoEndpoint {
	
	private static final String NAMESPACE_URI = "http://trabalho.com/spring-boot-soap";

	private RecursoService recursoService;

	@Autowired
	public RecursoEndpoint(RecursoService recursoService) {
		this.recursoService = recursoService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "criarUsuarioRequest")
	@ResponsePayload
	public RecuperarUsuarioResponse criarUsuario(@RequestPayload CriarUsuarioRequest request) {
		RecuperarUsuarioResponse response = new RecuperarUsuarioResponse();
		response.setUsuario(recursoService.criarUsuario(request));
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "editarUsuarioRequest")
	@ResponsePayload
	public RecuperarUsuarioResponse editarUsuario(@RequestPayload EditarUsuarioRequest request) {
		RecuperarUsuarioResponse response = new RecuperarUsuarioResponse();
		response.setUsuario(recursoService.editarUsuario(request));
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "criarPlaylistRequest")
	@ResponsePayload
	public RecuperarPlaylistResponse criarPlaylist(@RequestPayload CriarPlaylistRequest request) {
		RecuperarPlaylistResponse response = new RecuperarPlaylistResponse();
		response.setPlaylist(recursoService.criarPlaylist(request));
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "editarPlaylistRequest")
	@ResponsePayload
	public RecuperarPlaylistResponse editarPlaylist(@RequestPayload EditarPlaylistRequest request) {
		RecuperarPlaylistResponse response = new RecuperarPlaylistResponse();
		response.setPlaylist(recursoService.editarPlaylist(request));
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "criarMusicaRequest")
	@ResponsePayload
	public RecuperarMusicaResponse criarMusica(@RequestPayload CriarMusicaRequest request) {
		RecuperarMusicaResponse response = new RecuperarMusicaResponse();
		response.setMusica(recursoService.criarMusica(request));
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "editarMusicaRequest")
	@ResponsePayload
	public RecuperarMusicaResponse editarMusica(@RequestPayload EditarMusicaRequest request) {
		RecuperarMusicaResponse response = new RecuperarMusicaResponse();
		response.setMusica(recursoService.editarMusica(request));
		
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "recuperarUsuarioRequest")
	@ResponsePayload
	public RecuperarUsuarioListaResponse listarTodosUsuarios(@RequestPayload RecuperarUsuarioRequest request) {
		RecuperarUsuarioListaResponse response = new RecuperarUsuarioListaResponse();
		recursoService.listarTodasUsuarios().forEach(usuario -> response.getUsuarios().add(usuario));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "recuperarMusicaRequest")
	@ResponsePayload
	public RecuperarMusicaListaResponse listarTodasMusicas(@RequestPayload RecuperarMusicaRequest request) {
		RecuperarMusicaListaResponse response = new RecuperarMusicaListaResponse();

		if (request.getIdPlaylist() != 0) {
			RecuperarPlaylistRequest req = new RecuperarPlaylistRequest();
			req.setId(request.getIdPlaylist());

			recursoService.recuperarMusicasPorPlaylist(req).forEach(musica -> response.getMusicas().add(musica));
			return response;
		}

		recursoService.listarTodasMusicas().forEach(musica -> response.getMusicas().add(musica));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "recuperarPlaylistRequest")
	@ResponsePayload
	public RecuperarPlaylistListaResponse listarTodasPlaylists(@RequestPayload RecuperarPlaylistRequest request) {
		RecuperarPlaylistListaResponse response = new RecuperarPlaylistListaResponse();

		if (request.getIdMusica() != 0) {
			RecuperarMusicaRequest req = new RecuperarMusicaRequest();
			req.setId(request.getIdMusica());

			recursoService.recuperaPlaylistPorMusica(req).forEach(playlist -> response.getPlaylists().add(playlist));
			return response;
		}

		recursoService.recuperarPlaylistsPorUsuario(request).forEach(playlist -> response.getPlaylists().add(playlist));
		return response;
	}

}
