package k.example.contactlist.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    public Repository myrepository;
    LiveData<List<Contact_model>> allData;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        myrepository=new Repository(application);
        this.allData=myrepository.getAllData();
    }

    public LiveData<List<Contact_model>>GetAllData(){
        return allData;
    }

    public void InsertData(Contact_model data){
        myrepository.InsertData(data);
    }
}
