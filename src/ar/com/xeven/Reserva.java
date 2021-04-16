package ar.com.xeven;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int codReserva;
    private Estado estado;
    private int cantidadHuespedes;
    private int cantidadHabitaciones;
    private List<Habitacion> habitaciones = new ArrayList<>();
    private List<Huesped> huespedes = new ArrayList<>();
    private final String dbName = "hotel";
    private final String dbUser = "root";
    private final String dbPwd = "ange09lina08";

    public Reserva(String nombreReserva, int cantidadHuespedes, int cantidadHabitaciones, String mail, String telefono) {
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
        if(crearReserva(Estado.CONFIRMADA.ordinal(), cantidadHuespedes, cantidadHabitaciones)){
            this.estado = Estado.CONFIRMADA;
            actualizarHabitaciones();
            Huesped huesped = new Huesped(nombreReserva, mail, telefono, codReserva);
            crearHuespedXReserva(huesped.getIdHuesped());
            huespedes.add(huesped);
        }else{
            this.estado = Estado.CANCELADA;
        }
    }

    private void borrarReserva(){
        String sql = "DELETE FROM `reserva` WHERE codReserva = ?;";
        ConexionDB conexionDB = new ConexionDB(dbName,dbUser,dbPwd,sql);
        PreparedStatement pstmt = conexionDB.getPstmt();
        try {
            pstmt.setInt(1,codReserva);
            conexionDB.ejecutar();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conexionDB.cerrar();
        }
    }
    private void crearHuespedXReserva(int idHuesped){
        String sql = "INSERT INTO `huespedesxreserva` (`codReserva`, `idHuesped`) VALUES (?,?);";
        ConexionDB conexionDB = new ConexionDB(dbName,dbUser,dbPwd,sql);
        PreparedStatement pstmt = conexionDB.getPstmt();
        try {
            pstmt.setInt(1,codReserva);
            pstmt.setInt(2,idHuesped);
            conexionDB.ejecutar();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conexionDB.cerrar();
        }
    }
    private void reservarHabitacion(int idHabitacion){
        String sql = "UPDATE `habitaciones` SET `codReserva`=?, `disponible`='0' WHERE `idHabitacion`=?;";
        ConexionDB conexionDB = new ConexionDB(dbName, dbUser, dbPwd, sql);
        PreparedStatement pstmt = conexionDB.getPstmt();
        try {
            pstmt.setInt(1, codReserva);
            pstmt.setInt(2, idHabitacion);
            conexionDB.ejecutar();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conexionDB.cerrar();
        }
    }
    private void crearHabitacionXReserva(int idHabitacion){
        String sql = "INSERT INTO `habitacionesxreserva` (`codReserva`, `idHabitacion`) VALUES (?,?);";
        ConexionDB conexionDB = new ConexionDB(dbName, dbUser, dbPwd, sql);
        PreparedStatement pstmt = conexionDB.getPstmt();
        try {
            pstmt.setInt(1, codReserva);
            pstmt.setInt(2, idHabitacion);
            conexionDB.ejecutar();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conexionDB.cerrar();
        }
    }
    private void actualizarHabitaciones(){
        String sql = "SELECT * FROM habitaciones WHERE disponible = 1 AND (capacidad >= ? AND capacidad <= ?) ORDER BY capacidad;";
        ConexionDB conexionDB = new ConexionDB(dbName, dbUser, dbPwd, sql);
        PreparedStatement pstmt = conexionDB.getPstmt();
        int capacidadMinima = (int) Math.ceil(cantidadHuespedes / cantidadHabitaciones);
        try {
            pstmt.setInt(1, capacidadMinima);
            pstmt.setInt(2, capacidadMinima+2);
            ResultSet rs = pstmt.executeQuery();
            if(rs!=null)
                for(int i=0;i<cantidadHabitaciones;i++)
                    if(rs.next()) {
                        reservarHabitacion(rs.getInt(1));
                        crearHabitacionXReserva(rs.getInt(1));
                    }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conexionDB.cerrar();
        }

        //forEach habitacion, crearHabitacionesXReserva();
        /*
        ver cuántas y qué habitaciones necesito (SELECT * FROM habitaciones WHERE disponible = 1 AND...)
        UPDATE `habitaciones` SET `codReserva`=?, `disponible`='0' WHERE  `idHabitacion`=?;
         */
    }

    private boolean crearReserva(int estado, int cantidadHuespedes, int cantidadHabitaciones){
        String sql = "INSERT INTO `reservas` (`estado`, `cantidadHuespedes`, `cantidadHabitaciones`) VALUES (?,?,?);";
        ConexionDB conexionDB = new ConexionDB(dbName,dbUser,dbPwd,sql);
        PreparedStatement pstmt = conexionDB.getPstmt();
        try {
            pstmt.setInt(1, estado);
            pstmt.setInt(2, cantidadHuespedes);
            pstmt.setInt(3, cantidadHabitaciones);
            this.codReserva = conexionDB.ejecutarRetornarKey();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            conexionDB.cerrar();
        }
        return (this.codReserva>0);
    }

    public Reserva(int codReserva, Estado estado, String nombreReserva, int cantidadHuespedes, int cantidadHabitaciones, String mail, String telefono) {
        this.codReserva = codReserva;
        this.estado = estado;
        huespedes.add(new Huesped(nombreReserva, mail, telefono));
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.cantidadHuespedes = cantidadHuespedes;
    }

    public void agregarHuesped(String nombre) {
        huespedes.add(new Huesped(nombre, codReserva));
    }

    public void agregarHuesped(String nombre, String mail, String telefono) {
        huespedes.add(new Huesped(nombre, mail, telefono, codReserva));
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public List<Huesped> getHuespedes() {
        return huespedes;
    }

    public int getCodReserva() {
        return codReserva;
    }

    public void setCodReserva(int codReserva) {
        this.codReserva = codReserva;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCantidadHuespedes() {
        return cantidadHuespedes;
    }

    public void setCantidadHuespedes(int cantidadHuespedes) {
        this.cantidadHuespedes = cantidadHuespedes;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "codReserva=" + codReserva +
                ", estado=" + estado +
                ", cantidadHuespedes=" + cantidadHuespedes +
                ", cantidadHabitaciones=" + cantidadHabitaciones +
                ", habitaciones=" + habitaciones +
                ", huespedes=" + huespedes +
                '}';
    }
}
