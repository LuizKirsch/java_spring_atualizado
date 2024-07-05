package br.com.ienh.springacessobanco.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sala")
    private Integer sala;

    @Column(name = "disponivel")
    private String disponivel;

    public Sala() {}

    public Sala(Integer sala, String disponivel) {
        this.sala = sala;
        this.disponivel = disponivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", sala=" + sala +
                ", disponivel='" + disponivel + '\'' +
                '}';
    }
}
