package ar.com.xeven;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    List<Habitacion> habitaciones = new ArrayList<>();
    List<Reserva> reservas = new ArrayList<>();
    List<Huesped> huespedes = new ArrayList<>();
    private final String dbName = "hotel";
    private final String dbUser = "root";
    private final String dbPwd = "ange09lina08";

    public Hotel() {
    }

    public boolean crearReserva(String nombre, int cantidadHuespedes, int cantidadHabitaciones, String mail, String telefono){
        boolean reservado = false;
        // ACUERDO NUESTRO
        int capacidad = (int) Math.ceil(cantidadHuespedes / cantidadHabitaciones);
        if(cantidadHabitacionesDisponibles(capacidad) >= cantidadHabitaciones){
            reservas.add(new Reserva(nombre, cantidadHuespedes, cantidadHabitaciones, mail, telefono));
            reservado = true;
        }
        return reservado;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int cantidadHabitacionesDisponibles(){
        return cantidadHabitacionesDisponibles(0);
    }
    public int cantidadHabitacionesDisponibles(int cantidad){
        int cantidadHabitacionesDisponibles = 0;
        ConexionDB conexionDB = new ConexionDB(dbName,dbUser,dbPwd);
        ResultSet resultados = conexionDB.consultar("SELECT COUNT(*) AS cantidad FROM habitaciones WHERE disponible = TRUE AND capacidad >= "+cantidad+";");
        try{
            if(resultados!=null)
                if(resultados.next())
                    cantidadHabitacionesDisponibles = resultados.getInt("cantidad");
        }catch (SQLException e){
            System.out.println("Error!");
        }finally {
            conexionDB.cerrar();
        }
        return cantidadHabitacionesDisponibles;
    }

    public List<Reserva> getReservas() {
        ConexionDB conexionDB = new ConexionDB(dbName, dbUser, dbPwd);
        ResultSet resultados = conexionDB.consultar("SELECT reservas.codReserva, reservas.estado, huespedes.nombre, huespedes.email, huespedes.telefono, reservas.cantidadHuespedes, reservas.cantidadHabitaciones FROM reservas INNER JOIN huespedes ON reservas.codReserva = huespedes.codReserva;");
        try{
            if(resultados!=null){
                this.reservas.clear();
                while(resultados.next()){ // para cada reserva
                    int codReserva = resultados.getInt("reservas.codReserva");
                    int nroEstado = resultados.getInt("reservas.estado");
                    Estado estado = Estado.values()[nroEstado];
                    String nombreReserva = resultados.getString("huespedes.nombre");
                    int cantidadHuespedes = resultados.getInt("reservas.cantidadHuespedes");
                    int cantidadHabitaciones = resultados.getInt("reservas.cantidadHabitaciones");
                    String email = resultados.getString("huespedes.email");
                    String telefono = resultados.getString("huespedes.telefono");
                    this.reservas.add(new Reserva(codReserva, estado, nombreReserva, cantidadHuespedes, cantidadHabitaciones, email, telefono));
                }
            }
        }catch (SQLException e){
            System.out.println("No hay reservas");
        }finally {
            conexionDB.cerrar();
        }
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Huesped> getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(List<Huesped> huespedes) {
        this.huespedes = huespedes;
    }
}
