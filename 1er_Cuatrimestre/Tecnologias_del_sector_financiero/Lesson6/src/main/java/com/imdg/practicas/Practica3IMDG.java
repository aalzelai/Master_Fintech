package com.imdg.practicas;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.imdg.pojos.Person;

import java.util.ArrayList;

public class Practica3IMDG {

    public static void main(String[] args) {
	    // Instanciar hazelcast Cliente y crear una cache
        ClientConfig config = new ClientConfig();
        ArrayList<String> ips=new ArrayList();
        ips.add("127.0.0.1");
        config.getNetworkConfig().setAddresses(ips);

        HazelcastInstance client = HazelcastClient.newHazelcastClient( config );

        IMap<String, String> mapCustomers = client.getMap("practica5");

        System.out.println("Antes de meterlo: " + mapCustomers.get("EjemploValor"));
        mapCustomers.put("EjemploClave", "EjemploValor");

        System.out.println("Después de meterlo: " + mapCustomers.get("EjemploClave"));

        client.shutdown();




        //Vuestro código va aqui










        client.shutdown();

    }
}
