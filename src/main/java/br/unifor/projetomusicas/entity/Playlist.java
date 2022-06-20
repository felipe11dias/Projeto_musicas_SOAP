package br.unifor.projetomusicas.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_playlist")
	public Integer id;
	
	@Column(name="nome")
	public String nome;
	
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;
	
	@ManyToMany(fetch = FetchType.LAZY,
				cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      	})
	@JoinTable(
	  name = "playlists_musica", 
	  joinColumns = @JoinColumn(name = "id_playlist"), 
	  inverseJoinColumns = @JoinColumn(name = "id_musica"))
    private List<Musica> musicas;
}
