package ishelf.dracathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class PDPScreen extends AppCompatActivity {

    String productname;
    String allergyconfirmed;

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pdp_screen);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        String productnameurl;
        if (extras != null) {
            allergyconfirmed=extras.getString("allergyconfirmed");
            productnameurl = extras.getString("name");
            String productsplit[] = productnameurl.split("/");

            productname = productsplit[productsplit.length - 1];

            try {
                JSONObject jObj = new JSONObject(loadJSONFromAsset(productname));

                String name = jObj.getString("name");
                String price = jObj.getString("price");
                String expiry = jObj.getString("expiry");

                boolean hasEnrichExp= jObj.getBoolean("hasEnrichExp");

                String addedSugar = jObj.getString("Added Sugar");
                String energy = jObj.getString("energy");
                String carbohydrate = jObj.getString("carbohydrate");
                String protien = jObj.getString("Protein");
                String fat = jObj.getString("Fat");
                String shelfId = jObj.getString("shelfId");
               JSONArray jsa =  jObj.getJSONArray("Ingredients");

               StringBuffer sb = new StringBuffer();
                sb.append(jsa.get(0));

                for(int i=1;i<jsa.length();i++){
                    sb.append(",");
                    sb.append(jsa.get(i));
                }

                String ingredients = sb.toString().toLowerCase();

                String allergicIngredients = sharedpreferences.getString("allergic_ingredient","").toLowerCase();

                String[] allergicIngredient = allergicIngredients.split(",");

                //Toast.makeText(getApplicationContext(),allergicIngredient.toString() , Toast.LENGTH_SHORT).show();



       if(allergyconfirmed==null) {
           for (String s : allergicIngredient) {
               if (ingredients.contains(s) && s != "") {

                   Intent i = new Intent(PDPScreen.this, AllergyScreen.class);
                   i.putExtra("name", productnameurl);
                   i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(i);
               }
           }

       }



                TextView nameView = (TextView) findViewById(R.id.pdp_product_name);
                TextView priceView = (TextView) findViewById(R.id.pdp_product_price);
                TextView expiryView = (TextView) findViewById(R.id.pdp_product_expiry);
                TextView ingredientsView = (TextView) findViewById(R.id.pdp_product_ingredient);

                TextView energyView = (TextView) findViewById(R.id.pdp_product_energy);
                TextView carbohydrateView = (TextView) findViewById(R.id.pdp_product_carbohydrate);
                TextView protienView = (TextView) findViewById(R.id.pdp_product_protien);
                TextView fatView = (TextView) findViewById(R.id.pdp_product_fat);
                TextView sugarView = (TextView) findViewById(R.id.pdp_product_sugar);
                TextView shelfIdView = (TextView) findViewById(R.id.pdp_shelf_id);
                ImageView img = (ImageView)findViewById(R.id.pdp_product_image);

                shelfIdView.setText(shelfId);
                nameView.setText(name);
                priceView.setText(price);
                expiryView.setText(expiry);

                sugarView.setText(addedSugar);
                energyView.setText(energy);
                carbohydrateView.setText(carbohydrate);
                protienView.setText(protien);
                fatView.setText(fat);

                ingredientsView.setText(ingredients);

                int drawableId = getResources().getIdentifier("o"+productname, "drawable", getPackageName());
                img.setImageResource(drawableId);


             //----------------------------

                TextView priceViewTag = (TextView) findViewById(R.id.pdp_product_price_tag);

                TextView expiryViewTag = (TextView) findViewById(R.id.pdp_product_expiry_tag);
                TextView ingredientsViewTag = (TextView) findViewById(R.id.pdp_product_ingredient_tag);

                TextView energyViewTag = (TextView) findViewById(R.id.pdp_product_energy_tag);
                TextView carbohydrateViewTag = (TextView) findViewById(R.id.pdp_product_carbohydrate_tag);
                TextView protienViewTag = (TextView) findViewById(R.id.pdp_product_protien_tag);
                TextView fatViewTag = (TextView) findViewById(R.id.pdp_product_fat_tag);
                TextView sugarViewTag = (TextView) findViewById(R.id.pdp_product_sugar_tag);


                // New Changes Start

                final JSONArray jsa1 =  jObj.getJSONArray("recommended products");

                for(int i=0;i<jsa1.length();i++){
                     int j = i+1;
                    final int k = i;
                    JSONObject jObj1 = new JSONObject(loadJSONFromAsset((String) jsa1.get(i)));
                    String name1 = jObj1.getString("name");
                    String id = jObj1.getString("id");
                    int drawableId1 = getResources().getIdentifier("o"+id, "drawable", getPackageName());
                    ImageView img1 = null;
                    TextView nameView1 = null;
                    if(i==0) {
                         img1 = (ImageView)findViewById(R.id.pdp_product_image1);
                        nameView1 = (TextView) findViewById(R.id.pdp_product_name1);
                    } else {
                         img1 = (ImageView)findViewById(R.id.pdp_product_image2);
                        nameView1 = (TextView) findViewById(R.id.pdp_product_name2);
                    }

                    img1.setImageResource(drawableId1);
                    nameView1.setText(name1);

                    img1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        Intent intent = new Intent(PDPScreen.this, PDPScreen.class);
                                        intent.putExtra("name", ""+jsa1.get(k));
                                        startActivity(intent);
                                    } catch(Exception e) {

                                    }

                                }
                            });
                        }
                    });
                }

                //End
                if(!sharedpreferences.getBoolean("price_switch",true)){
                    priceViewTag.setVisibility(View.INVISIBLE);
                    priceView.setVisibility(View.INVISIBLE);
                }
                if(!sharedpreferences.getBoolean("expiry_switch",true)){
                            expiryViewTag.setVisibility(View.INVISIBLE);
                            expiryView.setVisibility(View.INVISIBLE);
                }
                if(!sharedpreferences.getBoolean("energy_switch",true)){

                    energyViewTag.setVisibility(View.INVISIBLE);
                            energyView.setVisibility(View.INVISIBLE);

                }
                if(!sharedpreferences.getBoolean("carbohydrate_switch",true)){

                    carbohydrateViewTag.setVisibility(View.INVISIBLE);
                            carbohydrateView.setVisibility(View.INVISIBLE);

                }
                if(!sharedpreferences.getBoolean("sugar_switch",true)){

                    sugarViewTag.setVisibility(View.INVISIBLE);
                            sugarView.setVisibility(View.INVISIBLE);

                }
                if(!sharedpreferences.getBoolean("protien_switch",true)){

                    protienViewTag.setVisibility(View.INVISIBLE);
                            protienView.setVisibility(View.INVISIBLE);

                }
                if(!sharedpreferences.getBoolean("fat_switch",true)){

                    fatViewTag.setVisibility(View.INVISIBLE);
                            fatView.setVisibility(View.INVISIBLE);

                }
                if(!sharedpreferences.getBoolean("ingredient_switch",true)){

                    ingredientsViewTag.setVisibility(View.INVISIBLE);
                            ingredientsView.setVisibility(View.INVISIBLE);
                }

            }catch(Exception e){

                Toast.makeText(getApplicationContext(), "Barcode: " + e, Toast.LENGTH_SHORT).show();
            }


        }


    }

    public String loadJSONFromAsset(String product) {
        String json = null;
        try {

            InputStream is = getAssets().open(product + ".txt");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(productname.equals("105")) {
            inflater.inflate(R.menu.pdp_screen_options, menu);
        }else{
            inflater.inflate(R.menu.main_screen_options, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scanner:
                Intent i = new Intent(PDPScreen.this, ScannerScreen.class);
                i.putExtra("calling_activity", "ScannerScreen");
                startActivity(i);
                return true;

            case R.id.accountinfo:
                Intent i1 = new Intent(PDPScreen.this, UserScreen.class);
                i1.putExtra("calling_activity", "ScannerScreen");
                startActivity(i1);
                return true;
            case R.id.progressBar:

                if(productname.equals("105")){
                    Intent i2 = new Intent(PDPScreen.this, ProgressBarScreen.class);
                    i2.putExtra("calling_activity", "ScannerScreen");
                    startActivity(i2);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
