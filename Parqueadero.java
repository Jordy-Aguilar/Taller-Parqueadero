import java.util.ArrayList;
public class Parqueadero {

    public static final int TAMANO = 40;


    public static final int NO_HAY_PUESTO = -1;


    public static final int PARQUEADERO_CERRADO = -2;


    public static final int CARRO_NO_EXISTE = -3;


    public static final int CARRO_YA_EXISTE = -4;


    public static final int HORA_INICIAL = 6;


    public static final int HORA_CIERRE = 20;


    public static final int TARIFA_INICIAL = 1200;


    private Puesto puestos[];


    private int tarifa;



    private int caja;


    private int horaActual;


    private boolean abierto;
    public double darTiempoPromedio() {
        int totalTiempo = 0;
        int totalCarros = 0;

        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                totalTiempo += carro.darTiempoEnParqueadero(horaActual);
                totalCarros++;
            }
        }

        if (totalCarros == 0) {
            return 0;
        }

        return (double) totalTiempo / totalCarros;
    }

    public boolean hayCarroMasDeOchoHoras() {
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero > 8) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Carro> darCarrosMasDeTresHorasParqueados() {
        ArrayList<Carro> carrosMasDeTresHoras = new ArrayList<>();

        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero > 3) {
                    carrosMasDeTresHoras.add(carro);
                }
            }
        }

        return carrosMasDeTresHoras;
    }

    public boolean hayCarrosPlacaIgual() {
        for (int i = 0; i < TAMANO - 1; i++) {
            if (puestos[i].estaOcupado()) {
                Carro carro1 = puestos[i].darCarro();
                for (int j = i + 1; j < TAMANO; j++) {
                    if (puestos[j].estaOcupado()) {
                        Carro carro2 = puestos[j].darCarro();
                        if (carro1.darPlaca().equals(carro2.darPlaca())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int contarCarrosQueComienzanConPlacaPB() {
        int contador = 0;

        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                if (carro.darPlaca().startsWith("PB")) {
                    contador++;
                }
            }
        }

        return contador;
    }

    public boolean hayCarroCon24Horas() {
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero >= 24) {
                    return true;
                }
            }
        }
        return false;
    }

    public int desocuparParqueadero() {
        int cantidadCarrosSacados = 0;

        for (int i = 0; i < TAMANO; i++) {
            if (puestos[i].estaOcupado()) {
                puestos[i].sacarCarro();
                cantidadCarrosSacados++;
            }
        }

        return cantidadCarrosSacados;
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




    public Parqueadero( )
    {
        horaActual = HORA_INICIAL;
        abierto = true;
        tarifa = TARIFA_INICIAL;
        caja = 0;
        // Crea el arreglo de puestos e inicializa cada uno de ellos
        puestos = new Puesto[TAMANO];
        for( int i = 0; i < TAMANO; i++ )
            puestos[ i ] = new Puesto( i );

    }



    public String darPlacaCarro( int pPosicion )
    {
        String respuesta = "";
        if( estaOcupado( pPosicion ) )
        {
            respuesta = "Placa: " + puestos[ pPosicion ].darCarro( ).darPlaca( );
        }
        else
        {
            respuesta = "No hay un carro en esta posici�n";
        }

        return respuesta;
    }

    /**
     * Ingresa un un carro al parqueadero. <br>
     * <b>pre: </b> El arreglo de puestos no est� vac�o. <br>
     * <b>post: </b>El carro qued� parqueado en el puesto indicado.
     * @param pPlaca Placa del carro que ingresa. pPlaca != null.
     * @return Puesto en el que debe parquear. <br>
     *         Si el parqueadero est� lleno retorna el valor NO_HAY_PUESTO. <br>
     *         Si el parqueadero est� cerrado retorna el valor PARQUEADERO_CERRADO.
     */
    public int entrarCarro( String pPlaca )
    {
        int resultado = 0;
        if( !abierto )
        {
            resultado = PARQUEADERO_CERRADO;
        }
        else
        {
            // Buscar en el parqueadero un carro con la placa indicada
            int numPuestoCarro = buscarPuestoCarro( pPlaca.toUpperCase( ) );
            if( numPuestoCarro != CARRO_NO_EXISTE )
            {
                resultado = CARRO_YA_EXISTE;
            }

            // Buscar un puesto libre para el carro y agregarlo
            resultado = buscarPuestoLibre( );
            if( resultado != NO_HAY_PUESTO )
            {
                Carro carroEntrando = new Carro( pPlaca, horaActual );
                puestos[ resultado ].parquearCarro( carroEntrando );
            }
        }

        return resultado;
    }


    public int sacarCarro( String pPlaca )
    {
        int resultado = 0;
        if( !abierto )
        {
            resultado = PARQUEADERO_CERRADO;
        }
        else
        {
            int numPuesto = buscarPuestoCarro( pPlaca.toUpperCase( ) );
            if( numPuesto == CARRO_NO_EXISTE )
            {
                resultado = CARRO_NO_EXISTE;
            }
            else
            {
                Carro carro = puestos[ numPuesto ].darCarro( );
                int nHoras = carro.darTiempoEnParqueadero( horaActual );
                int porPagar = nHoras * tarifa;
                caja = caja + porPagar;
                puestos[ numPuesto ].sacarCarro( );
                resultado = porPagar;
            }
        }

        return resultado;
    }



    public int darMontoCaja( )
    {
        return caja;
    }

    /**
     * Indica la cantidad de puestos libres que hay.
     * @return El n�mero de espacios vac�os en el parqueadero.
     */
    public int calcularPuestosLibres( )
    {
        int puestosLibres = 0;
        for( Puesto puesto : puestos )
        {
            if( !puesto.estaOcupado( ) )
            {
                puestosLibres = puestosLibres + 1;
            }
        }
        return puestosLibres;
    }


    public void cambiarTarifa( int pTarifa )
    {
        tarifa = pTarifa;
    }


    private int buscarPuestoLibre( )
    {
        int puesto = NO_HAY_PUESTO;
        for( int i = 0; i < TAMANO && puesto == NO_HAY_PUESTO; i++ )
        {
            if( !puestos[ i ].estaOcupado( ) )
            {
                puesto = i;
            }
        }
        return puesto;
    }


    private int buscarPuestoCarro( String pPlaca )
    {
        int puesto = CARRO_NO_EXISTE;
        for( int i = 0; i < TAMANO && puesto == CARRO_NO_EXISTE; i++ )
        {
            if( puestos[ i ].tieneCarroConPlaca( pPlaca ) )
            {
                puesto = i;
            }
        }
        return puesto;
    }


    public void avanzarHora( )
    {
        if( horaActual <= HORA_CIERRE )
        {
            horaActual = ( horaActual + 1 );
        }
        if( horaActual == HORA_CIERRE )
        {
            abierto = false;
        }
    }


    public int darHoraActual( )
    {
        return horaActual;
    }


    public boolean estaAbierto( )
    {
        return abierto;
    }


    public int darTarifa( )
    {
        return tarifa;
    }


    public boolean estaOcupado( int pPuesto )
    {
        boolean ocupado = puestos[ pPuesto ].estaOcupado( );
        return ocupado;
    }


    public String metodo1( )
    {
        return "respuesta 1";
    }


    public String metodo2( )
    {
        return "respuesta 2";
    }


}