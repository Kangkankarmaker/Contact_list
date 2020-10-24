package k.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import k.example.contactlist.adapter.ContactAdapter;
import k.example.contactlist.room.Contact_model;
import k.example.contactlist.room.ContactViewModel;

public class ContactActivity extends AppCompatActivity implements ContactAdapter.OnRecyclerItemClickInterface{

    List<Contact_model> listOfData = new ArrayList<>();
    RecyclerView recyclerView;
    ContactAdapter adapter;
    ContactViewModel contactViewModel;
    ContactAdapter.OnRecyclerItemClickInterface InterFace;
    Dialog myDialog;
    ContactViewModel myViewModel;
    FloatingActionButton fabPop;

    EditText ext_name, ext_email, ext_phone;
    Button addbutton, cancleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();
        setUpAdapter();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ContactAdapter.OnRecyclerItemClickInterface() {

            @Override
            public void OnItemClick(View view, int position) {
                Contact_model temp=listOfData.get(position);
                String email=temp.getEmail();
                String phone=temp.getPhone();
                String name=temp.getName();

                Bundle args = new Bundle();
                args.putString("name", name);
                args.putString("phone", phone);
                args.putString("email", email);
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet .setArguments(args);
                bottomSheet .show(getSupportFragmentManager(),bottomSheet.getTag());
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public void OnItemLongClick(View view, int position) {
                PopupMenu popup = new PopupMenu(getApplicationContext(),view);
                popup.inflate(R.menu.option_menu);
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menuDelete:
                            DeleteData(position);
                            break;

                        case R.id.menuUpdate:
                            Contact_model temp = listOfData.get(position);

                            String name=temp.getName();
                            String phone=temp.getPhone();
                            String email=temp.getEmail();

                            popUpInsert2(name,email,phone,position);
                            break;

                    }
                    return false;
                });
                //displaying the popup
                popup.show();
            }
        }));

    }

    private void DeleteData(int position) {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to delete the Contact?")
                .setTitle("WARNING!!!")
                .setPositiveButton("YES", (dialogInterface, i) -> {
                    //delete function called
                    Contact_model temp=listOfData.get(position);

                    myViewModel.DeleteData(temp);
                    adapter.notifyDataSetChanged();

                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                })
                .show();
    }

    private void init() {
        fabPop=findViewById(R.id.fab_add_contact);
        fabPop.setOnClickListener(view -> popUpInsert());
        recyclerView = findViewById(R.id.RV_Contact);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        InterFace = this;
        myDialog = new Dialog(this);
        myViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
    }

    public void popUpInsert() {

        myDialog.setContentView(R.layout.insert_dialog);

        addbutton = myDialog.findViewById(R.id.btn_add);
        cancleButton = myDialog.findViewById(R.id.btn_Cancle);
        ext_name = myDialog.findViewById(R.id.txt_name);
        ext_phone = myDialog.findViewById(R.id.txt_phone);
        ext_email = myDialog.findViewById(R.id.txt_email);

        addbutton.setOnClickListener(v -> {
            String email = ext_email.getText().toString();
            String phone = ext_phone.getText().toString();
            String name = ext_name.getText().toString();
            insertData(name,email,phone);
        });

        cancleButton.setOnClickListener(v -> myDialog.dismiss());

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();

    }

    private void popUpInsert2(String name, String email, String phone, int position) {

        myDialog.setContentView(R.layout.insert_dialog);

        addbutton = myDialog.findViewById(R.id.btn_add);
        cancleButton = myDialog.findViewById(R.id.btn_Cancle);
        ext_name = myDialog.findViewById(R.id.txt_name);
        ext_phone = myDialog.findViewById(R.id.txt_phone);
        ext_email = myDialog.findViewById(R.id.txt_email);


        ext_name.setText(name);
        ext_phone.setText(phone);
        ext_email.setText(email);
        addbutton.setOnClickListener(v -> {

            String updateName=ext_name.getText().toString();
            String updatePhone=ext_phone.getText().toString();
            String updateMail=ext_email.getText().toString();

            if (updateName.isEmpty()) {
                ext_name.setError("This part Can't be empty");
                ext_name.requestFocus();
            } else if (updatePhone.isEmpty()) {
                ext_phone.setError("This part Can't be empty");
                ext_phone.requestFocus();
            } else {

                Contact_model temp = listOfData.get(position);
                temp.setName(updateName);
                temp.setPhone(updatePhone);
                temp.setEmail(updateMail);
                myViewModel.UpdateData(temp);
                myDialog.dismiss();
            }

        });

        cancleButton.setOnClickListener(v -> myDialog.dismiss());

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();
    }


    private void insertData(String name, String email, String phone) {
            if (name.isEmpty()) {
                ext_name.setError("This part Can't be empty");
                ext_name.requestFocus();
            } else if (phone.isEmpty()) {
                ext_phone.setError("This part Can't be empty");
                ext_phone.requestFocus();
            } else {

                Contact_model temp = new Contact_model(name, phone, email);
                myViewModel.InsertData(temp);
                myDialog.dismiss();
            }
    }

    private void setUpAdapter() {
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        contactViewModel.GetAllData().observe(this, contact -> {

            listOfData = contact;

            adapter = new ContactAdapter(listOfData, InterFace);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void OnItemClick(View view, int position) {

    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ContactAdapter.OnRecyclerItemClickInterface clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ContactAdapter.OnRecyclerItemClickInterface clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.OnItemLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.OnItemClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}