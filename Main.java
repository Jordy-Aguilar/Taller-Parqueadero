import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Parqueadero parqueadero = new Parqueadero();
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    ingresarCarro(parqueadero, scanner);
                    break;
                case 2:
                    darSalidaCarro(parqueadero, scanner);
                    break;
                case 3:
                    mostrarIngresos(parqueadero);
                    break;
                case 4:
                    mostrarPuestosDisponibles(parqueadero);
                    break;
                case 5:
                    avanzarReloj(parqueadero);
                    break;
                case 6:
                    cambiarTarifa(parqueadero, scanner);
                    break;
                case 7:
                    mostrarEstadisticasAdicionales(parqueadero);
                    break;

                case 0:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
            }

        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("------ Menú Parqueadero ------");
        System.out.println("1. Ingresar un carro al parqueadero");
        System.out.println("2. Dar salida a un carro del parqueadero");
        System.out.println("3. Mostrar ingresos del parqueadero");
        System.out.println("4. Consultar la cantidad de puestos disponibles");
        System.out.println("5. Avanzar el reloj del parqueadero");
        System.out.println("6. Cambiar la tarifa del parqueadero");
        System.out.println("7. Mostrar estadísticas adicionales");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void ingresarCarro(Parqueadero parqueadero, Scanner scanner) {
        System.out.print("Ingrese la placa del carro: ");
        String placa = scanner.nextLine();
        int resultado = parqueadero.entrarCarro(placa);

        switch (resultado) {
            case Parqueadero.NO_HAY_PUESTO:
                System.out.println("El parqueadero está lleno. No hay puestos disponibles.");
                break;
            case Parqueadero.PARQUEADERO_CERRADO:
                System.out.println("El parqueadero está cerrado en este momento.");
                break;
            case Parqueadero.CARRO_YA_EXISTE:
                System.out.println("Ya hay un carro con la misma placa en el parqueadero.");
                break;
            default:
                System.out.println("Carro ingresado correctamente en el puesto " + resultado);
                break;
        }
    }

    private static void darSalidaCarro(Parqueadero parqueadero, Scanner scanner) {
        System.out.print("Ingrese la placa del carro que desea sacar: ");
        String placa = scanner.nextLine();
        int resultado = parqueadero.sacarCarro(placa);

        switch (resultado) {
            case Parqueadero.CARRO_NO_EXISTE:
                System.out.println("No hay un carro con la placa especificada en el parqueadero.");
                break;
            case Parqueadero.PARQUEADERO_CERRADO:
                System.out.println("El parqueadero está cerrado en este momento.");
                break;
            default:
                System.out.println("Carro sacado correctamente. Monto a pagar: $" + resultado);
                break;
        }
    }

    private static void mostrarIngresos(Parqueadero parqueadero) {
        int ingresos = parqueadero.darMontoCaja();
        System.out.println("Ingresos totales del parqueadero: $" + ingresos);
    }

    private static void mostrarPuestosDisponibles(Parqueadero parqueadero) {
        int puestosLibres = parqueadero.calcularPuestosLibres();
        System.out.println("Cantidad de puestos disponibles: " + puestosLibres);
    }

    private static void avanzarReloj(Parqueadero parqueadero) {
        parqueadero.avanzarHora();
        System.out.println("Reloj del parqueadero avanzado a la hora: " + parqueadero.darHoraActual());
    }

    private static void cambiarTarifa(Parqueadero parqueadero, Scanner scanner) {
        System.out.print("Ingrese la nueva tarifa por hora: ");
        int nuevaTarifa = scanner.nextInt();
        parqueadero.cambiarTarifa(nuevaTarifa);
        System.out.println("Tarifa actualizada correctamente.");
    }
    private static void mostrarEstadisticasAdicionales(Parqueadero parqueadero) {
        double tiempoPromedio = parqueadero.darTiempoPromedio();
        System.out.println("Tiempo promedio de los carros en el parqueadero: " + tiempoPromedio + " horas.");

        boolean hayCarroMasDeOchoHoras = parqueadero.hayCarroMasDeOchoHoras();
        System.out.println("¿Hay algún carro que lleve más de 8 horas parqueado?: " + hayCarroMasDeOchoHoras);

        ArrayList<Carro> carrosMasDeTresHoras = parqueadero.darCarrosMasDeTresHorasParqueados();
        System.out.println("Carros parqueados por más de tres horas:");
        for (Carro carro : carrosMasDeTresHoras) {
            System.out.println("- Placa: " + carro.darPlaca());
        }

        boolean hayCarrosPlacaIgual = parqueadero.hayCarrosPlacaIgual();
        System.out.println("¿Hay dos carros parqueados con la misma placa?: " + hayCarrosPlacaIgual);

        int carrosPlacaPB = parqueadero.contarCarrosQueComienzanConPlacaPB();
        System.out.println("Cantidad de carros con placa que comienza con 'PB': " + carrosPlacaPB);

        boolean hayCarroCon24Horas = parqueadero.hayCarroCon24Horas();
        System.out.println("¿Hay algún carro que lleve 24 o más horas parqueado?: " + hayCarroCon24Horas);

        int cantidadCarrosSacados = parqueadero.desocuparParqueadero();
        System.out.println("Cantidad de carros sacados: " + cantidadCarrosSacados);
    }
}