package k.example.contactlist.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insert_data(Contact_model db);

    @Query("select * from Contact_model order by name")
    LiveData<List<Contact_model>> getAllData();


}
