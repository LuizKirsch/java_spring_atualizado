package br.com.ienh.springacessobanco.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "doacoes")
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "doador")
    private String doador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public Doacao(int id, String doador, Livro livro) {
        this.id = id;
        this.doador = doador;
        this.livro = livro;
    }

    public Doacao() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoador() {
        return doador;
    }

    public void setDoador(String doador) {
        this.doador = doador;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
