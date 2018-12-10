package publisher;

import com.cnebrera.uc3.tech.lesson3.driver.BackOffMediaDriver;
import io.aeron.Aeron;
import io.aeron.Publication;
import io.aeron.driver.MediaDriver;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;

import java.util.concurrent.TimeUnit;

/**
 * Created by Andoni on 11/11/2018.
 */
public class Publisher {

    private static final String CHANNEL = "aeron:ipc";
    private static final int STREAM_ID = 2;
    private static final int NUMBER_OF_MESSAGES = 100000;
    private static final UnsafeBuffer BUFFER = new UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64));



    public void execute(){

        final Aeron.Context ctx = new Aeron.Context();

        try{
            Aeron aeron = Aeron.connect();
            Publication publication = aeron.addPublication(CHANNEL, STREAM_ID);
            for(long i = 0; i < NUMBER_OF_MESSAGES; i++){

                final String message = "Hello!" + i;
                final byte[] messageBytes = message.getBytes();
                BUFFER.putBytes(0, messageBytes);
                System.out.println("Offering " + i + "/" + NUMBER_OF_MESSAGES + " - ");

                final long result = publication.offer(BUFFER, 0, messageBytes.length);

                if (result < 0L)
                {
                    if (result == Publication.BACK_PRESSURED)
                    {
                        System.out.println("Offer failed due to back pressure");
                    }
                    else if (result == Publication.NOT_CONNECTED)
                    {
                        System.out.println("Offer failed because publisher is not yet connected to subscriber");
                    }
                    else if (result == Publication.ADMIN_ACTION)
                    {
                        System.out.println("Offer failed because of an administration action in the system");
                    }
                    else if (result == Publication.CLOSED)
                    {
                        System.out.println("Offer failed publication is closed");
                        break;
                    }
                    else
                    {
                        System.out.println("Offer failed due to unknown reason");
                    }


                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }






}
