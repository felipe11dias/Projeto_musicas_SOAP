package br.unifor.projetomusicas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "musicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Musica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_musica")
	public Integer id;
	
	@Column(name="nome")
	public String nome;
	
	@Column(name="artista")
	public String artista;
	
	@ManyToMany(fetch = FetchType.LAZY,
				cascade = {
				          CascadeType.PERSIST,
				          CascadeType.MERGE
				}, mappedBy = "musicas")
	List<Playlist> playlists = new ArrayList<>();

}
