package com.example.jonmid.tareasasincronas.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonmid.tareasasincronas.Models.Post;
import com.example.jonmid.tareasasincronas.R;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    List<Post> postList = new ArrayList<>();
    Context context;

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    // ******************************************************************************

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Obtener la vista (item.xml)
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);

        // Pasar la vista (item.xml) al ViewHolder
        ViewHolder viewHolder = new ViewHolder(item);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Asignar los valores a la vista
        holder.textViewId.setText(String.valueOf(postList.get(position).getId()));
        holder.textViewTitle.setText(postList.get(position).getTitle());
        holder.textViewBody.setText(postList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    // ******************************************************************************

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewId;
        TextView textViewTitle;
        TextView textViewBody;

        public ViewHolder(View item) {
            super(item);

            textViewId = (TextView) item.findViewById(R.id.id_txv_id);
            textViewTitle = (TextView) item.findViewById(R.id.id_txv_title);
            textViewBody = (TextView) item.findViewById(R.id.id_txv_body);
        }
    }
}
