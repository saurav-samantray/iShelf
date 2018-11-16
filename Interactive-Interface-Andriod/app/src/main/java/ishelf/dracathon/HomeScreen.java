package ishelf.dracathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class HomeScreen  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scanner:
                Intent i = new Intent(HomeScreen.this, ScannerScreen.class);
                i.putExtra("calling_activity", "ScannerScreen");
                startActivity(i);
                return true;

            case R.id.accountinfo:
                Intent i1 = new Intent(HomeScreen.this, UserScreen.class);
                i1.putExtra("calling_activity", "ScannerScreen");
                startActivity(i1);
                return true;
            case R.id.progressBar:
                Intent i2 = new Intent(HomeScreen.this, ProgressBarScreen.class);
                i2.putExtra("calling_activity", "ScannerScreen");
                startActivity(i2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
