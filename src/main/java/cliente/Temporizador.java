package cliente;

import java.util.Timer;
import java.util.TimerTask;

public class Temporizador {
	private int minutos;
	Timer timer = new Timer();
	
	 public void iniciarTemporizador() {
	     TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                minutos++;
            }
	     };
	        timer.scheduleAtFixedRate(tarea, 0, 60000);
	       
	    }
	 public int terminarTemporizador() {
		 timer.cancel();
	     return minutos;
	 }
	 
}
