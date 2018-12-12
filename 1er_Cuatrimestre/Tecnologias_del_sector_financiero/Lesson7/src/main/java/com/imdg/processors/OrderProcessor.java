package com.imdg.processors;

import com.hazelcast.map.AbstractEntryProcessor;
import com.imdg.pojos.MarketOrder;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Sobremesa on 31/10/2016.
 */
public class OrderProcessor
        extends AbstractEntryProcessor<String,MarketOrder> implements Serializable {
    int volumenAcumulado = 0;
    int prevVol;

    /**
     * Metodo que debe procesar cada entrada y cambiar su volumen a 0, y devolver el antiguo
     * @param entry Hazelcast llamará a este metodo para cada entrada de la cache
     * @return Integer con el volumen que existia
     */
    @Override
    public Object process(Map.Entry<String,MarketOrder> entry) {
        if(volumenAcumulado < 30000) {
            volumenAcumulado+= entry.getValue().getVolume();
            prevVol = entry.getValue().getVolume();
            MarketOrder order = entry.getValue();

            order.setVolume(0);
            entry.setValue(order);
        }else{
            System.out.println("30.000 entries added");
            volumenAcumulado = 0;
        }
        return prevVol;
    }
}
