package caceresbretti.todovolumen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marcel on 19-11-2016.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>{
    ArrayList<Noticia> noticias;
    Context context;

    public NewsListAdapter(Context context) {
        this.noticias = new ArrayList<>();
        this.context = context;
    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_noticia, parent, false);
        return new NewsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {
        Noticia noticiaActual = noticias.get(position);
        holder.setNewsTitle(noticiaActual.getTitulo());
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public void addAll(@NonNull ArrayList<Noticia> noticias){
        if(noticias == null){
            throw new NullPointerException("No puede ser null");
        }
        else{
            this.noticias.addAll(noticias);
            notifyItemRangeInserted(getItemCount()-1,noticias.size());
        }
    }

    public class NewsListViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        ImageView imagen;

        public NewsListViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.txt_noticia_title);
            imagen = (ImageView) itemView.findViewById(R.id.img_noticia);
        }

        public void setNewsTitle(String title){
            titulo.setText(title);
        }
    }
}
