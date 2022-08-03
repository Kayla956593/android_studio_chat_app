package com.koddev.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements Filterable {




    public interface OnItemClickHandler {
        void onItemClick(View v, writing pos);
    }

    private List<writing> list;//資料來源
    private List<writing> replace_list;
    private Context context;//上下文
    private OnItemClickHandler mClickHandler;

    public MyRecyclerViewAdapter(List<writing> list, OnItemClickHandler clickHandler) {
        mClickHandler=clickHandler;
        this.list = list;
        replace_list=new ArrayList<>(this.list);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_forum,parent,false);
        return new MyViewHolder(view);
    }

    //繫結
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        writing data = list.get(position);
        holder.writingType.setText(data.getWritingType());
        holder.userName.setText(data.getUserNmae());
        holder.title.setText(data.getWritingTitle());
        holder.context.setText(data.getWritingContext());
        //holder.ivIcon.setBackgroundResource(data.getIcon());

        //holder.itemView.setTag(position);
        //holder.btnAgree.setTag(position);
    }

    //有多少個item？
    @Override
    public int getItemCount() {
        return list.size();
    }



    //建立MyViewHolder繼承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivIcon;
        private TextView title,context, userName, writingType;

        public MyViewHolder(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.textTitle);
            context=itemView.findViewById(R.id.textContext);
            userName=itemView.findViewById(R.id.userName);
            writingType=itemView.findViewById(R.id.userType);
            //ivIcon = itemView.findViewById(R.id.iv_icon);

            // 為ItemView新增點選事件
            //itemView.setOnClickListener(MyRecyclerViewAdapter.this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v ) {
                    //Toast.makeText(v.getContext(),"i am clicked by u. " ,Toast.LENGTH_SHORT).show();

                    mClickHandler.onItemClick(v, list.get(getAdapterPosition()));
                }
            });
        }

    }


    @Override
    public Filter getFilter() {
        //Toast.makeText(context,  "123", Toast.LENGTH_LONG).show();

        final List<writing> filteredList = new ArrayList<>();
        Filter writingFilter = new Filter() {


            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                Toast.makeText(context,  "123", Toast.LENGTH_LONG).show();
                //List<writing> filteredList = new ArrayList<>();
                filteredList.clear();



                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(replace_list);
                    Toast.makeText(context.getApplicationContext(),  "456", Toast.LENGTH_LONG).show();
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (writing item : replace_list) {
                        Toast.makeText(context.getApplicationContext(),  "789", Toast.LENGTH_LONG).show();
                        if (item.getWritingTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //Toast.makeText(context.getApplicationContext(),  "456", Toast.LENGTH_LONG).show();
                list.clear();
                if ((List) filterResults.values != null) {
                    list.addAll((List) filterResults.values);
                }
                notifyDataSetChanged();
            }
        };
        return writingFilter;

    }

    /*

    @Override
    public Filter getFilter() {
        //Toast.makeText(context,  "123", Toast.LENGTH_LONG).show();
        return writingFilter;
    }

    private Filter writingFilter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<writing> filteredList = new ArrayList<>();

            //Toast.makeText(context,  "123", Toast.LENGTH_LONG).show();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(replace_list);
                //Toast.makeText(context.getApplicationContext(),  "123", Toast.LENGTH_LONG).show();
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (writing item : replace_list){
                    //Toast.makeText(context.getApplicationContext(),  "123", Toast.LENGTH_LONG).show();
                    if(item.getWritingTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }



        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            if((List)filterResults.values!=null){
                list.addAll((List)filterResults.values);
            }
            notifyDataSetChanged();
        }
    };*/

}
