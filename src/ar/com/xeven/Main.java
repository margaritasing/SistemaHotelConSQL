package ar.com.xeven;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Hotel hotel = new Hotel();

        int cant = hotel.cantidadHabitacionesDisponibles();
        System.out.printf("Tengo %d habitaciones disponibles.\n", cant);
        int cantHab = hotel.cantidadHabitacionesDisponibles(6);
        System.out.println("Tengo "+cantHab+" disponibles para 6 o más.");


        for(Reserva r: hotel.getReservas())
            System.out.println("Reserva: "+r);


        System.out.println("Crear nueva reserva");
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Cantidad de huéspedes: ");
        int cantidadHuespedes = Integer.parseInt(sc.nextLine());
        System.out.print("Cantidad de habitaciones: ");
        int cantidadHabitaciones = Integer.parseInt(sc.nextLine());
        System.out.print("Email: ");
        String mail = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        if(hotel.crearReserva(nombre, cantidadHuespedes, cantidadHabitaciones, mail, telefono))
            System.out.println("Reserva creada!");
        else
            System.out.println("No se pudo crear la reserva.");


    }
}
