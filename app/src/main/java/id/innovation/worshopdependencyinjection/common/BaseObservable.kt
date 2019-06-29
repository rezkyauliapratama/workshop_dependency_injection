package id.innovation.worshopdependencyinjection.common

import java.util.*
import java.util.concurrent.ConcurrentHashMap


/**
 * Base class for observable entities in the application
 * @param <LISTENER_CLASS> the class of the listeners
</LISTENER_CLASS> */
abstract class BaseObservable<LISTENER_CLASS> {

    // thread-safe set of listeners
    private val mListeners = Collections.newSetFromMap(
        ConcurrentHashMap<LISTENER_CLASS, Boolean>(1)
    )

    /**
     * Get a reference to the unmodifiable set containing all the registered listeners.
     */
    protected val listeners: Set<LISTENER_CLASS>
        get() = Collections.unmodifiableSet(mListeners)


    fun registerListener(listener: LISTENER_CLASS) {
        mListeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER_CLASS) {
        mListeners.remove(listener)
    }

}
