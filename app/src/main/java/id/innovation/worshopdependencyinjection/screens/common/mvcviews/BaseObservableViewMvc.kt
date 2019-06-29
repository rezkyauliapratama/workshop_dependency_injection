package id.innovation.worshopdependencyinjection.screens.common.mvcviews

import java.util.*

abstract class BaseObservableViewMvc<ListenerType> : BaseViewMvc(),
    ObservableViewMvc<ListenerType> {

    private val mListeners = HashSet<ListenerType>()

    protected val listeners: Set<ListenerType>
        get() = Collections.unmodifiableSet(mListeners)


    override fun registerListener(listener: ListenerType) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        mListeners.remove(listener)
    }
}
