package k.example.contactlist.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insert_data(Contact_model db);

    @Update
    void Update_data(Contact_model db);

    @Delete
    void delete_data(Contact_model db);

    @Query("select * from Contact_model order by name")
    LiveData<List<Contact_model>> getAllData();

    @Query("Delete from Contact_model")
    void deleteAll();
}
