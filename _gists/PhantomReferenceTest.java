/* Illustration of phantom references handling */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * An object which requires cleanup action when reclaimed
 */
class MyObject {
    FileOutputStream f;

    public MyObject(FileOutputStream f) {
        this.f = f;
    }
}

/**
 * Reference to MyObject which captures data needed for cleanup action
 * and defines a cleanup method
 */
class MyObjectReference extends PhantomReference {
    /** Must close and delete when referrent is reclaimed */
    FileOutputStream f;

    /** Constructor */
    public MyObjectReference(Object o, ReferenceQueue rq, FileOutputStream f) {
        super(o, rq);
        this.f = f;
    }

    /** Closes and deletes the file */
    public void cleanup() {
        System.out.println("Cleanup invoked: " + f);
    }
}

/**
 * Thread which monitors the referene queue and invokes clean action
 */
class Cleaner extends Thread {
    ReferenceQueue rq;

    Cleaner(ReferenceQueue rq) {
        this.rq = rq;
        setDaemon(true);
    }

    public void run() {
        boolean stop = false;
        while (!stop) {
            try {
                MyObjectReference r = (MyObjectReference) rq.remove();
                r.cleanup();
            } catch (InterruptedException ex) {
                stop = true;
            }
        }
    }
}

public class PhantomReferenceTest {
    public static void main(String[] args)
        throws FileNotFoundException, InterruptedException
    {
        ReferenceQueue rq = new ReferenceQueue();
        Cleaner cleaner = new Cleaner(rq);
        FileOutputStream f = new FileOutputStream("foo.bar");
        MyObject o = new MyObject(f);
        MyObjectReference fr = new MyObjectReference(o, rq, f);

        cleaner.start();

        /* At this point, we have strong references to o and GC should not add it rq */
        System.gc();
        if (rq.poll() != null)
            System.out.println("Error: reference queue is not empty!");
        else
            System.out.println("reference queue is empty as expected");

        /* Clear strong references and perform GC */
        o = null;
        System.gc();
        Thread.sleep(1000);

        /* By this time, the cleanup method should have run and printed the message */
    }
}
