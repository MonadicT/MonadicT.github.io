/**
 * Code to illustrate garbage collector's handling of WeakReference objects.
 *
 * A number of weak references to byte arrays are added to a hash
 * map. For some of those byte arrays, strong references are also
 * retained. When garbage collector runs out of memory, it should
 * clear all soft references except the ones to which we have strong
 * references.
 */

import java.io.Console;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeakReferenceTest {
    static Console con = System.console();

    /* Cache with a bunch of WeakReference objects */
    static Map<String, WeakReference> cache
        = new HashMap<String, WeakReference>();

    /* Strong references to 10 of cached objects */
    static int strongRefCnt = 10;
    static Object[] strongRefs = new Object[strongRefCnt];

    /* Count of objects added to cache */
    static int cachedObjCnt = 1000;

    /* Size of byte array allocated */
    static int objSize = 1000000;

    /**
     * Populates cache with objects wrapped in WeakReference
     */
    static void populateCache() {
        for (int i = 0; i < cachedObjCnt; i++) {
            Object o = new byte[objSize];
            cache.put("Key" + i, new WeakReference(o));
            if (i < strongRefCnt) strongRefs[i] = o;
        }
    }

    /**
     * Allocate memory until a OOME occurs. This causes GC to handle
     * objects with soft references
     */
    static void allocMemory() {
        List l = new ArrayList();
        boolean oome = false;
        while (!oome) {
            try {
                l.add(new byte[objSize]);
            } catch (OutOfMemoryError  ex) {
                oome = true;
            }
        }
    }

    /**
     * Returns the number of entries in the cache with valid references
     */
    static int countValidCacheItems() {
        int cnt = 0;
        for (Reference r : cache.values()) {
            if (r.get() != null) cnt++;
        }
        return cnt;
    }

    public static void main (String[] args) {
        con.printf("Populating cache with %d objects\n", cachedObjCnt);
        populateCache();
        con.printf("Count of objects in cache after population: %d\n",
                   countValidCacheItems());
        System.gc();
        con.printf("Count of objects in cache after System.gc: %d\n",
                   countValidCacheItems());
        con.printf("Triggering OutOfMemoryError\n");
        allocMemory();
        con.printf("Count of objects in cache after OOME: %d\n",
                   countValidCacheItems());
    }
}
