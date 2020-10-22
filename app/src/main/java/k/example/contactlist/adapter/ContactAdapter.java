package k.example.contactlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import k.example.contactlist.R;
import k.example.contactlist.room.Contact_model;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.viewHolder>{

    List<Contact_model> mylist;
    OnRecyclerItemClickInterface itemClickInterface;

    public ContactAdapter(List<Contact_model> mylist, OnRecyclerItemClickInterface itemClickInterface) {
        this.mylist = mylist;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public ContactAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);

        return  new viewHolder(view,itemClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Contact_model temp=mylist.get(position);
        holder.name.setText(temp.getName());

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, phone, email;
        OnRecyclerItemClickInterface onRecyclerItemClickInterface;

        public viewHolder(@NonNull View itemView,OnRecyclerItemClickInterface interFace) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(this);
            this.onRecyclerItemClickInterface=interFace;
        }

        @Override
        public void onClick(View view) {
            onRecyclerItemClickInterface.OnItemClick(getAdapterPosition());
        }
    }
    public interface  OnRecyclerItemClickInterface{
        void OnItemClick(int position);
    }
}
