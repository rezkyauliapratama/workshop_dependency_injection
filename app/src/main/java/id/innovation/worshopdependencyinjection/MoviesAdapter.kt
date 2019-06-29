package id.innovation.worshopdependencyinjection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.innovation.worshopdependencyinjection.networking.MovieDtoBean
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val mMoviesList: ArrayList<MovieDtoBean> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)

        return ViewHolder(itemView)
    }

    fun bindData(movies: List<MovieDtoBean>) {
        mMoviesList.clear()
        mMoviesList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mMoviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(mMoviesList.get(position))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindMovie(movieDtoBean: MovieDtoBean) {
            itemView.tvTitle.text = movieDtoBean.title
            itemView.tvScore.text = movieDtoBean.voteAverage.toString()
            itemView.ivPoster.loadImage(StringBuilder().append("https://image.tmdb.org/t/p/w500")
                .append(movieDtoBean.backdropPath).toString())        }
    }

}



fun ImageView.loadImage(source: String?, requestOptions: RequestOptions? = null) {
    val requestBuilder = Glide.with(this.context)
        .load(source ?: "")
    requestOptions?.let {
        requestBuilder.apply(requestOptions)
    }
    requestBuilder.into(this)
}
