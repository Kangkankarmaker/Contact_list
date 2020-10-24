package k.example.contactlist.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {

    public  ContactDao contactDao;
    LiveData<List<Contact_model>> allData;

    Repository(Application application){
        ContactDataBase db=ContactDataBase.getDatabase(application);
        contactDao=db.ContactDao();
        allData=contactDao.getAllData();
    }

    public LiveData<List<Contact_model>> getAllData(){
        return  allData;
    }


    public void  InsertData(Contact_model db){
        new InsertTask(contactDao).execute(db);

    }

    public void DeleteData(Contact_model db){
        new DeleteTask(contactDao).execute(db);
    }

    public void UpdateData(Contact_model db){
        new UpdateTask(contactDao).execute(db);
    }

    public class  InsertTask extends AsyncTask<Contact_model,Void,Void> {
        private  ContactDao contactDao;

        public InsertTask(ContactDao Dao) {

            this.contactDao = Dao;
        }

        @Override
        protected Void doInBackground(Contact_model... perams) {

            contactDao.insert_data(perams[0]);

            return null;
        }
    }

    public class  DeleteTask extends AsyncTask<Contact_model,Void,Void>{
        private  ContactDao contactDao;

        public DeleteTask(ContactDao Dao) {

            this.contactDao = Dao;
        }

        @Override
        protected Void doInBackground(Contact_model... perams) {

            contactDao.delete_data(perams[0]);

            return null;
        }
    }

    public class  UpdateTask extends AsyncTask<Contact_model,Void,Void>{
        private  ContactDao contactDao;

        public UpdateTask(ContactDao Dao) {
            this.contactDao = Dao;
        }

        @Override
        protected Void doInBackground(Contact_model... perams) {

            contactDao.Update_data(perams[0]);

            return null;
        }
    }

}
