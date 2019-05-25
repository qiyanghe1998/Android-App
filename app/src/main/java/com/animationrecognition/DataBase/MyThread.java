package com.animationrecognition.DataBase;

/**
 * class to provide a thread that satisfy the database requirements
 */
public class MyThread extends Thread {
    protected final Work work;

    MyThread(Work work){
        this.work = work;
    }
}

/**
 * class to callback
 */
class Work{
    protected Object x;
    Work(Object x){this.x = x;}

    /**
     * method to get Object x
     *
     * @return x
     */
    protected Object getX(){
        return x;
    }
}
