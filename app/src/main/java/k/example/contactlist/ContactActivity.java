package k.example.contactlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import k.example.contactlist.adapter.ContactAdapter;
import k.example.contactlist.room.Contact_model;
import k.example.contactlist.room.ContactViewModel;

public class ContactActivity extends AppCompatActivity implements ContactAdapter.OnRecyclerItemClickInterface {

    List<Contact_model> listOfData=new ArrayList<>();
    RecyclerView recyclerView;
    ContactAdapter adapter;
    ContactViewModel contactViewModel;
    ContactAdapter.OnRecyclerItemClickInterface InterFace;

    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();
        setUpAdapter();

    }

    private void init() {
        recyclerView=findViewById(R.id.RV_Contact);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        InterFace=this;
        myDialog = new Dialog(this);

    }

    public void popUp(View view){
        EditText ext_name,ext_email,ext_phone;
        Button addbutton,cancleButton;
        ContactViewModel myviewmodel;
        myDialog.setContentView(R.layout.insert_dialog);

        addbutton=myDialog.findViewById(R.id.btn_add);
        cancleButton=myDialog.findViewById(R.id.btn_Cancle);
        ext_name=myDialog.findViewById(R.id.txt_name);
        ext_phone=myDialog.findViewById(R.id.txt_phone);
        ext_email=myDialog.findViewById(R.id.txt_email);

        myviewmodel= ViewModelProviders.of(this).get(ContactViewModel.class);

        addbutton.setOnClickListener(v -> {
            String email=ext_email.getText().toString();
            String phone=ext_phone.getText().toString();
            String name=ext_name.getText().toString();

            if (name.isEmpty()){
                ext_name.setError("This part Can't be empty");
                ext_name.requestFocus();
            }
            else if (phone.isEmpty()){
                ext_phone.setError("This part Can't be empty");
                ext_phone.requestFocus();
            }else {

                Contact_model temp = new Contact_model(name, phone, email);
                myviewmodel.InsertData(temp);
                myDialog.dismiss();
            }

        });
        cancleButton.setOnClickListener(v -> myDialog.dismiss());

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();

    }

    private void setUpAdapter() {
        contactViewModel= ViewModelProviders.of(this).get(ContactViewModel.class);

        contactViewModel.GetAllData().observe(this, contact -> {

            listOfData=contact;

            adapter =new ContactAdapter(listOfData,InterFace);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void OnItemClick(int position) {

        Contact_model temp=listOfData.get(position);
        String email=temp.getEmail();
        String phone=temp.getPhone();
        String name=temp.getName();

       /* BottomSheetDialog sheetDialog=new BottomSheetDialog();
        sheetDialog.show(getSupportFragmentManager(), "ModalBottomSheet");

        Bundle bundle = new Bundle();
        String S_name =name;
        String S_phone =phone;
        String S_email =email;
        bundle.putString("name", S_name );
        bundle.putString("phone", S_phone );
        bundle.putString("email", S_email);
        BottomSheetDialog fragInfo = new BottomSheetDialog();
        fragInfo.setArguments(bundle);*/

        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("phone", phone);
        args.putString("email", email);
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet .setArguments(args);
        bottomSheet .show(getSupportFragmentManager(),bottomSheet.getTag());


    }

}