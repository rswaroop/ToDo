package swaroop.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private EditText mEditText;
    private int mItemPos;

    public void onSubmit(View v) {
        Intent item = new Intent();
        item.putExtra("item", mEditText.getText().toString());
        item.putExtra("itemPos", mItemPos);
        setResult(RESULT_OK, item);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditText = (EditText) findViewById(R.id.editText);
        String item = getIntent().getStringExtra("item");
        mEditText.setText(item);
        mItemPos = getIntent().getIntExtra("itemPos", 0);
    }
}
