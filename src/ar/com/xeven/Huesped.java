package ar.com.xeven;

import java.sql.*;

public class Huesped {

    private int idHuesped;
    private String nombre;
    private String mail;
    private String telefono;
    private int codReserva;
    private final String dbName = "hotel";
    private final String dbUser = "root";
    private final String dbPwd = "ange09lina08";

    public Huesped(String nombreReserva, String mail, String telefono, int codReserva) {
        this.nombre = nombreReserva;
        this.mail = mail;
        this.telefono = telefono;
        this.codReserva = codReserva;
        crearHuesped();
    }
    public Huesped(String nombreReserva, String mail, String telefono) {
        this.nombre = nombreReserva;
        this.mail = mail;
        this.telefono = telefono;
    }

    public Huesped(String nombre, int codReserva) {
        this.nombre = nombre;
        this.mail = "";
        this.telefono = "";
        this.codReserva = codReserva;
        crearHuesped();
    }

    public Huesped(int idHuesped, String nombre, String mail, String telefono) {
        this.idHuesped = idHuesped;
        this.nombre = nombre;
        this.mail = mail;
        this.telefono = telefono;
    }
    public Huesped(int idHuesped, String nombre, String mail, String telefono, int codReserva) {
        this.idHuesped = idHuesped;
        this.nombre = nombre;
        this.mail = mail;
        this.telefono = telefono;
        this.codReserva = codReserva;
    }
    private boolean crearHuesped(){
        String sql = "INSERT INTO `huespedes` (`nombre`, `email`, `telefono`, `codReserva`) VALUES (?,?,?,?);";
        ConexionDB conexionDB = new ConexionDB(dbName,dbUser,dbPwd,sql);
        PreparedStatement pstmt = conexionDB.getPstmt();
        try {
            pstmt.setString(1, nombre);
            pstmt.setString(2, mail);
            pstmt.setString(3,telefono);
            pstmt.setInt(4,codReserva);
            this.idHuesped = conexionDB.ejecutarRetornarKey();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            conexionDB.cerrar();
        }
        return (this.idHuesped>0);
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
