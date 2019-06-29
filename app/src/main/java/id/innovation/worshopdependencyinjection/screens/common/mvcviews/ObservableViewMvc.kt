package id.innovation.worshopdependencyinjection.screens.common.mvcviews


interface ObservableViewMvc<ListenerType> : ViewMvc {

    fun registerListener(listener: ListenerType)

    fun unregisterListener(listener: ListenerType)
}
