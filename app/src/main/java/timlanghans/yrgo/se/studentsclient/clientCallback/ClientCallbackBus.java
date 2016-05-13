package timlanghans.yrgo.se.studentsclient.clientCallback;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
public class ClientCallbackBus implements IClientCallbackBus{

    private static final ClientCallbackBus bus = new ClientCallbackBus();

    private static final String LOG_TAG = ClientCallbackBus.class.getName();

    private final List<IClientCallbackListener> listeners = new ArrayList<>();


    public static IClientCallbackBus getInstance(){
        return bus;
    }


    @Override
    public void registerListener(IClientCallbackListener listener) {
        listeners.add(listener);
      Log.d(LOG_TAG, "added IClientCallbacklistener: " + listener.toString());
    }

    @Override
    public boolean unregisterListener(IClientCallbackListener listener) {
      Log.d(LOG_TAG, "unregistered IClientCallbacklistener: " + listener.toString());
        return listeners.remove(listener);
    }

    @Override
    public void broadcastEvent(CallbackEvent event) {
        for (IClientCallbackListener l : listeners){
            l.onEvent(event);
        }
      Log.d(LOG_TAG, "broadcasted Event to " + listeners.size() + " " +
              "CallbackListeners");
    }

}
