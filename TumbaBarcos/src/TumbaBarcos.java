import java.util.*;

public class TumbaBarcos {
	
		public static final char AGUA='A', AGUA_NO_TOCADO='.', TOCADO='X';
		
		public static final int TAMANNIO=10;
		
		public static Scanner dato = new Scanner (System.in);
	
	public static void main (String [] args) throws InterruptedException {
		
		//Declaración Variables
		
		int puntosJugador , puntosOrdenador , fila , columna;
		
		puntosJugador = puntosOrdenador = 24;
		
		boolean juegoTerminado = false, disparoCorrecto = false;
		
		char resultadoDisparo='A';
		
		//Declaración Mapas
		
		char mapaUsuario [] [] = new char [TAMANNIO] [TAMANNIO];
		
		char mapaOrdenador [] [] = new char [TAMANNIO] [TAMANNIO];
		
		char mapaOrdenadorParaUsuario [] [] = new char [TAMANNIO] [TAMANNIO];
		
		int [] disparo = new int [2] ; 
		
		//Inicializar mapas y registrar barcos
		
		inicializacionMapa(mapaUsuario);
		
		inicializacionMapa(mapaOrdenador);
		
		inicializacionMapa(mapaOrdenadorParaUsuario);
		
		imprimirMapa("MAPA USUARIO", mapaUsuario);
		
		imprimirMapa("MAPA ORDENADOR", mapaOrdenador);
		
		imprimirMapa("MAPA ORDENADOR PARA USUARIO", mapaOrdenadorParaUsuario);
		
		registrarBarcos(mapaUsuario);
		
		registrarBarcos(mapaOrdenador);
		
		imprimirMapa("MAPA USUARIO", mapaUsuario);
		
		imprimirMapa("MAPA ORDENADOR PARA USUARIO", mapaOrdenadorParaUsuario);
		
			while (puntosOrdenador != 0 && puntosJugador != 0) {
				
				//Dispara Usuario
				
				System.out.println("TU TURNO\n=======\n");
				
				System.out.println("PUNTOS ORDENADOR: "+ puntosOrdenador);
				
				System.out.println("TUS PUNTOS: "+ puntosJugador);
				
				imprimirMapa("MAPA ORDENADOR PARA USUARIO", mapaOrdenadorParaUsuario);
				
				disparoCorrecto = false;
				
				while (!disparoCorrecto) {
					
					disparo = pedirCoordenada();
					
					if (disparo[0] != -1) {
						
						resultadoDisparo = evaluarDisparo(disparo , mapaOrdenador);
						
						if (resultadoDisparo == 'E') 
							
							System.out.println("DISPARO NO VALIDO");
							
						else {
							
							disparoCorrecto = true ;
							
							fila = disparo[0] ;
							
							columna = disparo[1] ;
							
							mapaOrdenadorParaUsuario [fila] [columna] = resultadoDisparo ;
							
							mapaOrdenador [fila] [columna] = resultadoDisparo ;
							
							if (resultadoDisparo == 'X') 
							
							puntosOrdenador--;
							
						}
						
					} else System.out.println("COORDENADAS NO VALIDAS");
		
				}
				
				System.out.println("PUNTOS ORDENADOR: "+ puntosOrdenador);
				
				imprimirMapa("MAPA ORDENADOR PARA USUARIO", mapaOrdenadorParaUsuario);
				
				Thread.sleep(3000);
				
				if (puntosOrdenador != 0) {
					
					//Dispara Ordenador
					
					System.out.println("TURNO DEL ORDENADOR\n==========\n");
				
					System.out.println("PUNTOS ORDENADOR: "+ puntosOrdenador);
				
					System.out.println("TUS PUNTOS: "+ puntosJugador);
					
					disparoCorrecto = false ;
					
					while (!disparoCorrecto) {
						
						disparo = generarDisparoAleatorio () ;
						
						resultadoDisparo = evaluarDisparo (disparo , mapaUsuario) ;
						
						if (resultadoDisparo == 'E') 
							
							System.out.println("DISPARO NO VALIDO");
							
						else {
							
							disparoCorrecto = true ;
							
							fila = disparo[0] ;
							
							columna = disparo[1] ;
							
							mapaUsuario [fila] [columna] = resultadoDisparo ;
							
							if (resultadoDisparo == 'X') 
							
							puntosJugador--;
							
						}
						
					}
					
					System.out.println("TUS PUNTOS: "+ puntosJugador);
					
					System.out.println("PUNTOS ORDENADOR: "+ puntosOrdenador);
				
					imprimirMapa("TU MAPA", mapaUsuario);
				
					Thread.sleep(3000);
				
				}
				
			}
			
			if (puntosOrdenador == 0) System.out.println("ENHORABUENA. HAS GANADO LA PARTIDA");
			
			else  System.out.println("LO SIENTO. HAS PERDIDO");
		
		}
	
