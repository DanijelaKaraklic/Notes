package rs.aleph.android.example21.activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import rs.aleph.android.example21.R;
import rs.aleph.android.example21.db.DatabaseHelper;
import rs.aleph.android.example21.dialogs.AboutDialog;

public class MainActivity extends AppCompatActivity{



    private AlertDialog dialog;
    //za rad sa bazom
    private DatabaseHelper databaseHelper;





    private int productId = 0;
    private static int NOTIFICATION_ID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        //showing notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_stat_buy);
        builder.setSmallIcon(R.drawable.ic_stat_buy);
        builder.setContentTitle("Title");
        builder.setContentText("Content title");
        builder.setLargeIcon(bitmap);

        // Shows notification with the notification manager (notification ID is used to update the notification later on)
        //umesto this aktivnost
        NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());



        //showing AboutDialog
        if (dialog == null){
            dialog = new AboutDialog(MainActivity.this).prepareDialog();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        dialog.show();

        //Pristupanje deljenim podesavanjima,primaju samo primitivne tipove
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //default vrednost iz liste "1"
        String s = sharedPreferences.getString("@string/pref_sync","1");
        boolean b = sharedPreferences.getBoolean("@string/pref_sync",false);




        //samples of views
      /*  EditText name = (EditText) findViewById(R.id.name);
        name.setText(product.getmName());

        EditText description = (EditText) findViewById(R.id.description);
        description.setText(product.getDescription());

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        ratingBar.setRating(product.getRating());

        ImageView imageView = (ImageView) findViewById(R.id.image);
        InputStream is = null;
        try {
            is = getAssets().open(product.getImage());
            Drawable drawable = Drawable.createFromStream(is, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        //Showing a dialog

        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.setContentView(R.layout.dialog_layout);

        dialog.setTitle("Dialog");

        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final EditText editName = (EditText) dialog.findViewById(R.id.product_name);
                final EditText editDescription = (EditText) dialog.findViewById(R.id.product_description);
                final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rb_product);
                Button btnImage = (Button) dialog.findViewById(R.id.btn_browse_image);
                btnImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                        /*final Spinner image = (Spinner) dialog.findViewById(R.id.sp_dialog_images);

                        List<String> images  = new ArrayList<String>();
                        images.add("apples.jpg");
                        images.add("bananas.jpg");
                        images.add("oranges.jpg");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, images);
                        image.setAdapter(adapter);
                        image.setSelection(1);*/

                Spinner category = (Spinner)dialog.findViewById(R.id.sp_dialog_categories);


                //ArrayAdapter<Category> adapter1 = new ArrayAdapter<Category>(MainActivity.this,android.R.layout.simple_spinner_item,categories);
                //category.setAdapter(adapter1);
                //category.setSelection(0);


                dialog.dismiss();


            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Toast.makeText(MainActivity.this, R.string.dialog_message_no,Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();




        // Enable ActionBar app icon to behave as action to toggle nav drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }



        if (savedInstanceState == null) {

        }




    }


   /* private void addItem(String name, String description, float rating, Category category, String image){

        Product product = new Product();
        product.setmName(name);
        product.setDescription(description);
        product.setRating(rating);
        product.setImage(image);
        product.setCategory(category);

        //pozovemo metodu create da bi upisali u bazu
        try {
            getDatabaseHelper().getProductDao().create(product);
            getDatabaseHelper().getProductDao().update(product);
            if (product != null) {
                getDatabaseHelper().getProductDao().delete(product);
            }
            getDatabaseHelper().getProductDao().queryBuilder().
                    where().
                    eq(Product.FIELD_NAME_NAME,"nesto").
                    query();

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
            try {
                Date date = sdf.parse("12.02.2014.");
                String dateString = sdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }



            refresh();

            Toast.makeText(this, "Product inserted", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.products);

        if (listview != null){
            ArrayAdapter<Product> adapter = (ArrayAdapter<Product>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Product> list = getDatabaseHelper().getProductDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_master, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     * Metoda koja je izmenjena da reflektuje rad sa Asinhronim zadacima
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(MainActivity.this, "Sinhronizacija pokrenuta u pozadini niti. dobro :)",Toast.LENGTH_SHORT).show();

                break;
            case R.id.action_add:

                    Toast.makeText(MainActivity.this, "Sinhronizacija pokrenuta u glavnoj niti. Nije dobro :(",Toast.LENGTH_SHORT).show();


                break;
            case R.id.action_delete:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }*/









   /* @Override
    public void onBackPressed() {

        if (landscapeMode) {
            finish();
        } else if (listShown == true) {
            finish();
        } else if (detailShown == true) {

        }

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}


