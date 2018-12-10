package com.imdg.practicas;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.hazelcast.core.IMap;
import com.imdg.pojos.Person;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PracticaIMDG2c {

    private final static Logger LOGGER = Logger.getLogger("com.imdg.practicas.PracticaIMDG2c");
    public static void main(String[] args) {
	    // Instanciar hazelcast y crear una cache
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
        IMap<String, Person> mapCustomers = hzInstance.getMap("practica5");
        try{
        ICountDownLatch latch = hzInstance.getCountDownLatch( "practica5" );
        latch.trySetCount(3);
        // Insertar un dato y arrancar 3 veces el main,
        mapCustomers.put("nodo3", new Person("Ander", 28053, "Gran via", "Gran via 1, Bilbao"));

        latch.countDown();
        // Leer el output de consola y ver como hazelcast va encontrando "miembros"
        // Comprobar que se conectan (en el output deberian verse 3 miembros en la consola) y capturarlo
        Config config = new Config();
        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

            latch.await(30, TimeUnit.SECONDS);
            System.out.println(mapCustomers.get("nodo1"));
            System.out.println(mapCustomers.get("nodo2"));
            System.out.println(mapCustomers.get("nodo3"));

            latch.destroy();
        }catch (InterruptedException e){
            LOGGER.log(Level.WARNING, "Hazelcast interrupted");
        }



    }
}
