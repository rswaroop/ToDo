package swaroop.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import swaroop.todo.db.ToDoData;
import swaroop.todo.db.dao.ToDoDataDao;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 8501;
    private ArrayList<String> mItems;
    private ArrayAdapter<String> mItemsAdapter;
    private ListView mListView;

    private ToDoDataDao mDataObjects;

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        mItemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataObjects = new ToDoDataDao(getApplicationContext());
        readItems();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.lvItems);
        mItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItems);
        mListView.setAdapter(mItemsAdapter);
        setupListViewListener();
        setupEditListener();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String item = data.getStringExtra("item");
            int itemPos = data.getIntExtra("itemPos", 0);
            mItems.set(itemPos, item);
            mItemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void setupListViewListener() {
        mListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        mItems.remove(pos);
                        mItemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }

    private void setupEditListener() {
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("item", mItems.get(pos));
                        intent.putExtra("itemPos", pos);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });
    }

    private void readItems() {
        mItems = new ArrayList<>();

        if (mDataObjects.listAll().size() > 0) {
            for (ToDoData data : mDataObjects.listAll()) {
                mItems.add(data.getTodoItem());
            }
        }
    }

    private void writeItems() {
        mDataObjects.deleteAll();
        for (String item : mItems) {
            ToDoData data = new ToDoData();
            data.setTodoItem(item);
            mDataObjects.insert(data);
        }
    }
}