	public static void inicializacionMapa(char [] [] m) {
		
		for (int f = 0; f < m.length; f++) {
			
			for (int c = 0; c < m[f].length; c++) {
				
				m[f][c] = AGUA_NO_TOCADO;
				
			}
			
		}
		
	}
	
	public static void registrarBarcos(char [] [] m) {
		
		//Declaración de vectores 
		
		boolean colocado;
		
		int fila , columna , dir ;
		
		int barcos [] = { 5, 5 , 3 , 3 , 3 , 1 , 1 , 1 , 1 , 1 };
		
		char direccion [] = { 'V' , 'H' };
		
		for (int tamanioBarco : barcos) {
			
			colocado = false;
			
			while (!colocado) {
				
				//Generar coordenadas y direccion aleatoriamente
				
				fila = (int) (Math.random()*TAMANNIO);
				
				columna = (int) (Math.random()*TAMANNIO);
				
				dir = (int) (Math.random()*2);
								
				//Comprobar si el barco cabe a partir de las coordenadas
				
				if ((direccion[dir]=='V' && (fila+tamanioBarco)<TAMANNIO) || (direccion[dir]=='H' && (columna+tamanioBarco)<TAMANNIO)) {
					
					//Comprobar si el barco no choca con otro
					
					boolean vacio = true;
					
					if (direccion[dir]=='V') {
						
						int f = 0;
						
						while (f<tamanioBarco && vacio) {
							
							if (m[fila+f][columna]!=AGUA_NO_TOCADO)	vacio=false;
						
							else f++;
						
						}
						
					} else {
						
						int c = 0;
						
						while (c<tamanioBarco && vacio) {
							
							if (m[fila][columna+c]!=AGUA_NO_TOCADO) vacio=false;
						
							else c++;
						
						}
					
					}
					
					if (vacio) {
						
						//Colocar el barco en las coordenadas
						
						for (int i = 0; i < tamanioBarco; i++) {
							
							if (direccion[dir]=='V') m[fila+i][columna]=Integer.toString(tamanioBarco).charAt(0);
								
							else m[fila][columna+i]=Integer.toString(tamanioBarco).charAt(0);
								
						}
						
						colocado=true;
						
					}
					
				}
				
			}
			
		}
			
	}
	
	public static void imprimirMapa(String titulo, char [] [] m) {
		
		//Crear vector con las letras de las cabeceras de las filas
		
		char [] letrasCabecera = new char [TAMANNIO];
		
		for (int i = 0; i < letrasCabecera.length; i++) {
			
			letrasCabecera[i] = (char) ('A' + i);
			
		}
		
		//Visualizar el Array
		//Imprimir la 1º linea de cabecera
		
		System.out.print("\n"+titulo+"\n   ");
		
		for (int i = 0; i < TAMANNIO; i++) {
			
			System.out.printf("[%1S]  ", i);
		
		}
		
		System.out.println();
		
		for (int f = 0; f < m.length; f++) {
			
			System.out.printf("\n[%1S]  ", letrasCabecera[f]);
			
			for (int c = 0; c < m[f].length; c++) {
				
				System.out.printf(" %S   ", m [f] [c]);
				
			}
			
		}
		
	}
	
	public static int [] pedirCoordenada () {
		
		//Declaración del vector que guardará las coordenadas
		
		int [] c = new int [2] ;
		
		String coordenadas ;
		
		char coordenada1 , coordenada2 ;
		
		System.out.println("Introduce las coordenadas del disparo:") ;
		
		coordenadas = dato.next() ;
		
		coordenada1 = Character.toUpperCase(coordenadas.charAt(0)) ;
		
		coordenada2 = coordenadas.charAt(1) ;
		
		if (Character.getNumericValue(coordenada1) < Character.getNumericValue('A')+TAMANNIO && Character.getNumericValue(coordenada2) < Character.getNumericValue('0')+TAMANNIO) {
			
			c [0] = Character.getNumericValue(coordenada1) - Character.getNumericValue('A') ;
			
			c [1] = Character.getNumericValue(coordenada2) - Character.getNumericValue('0') ;
			
		} else {
			
			c [0] = -1 ;
			
			c [1] = -1 ;
			
		}
		
		return c;
		
	}
	
	public static char evaluarDisparo (int [] d , char [] [] m) {
		
		int fila , columna ;
		
		fila = d[0] ;
		
		columna = d[1] ;
		
		if (m [fila] [columna] >= '1' && m [fila] [columna] <= '5') return 'X' ;
		
		else if (m [fila] [columna] == AGUA_NO_TOCADO)  return 'A' ;

		else return 'E' ;
		
	}
	
	public static int [] generarDisparoAleatorio () {
		
		int [] c = new int [2] ;
		
		c [0] = (int) (Math.random () * TAMANNIO) ;
		
		c [1] = (int) (Math.random () * TAMANNIO) ;
		
		return c ;
		
	}
	
}