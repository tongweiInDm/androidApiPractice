package net.yslibrary.android.sample.keyboardvisibilityevent;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

public class MainActivity extends AppCompatActivity {

    TextView mKeyboardStatus;

    EditText mTextField;

    Button mButtonUnregister;

    Unregistrar mUnregistrar;

    View mFocusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mKeyboardStatus = findViewById(R.id.keyboard_status);
        mTextField = findViewById(R.id.text_field);
        mButtonUnregister = findViewById(R.id.btn_unregister);

        mFocusView = mKeyboardStatus;
        mFocusView.setFocusableInTouchMode(true);
        mFocusView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String keyEvent = "event.getKeyCode():" + event.getKeyCode() + ",event.getCharacters():" + event.getCharacters();
                Log.d("KeyCodeTest", keyEvent);
                Toast.makeText(MainActivity.this, keyEvent, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        /*
          You can also use {@link KeyboardVisibilityEvent#setEventListener(Activity, KeyboardVisibilityEventListener)}
          if you don't want to unregister the event manually.
         */
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                updateKeyboardStatusText(isOpen);
            }
        });

        updateKeyboardStatusText(KeyboardVisibilityEvent.isKeyboardVisible(this));

        mButtonUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregister();
            }
        });

        findViewById(R.id.btn_show_keyboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mFocusView, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        findViewById(R.id.btn_hide_keyboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mFocusView.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void updateKeyboardStatusText(boolean isOpen) {
        mKeyboardStatus.setText(String.format("keyboard is %s", (isOpen ? "visible" : "hidden")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void unregister() {
        mUnregistrar.unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnregistrar.unregister();
    }
}
