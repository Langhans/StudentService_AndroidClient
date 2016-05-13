package timlanghans.yrgo.se.studentsclient.clientCallback;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Simple implementation of the Observer pattern for sending update events
 * from the controller to the UI. Activities that implement the
 * ICallbackListener interface can un/register to receiving CallbackEvents by
 * using the un/registerListener() methods.
 *
 * I tried to loosen the ties between controller och UI. The bus makes it
 * possible for different Activities/Views to react differently and more
 * flexible to the same event.
 *
 * Created by timlanghans on 16-04-26 / 17.
 */
public class ClientCallbackBusWEAK implements IClientCallbackBus{

    private static final ClientCallbackBusWEAK bus = new
            ClientCallbackBusWEAK();

    private static final String LOG_TAG = ClientCallbackBusWEAK.class.getName();

    private final WeakHashMap<Integer,IClientCallbackListener> listeners =
            new WeakHashMap<>();



    public static IClientCallbackBus getInstance(){
        return bus;
    }


    @Override
    public void registerListener(IClientCallbackListener listener) {
        listeners.put(listener.hashCode(),listener);
      Log.d(LOG_TAG, "added IClientCallbacklistener: " + listener.toString());
    }

    @Override
    public boolean unregisterListener(IClientCallbackListener listener) {
      /*
      Log.d(LOG_TAG, "unregistered IClientCallbacklistener: " + listener
              .toString());
        return listeners.remove(listener);
       */

      // TODO should not be needed any more
      return true;
    }

    @Override
    public void broadcastEvent(CallbackEvent event) {
        for (IClientCallbackListener l : listeners.values()){
            l.onEvent(event);
        }
      Log.d(LOG_TAG, "broadcasted Event to " + listeners.size() + " " +
              "CallbackListeners");
    }

}
