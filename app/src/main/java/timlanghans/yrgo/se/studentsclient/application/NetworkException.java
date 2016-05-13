package timlanghans.yrgo.se.studentsclient.application;

/**
 * Wrapper for different Exceptions concerning the fetching and translation
 * of new data from not local resources.
 *
 * Created by timlanghans on 16-04-19 / 16.
 */
public class NetworkException extends Exception {

    public enum ErrorType{
        /** Fetched content is not properly formatted and cannot be read */
        CONTENT_NOT_VALID,
        /** The HTTP-service request resulted in an Exception */
        HTTP_SERVICE_ERROR,
        /** The server responded with a status 400 or 404 to the request */
        STATUS_4XX_ERROR,
        /** The unit has not connection to the internet */
        NO_INTERNET,
        /** Not otherwise specified error */
        NOT_SPECIFIED;
    }

    private ErrorType errorType = ErrorType.NOT_SPECIFIED;


    public NetworkException( String message ){
        super(message);
    }

    public NetworkException(Throwable cause){
        super(cause);
    }

    public NetworkException(String message, Throwable cause){
        super (message, cause);
    }

    public NetworkException setErrorType(ErrorType type){
        this.errorType = type;
        return this;
    };

    public ErrorType getErrorType(){
        return errorType;
    }

}
