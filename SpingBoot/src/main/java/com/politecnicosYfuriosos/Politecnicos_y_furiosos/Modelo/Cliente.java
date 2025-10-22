package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String apellido;
    private String codigo_postal;
    private String telefono;
    private String dni;
    private String direccion;
    private String usuario;
    private String contrasena;
    private String pais;
    private MembershipPlan membresia;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Resenia> resenias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String email) {
        this.codigo_postal = email;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
    }

    public void setMembresia(MembershipPlan membresia){
        this.membresia=membresia;
    }
    public MembershipPlan getMembresia(){
        return membresia;
    }
}

