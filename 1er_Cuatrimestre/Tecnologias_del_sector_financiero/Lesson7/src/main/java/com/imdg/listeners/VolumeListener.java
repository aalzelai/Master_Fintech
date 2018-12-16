package com.imdg.listeners;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapListener;
import com.imdg.pojos.MarketOrder;
import com.sun.media.jfxmedia.logging.Logger;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Sobremesa on 31/10/2016.
 */
public class VolumeListener
            implements EntryAddedListener<String, MarketOrder>,
        EntryUpdatedListener<String, MarketOrder>, Serializable {

    private String instrumentoAControlar;
    private int volumenAcumulado=0;
    private HashMap<String, MarketOrder> orders = new HashMap<>();

    public VolumeListener(String instrument) {
        this.instrumentoAControlar=instrument;
    }

    /**
     * Escuchar entradas que se añaden y sumarlo al volumen/imprimir alerta si llegamos a 30000
     * @param entryEvent
     */
    @Override
    public void entryAdded(EntryEvent<String, MarketOrder> entryEvent) {

        if(volumenAcumulado < 30000) {
            volumenAcumulado+= entryEvent.getValue().getVolume();
            orders.put(entryEvent.getKey(), entryEvent.getValue());
        }else{
            System.out.println("30.000 entries added");
            volumenAcumulado = 0;
        }
    }


    /**
     * Escuchar entradas que se añaden, restar valor antiguo y
     * sumar el nuevo al volumen/imprimir alerta si llegamos a 30000
     * @param entryEvent
     */
    @Override
    public void entryUpdated(EntryEvent<String, MarketOrder> entryEvent) {

        if(volumenAcumulado < 30000) {
            try {
                volumenAcumulado+= (entryEvent.getValue().getVolume() - orders.get(entryEvent.getKey()).getVolume());
                orders.put(entryEvent.getKey(), entryEvent.getValue());
            }catch (NullPointerException e){
                Logger.logMsg(Logger.ERROR, "The selected order has not been found");
            }

        }else{
            System.out.println("30.000 entries updated");
            volumenAcumulado = 0;
        }

    }
}
