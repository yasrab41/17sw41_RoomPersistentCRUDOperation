package com.example.roompersistancedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorEventListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.roompersistancedatabase.AppDatabase.getAppDatabase;

public class MainActivity extends AppCompatActivity {


    EditText editId,editName,editData;
    Button btnSave, btnShow;
    ListView listView;
    List<EntitySutdentRecord> studentRecordList;
    private StudentDao studentDao;
    private EntitySutdentRecord entitySutdentRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = (EditText)findViewById(R.id.edid);
        editName = (EditText)findViewById(R.id.edname);
        editData = (EditText)findViewById(R.id.eddata);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnShow = (Button)findViewById(R.id.btnShow);

        listView = (ListView)findViewById(R.id.listview);

//        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
//        listView.setAdapter(listAdapter);
//        Button addButton = (Button) findViewById(R.id.button);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                EditText myItem = (EditText)findViewById(R.id.editText);
//                String value = myItem.getText().toString();
//                listAdapter.add(value);
//                myItem.getText().clear();
//
//
//            }
//        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),((TextView)view).getText().toString(),Toast.LENGTH_LONG).show();
//            }
//        });
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                //Adapter adapter = new Adapter(this, listAdapter);
//
//
//                listAdapter.remove(String.valueOf(position));
//                listAdapter.notifyDataSetChanged();
//                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

    }
    public void SaveDatabtn(View view){
        saveData();
    }

    public void ShowDatabtn(View view){
        getData();
    }

//    public void deletebtn(View view){
//        deleteItem();
//    }

    private void saveData() {
        final String sName = editName.getText().toString().trim();
        final String sData = editData.getText().toString().trim();
    if (sName.isEmpty()) {
        editName.setError("Task required");
        editName.requestFocus();
        return;
    }
    if (sData.isEmpty()) {
        editData.setError("Desc required");
        editData.requestFocus();
        return;
    }
    class SaveTask extends AsyncTask<Void, Void, Void> {
        @Override
            protected Void doInBackground(Void... voids) {
            //creating a task
            EntitySutdentRecord task = new EntitySutdentRecord();
            task.setName(sName);
            task.setData(sData);
            //adding to database
            getAppDatabase(getApplicationContext())
                    .studentDao()
                    .insert(task);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        }
    }
    SaveTask st = new SaveTask();
    st.execute();
    }

    private void getData() {
        class GetTasks extends AsyncTask<Void, Void, List<EntitySutdentRecord>> {
            @Override
            protected List<EntitySutdentRecord> doInBackground(Void... voids) {
                studentRecordList= AppDatabase
                        .getAppDatabase(getApplicationContext())
                        .studentDao()
                        .getAll();
                return studentRecordList;

            }
            @Override
            protected void onPostExecute(List<EntitySutdentRecord> sutdentRecords) {
                super.onPostExecute(sutdentRecords);
                ArrayList<String> names = new ArrayList();
                for(EntitySutdentRecord t:sutdentRecords){
                    names.add(t.getId() +"   ||   " + t.getName() + "  ||  " + t.getData());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, names);
                listView.setAdapter(adapter);
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();

    }


    public void deletebtn(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = Integer.parseInt(editId.getText().toString());
                EntitySutdentRecord entitySutdentRecord = AppDatabase.getAppDatabase(getApplicationContext())
                        .studentDao()
                        .findStudentById(id);

//                Log.d(TAG, "run: " + todo.toString());
                if (entitySutdentRecord != null) {
                    AppDatabase.getAppDatabase(getApplicationContext())
                            .studentDao()
                            .delete(entitySutdentRecord);

//                    Log.d(TAG, "run: todo has been deleted...");
                }

            }
        }).start();
        editId.setText("");
        getData();
    }


    public void updateBtn(View view) {

        final int id = Integer.parseInt(editId.getText().toString());
        final String sName = editName.getText().toString().trim();
        final String sData = editData.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                EntitySutdentRecord entitySutdentRecord = new EntitySutdentRecord();
                entitySutdentRecord.setId(id);
                entitySutdentRecord.setName(sName);
                entitySutdentRecord.setData(sData);


                    AppDatabase.getAppDatabase(getApplicationContext())
                            .studentDao()
                            .update(entitySutdentRecord);

            }
        }).start();

    }
//
//    private void deleteItem(){
//
//        class DeleteFromDatabase extends AsyncTask<EntitySutdentRecord,Void,String>{
//
//            EntitySutdentRecord entitySutdentRecord = AppDatabase.getAppDatabase(getApplicationContext())
//                    .studentDao()
//                    .findStudentById(1);
//            @Override
//            protected String doInBackground(EntitySutdentRecord... strings) {
//
////                MainActivity.AppDatabase.studentDao().delete(entitySutdentRecord);
//                AppDatabase.getAppDatabase(getApplicationContext())
//                        .studentDao()
//                        .delete(entitySutdentRecord);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                Log.d("mPavan001", "delete done");
//                super.onPostExecute(s);
//            }
//        }
//
//        DeleteFromDatabase dt = new DeleteFromDatabase();
//        dt.execute();
//
////        class deleteWordAsyncTask extends AsyncTask<EntitySutdentRecord, Void, Void> {
////            EntitySutdentRecord entitySutdentRecord = AppDatabase.getAppDatabase(getApplicationContext())
////                    .studentDao()
////                    .findStudentById(1);//
//////            deleteWordAsyncTask(StudentDao dao) {
//////                mAsyncTaskDao = dao;
//////            }
////
////
////
////            @Override
////            protected Void doInBackground(final EntitySutdentRecord... params) {
////              AppDatabase.getAppDatabase(getApplicationContext())
////                            .studentDao()
////                            .delete(entitySutdentRecord);
////                return null;
////            }
////        }
////            deleteWordAsyncTask dt = new deleteWordAsyncTask();
////        dt.execute();
////        new deleteWordAsyncTask(studentDao).execute(entitySutdentRecord);
//
//    }
//

}
