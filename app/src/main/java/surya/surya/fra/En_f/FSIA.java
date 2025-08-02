package surya.surya.fra.En_f;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import surya.surya.surya.surya.R;

public class FSIA extends Activity {
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("ImageURL");
        setTitle(bundle.getString("Title"));
        Picasso.get().load(image).error(R.drawable.warning_icon).noFade().into((ImageView) findViewById(R.id.fullimage));
    }
}
