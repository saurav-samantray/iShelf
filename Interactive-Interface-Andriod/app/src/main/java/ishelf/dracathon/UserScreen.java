package ishelf.dracathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;



public class UserScreen extends AppCompatActivity {



    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_screen);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        final Switch priceSwitch  = (Switch)findViewById(R.id.user_price_switch);
        final Switch expirySwitch  = (Switch)findViewById(R.id.user_expiry_switch);
        final Switch energySwitch  = (Switch)findViewById(R.id.user_energy_switch);
        final Switch carbohydrateSwitch  = (Switch)findViewById(R.id.user_carbohydrate_switch);
        final Switch sugarSwitch  = (Switch)findViewById(R.id.user_sugar_switch);
        final Switch protienSwitch  = (Switch)findViewById(R.id.user_protien_switch);
        final Switch fatSwitch  = (Switch)findViewById(R.id.user_fat_switch);
        final Switch ingredientSwitch  = (Switch)findViewById(R.id.user_ingredient_switch);

        Button saveButton  = (Button)findViewById(R.id.user_save_btn);
        final EditText allergicIngredient  = (EditText)findViewById(R.id.user_allergic_text);


        priceSwitch.setChecked(sharedpreferences.getBoolean("price_switch",true));
        expirySwitch.setChecked(sharedpreferences.getBoolean("expiry_switch",true));
        energySwitch.setChecked(sharedpreferences.getBoolean("energy_switch",true));
        carbohydrateSwitch.setChecked(sharedpreferences.getBoolean("carbohydrate_switch",true));
        sugarSwitch.setChecked(sharedpreferences.getBoolean("sugar_switch",true));
        protienSwitch.setChecked(sharedpreferences.getBoolean("protien_switch",true));
        fatSwitch.setChecked(sharedpreferences.getBoolean("fat_switch",true));
        ingredientSwitch.setChecked(sharedpreferences.getBoolean("ingredient_switch",true));

        allergicIngredient.setText(sharedpreferences.getString("allergic_ingredient",""));


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                editor = sharedpreferences.edit();

                editor.putBoolean("price_switch",priceSwitch.isChecked());
                editor.putBoolean("expiry_switch",expirySwitch.isChecked());
                editor.putBoolean("energy_switch",energySwitch.isChecked());
                editor.putBoolean("carbohydrate_switch",carbohydrateSwitch.isChecked());
                editor.putBoolean("sugar_switch",sugarSwitch.isChecked());
                editor.putBoolean("protien_switch",protienSwitch.isChecked());
                editor.putBoolean("fat_switch",fatSwitch.isChecked());
                editor.putBoolean("ingredient_switch",ingredientSwitch.isChecked());

                editor.putString("allergic_ingredient",allergicIngredient.getText().toString());

                editor.commit();


                Intent i = new Intent(UserScreen.this, HomeScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



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
                Intent i = new Intent(UserScreen.this, ScannerScreen.class);
                i.putExtra("calling_activity", "ScannerScreen");
                startActivity(i);
                return true;

            case R.id.accountinfo:
                Intent i1 = new Intent(UserScreen.this, UserScreen.class);
                i1.putExtra("calling_activity", "ScannerScreen");
                startActivity(i1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
