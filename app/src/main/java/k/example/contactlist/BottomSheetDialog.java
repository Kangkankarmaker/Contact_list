package k.example.contactlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    TextView txtname,txtphone,txtemail;
    ImageView phn_call,phn_msg;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.contact_details_bottom, container, false);

        txtname=v.findViewById(R.id.txt_user_name);
        txtphone=v.findViewById(R.id.txt_user_phone);
        txtemail=v.findViewById(R.id.txt_user_email);

        phn_call=v.findViewById(R.id.img_phn_call);
        phn_msg=v.findViewById(R.id.img_phn_msg);

        Bundle mArgs = getArguments();
        String name = mArgs.getString("name");
        String phone = mArgs.getString("phone");
        String email = mArgs.getString("email");
         try {
             txtname.setText(name);
             txtphone.setText(phone);
             txtemail.setText(email);
         }catch (Exception e){
             Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
         }

        phn_call.setOnClickListener(v12 -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+phone));
            startActivity(i);
        });

         phn_msg.setOnClickListener(v1 -> {
             Intent smsIntent=new Intent(Intent.ACTION_VIEW);

             smsIntent.setData(Uri.parse("smsto:"));
             smsIntent.setType("vnd.android-dir/mms-sms");
             smsIntent.putExtra("address" , new String (phone));

             try {
                 startActivity(smsIntent);
             }catch (android.content.ActivityNotFoundException ex) {
                 Toast.makeText(getContext(),  "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
             }

         });


        return v;
    }

}

