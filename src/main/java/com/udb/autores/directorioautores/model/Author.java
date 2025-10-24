package com.udb.autores.directorioautores.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "autor")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    @Column(name = "id_autor") // Columna SQL
    private int id;

    @Column(name = "nombre_autor") // Columna SQL
    private String name;

    @Column(name = "telefono") // Columna SQL
    private String phone;

    @Temporal(TemporalType.DATE) // Importante para manejar solo Fecha (sin hora)
    @Column(name = "fecha_nacimiento") // Columna SQL
    private Date birthDate;

    // Esta es la relación: Muchos autores (Many) tienen Un género (One)
    @ManyToOne
    @JoinColumn(name = "id_genero") // La llave foránea en la tabla 'autor'
    private LiteraryGenre literaryGenre;

    // --- Constructores ---
    public Author() {
    }

    // --- Getters y Setters ---
    // (JSF los necesita para acceder a los datos)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public LiteraryGenre getLiteraryGenre() {
        return literaryGenre;
    }

    public void setLiteraryGenre(LiteraryGenre literaryGenre) {
        this.literaryGenre = literaryGenre;
    }
}