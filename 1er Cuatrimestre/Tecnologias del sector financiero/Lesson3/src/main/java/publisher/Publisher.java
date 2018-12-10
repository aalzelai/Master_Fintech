package publisher;

import com.cnebrera.uc3.tech.lesson3.driver.BackOffMediaDriver;
import io.aeron.Aeron;
import io.aeron.Publication;
import io.aeron.driver.MediaDriver;

/**
 * Created by Andoni on 11/11/2018.
 */
public class Publisher {

    private static final String CHANNEL = "aeron:ipc";
    private static final int STREAM_ID = 2;

    public void execute(){

        final Aeron.Context ctx = new Aeron.Context();

        try{
            Aeron aeron = Aeron.connect();
            Publication publication = aeron.addPublication(CHANNEL, STREAM_ID);




        }catch (Exception e){
            e.printStackTrace();
        }
    }






}
