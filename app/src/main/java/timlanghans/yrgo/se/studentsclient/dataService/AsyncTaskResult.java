package timlanghans.yrgo.se.studentsclient.dataService;

import timlanghans.yrgo.se.studentsclient.application.NetworkException;

/**
 * adapted version of a solution created by FercoCQ
 * on http://stackoverflow.com/questions/1739515
 *
 * @author timlanghans
 *
 * Trying to solve the problem to get back an Exception from a AsyncTask
 */

public class AsyncTaskResult<T, E extends NetworkException> {
    private T result;
    private  E error;

    public AsyncTaskResult(T result) {
        super();
        this.result = result;
    }

    public AsyncTaskResult(E error) {
        super();
        this.error = error;
    }

    public AsyncTaskResult(T result , E error){
        super();
        this.result = result;
        this.error = error;
    }


    public T getResult() {
        return result;
    }

    public E getError() {
        return error;
    }

}