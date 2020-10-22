package k.example.contactlist.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact_model.class},version = 2,exportSchema = false)
public abstract class ContactDataBase extends RoomDatabase {

    public abstract ContactDao ContactDao();
    private static volatile ContactDataBase INSTANCE;

    static ContactDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDataBase.class) {
                if (INSTANCE == null) {


                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDataBase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();


                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ContactDao mDao;

        PopulateDbAsync(ContactDataBase db) {
            mDao = db.ContactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteAll();
//            Semister semister = new Semister(3.25,4.00,"mysem");
//            mDao.insertSemister(semister);
            return null;
        }
    }

}

