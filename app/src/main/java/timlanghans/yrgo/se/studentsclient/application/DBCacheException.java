package timlanghans.yrgo.se.studentsclient.application;

/**
 *
 * Wrapper Exception for different possible Exceptions regarding the local
 * database.
 *
 * NOT IMPLEMENTED YET IN THE APPLICATION!
 *
 * Created by timlanghans on 16-04-19 / 16.
 */
public class DBCacheException extends Exception {

    public DBCacheException(String message){
        super(message);
    }

    public DBCacheException(Throwable cause){
        super(cause);
    }

    public DBCacheException(String message, Throwable cause){
        super (message, cause);
    }

}