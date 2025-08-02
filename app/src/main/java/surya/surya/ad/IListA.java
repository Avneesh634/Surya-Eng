package surya.surya.ad;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import org.apache.http.HttpStatus;
import surya.surya.surya.surya.R;

public class IListA extends ArrayAdapter<String> {
    public static EditText EditImage_Description;
    private final ArrayList<String> Image_ArrayList;
    private final Activity context;

    public IListA(Activity context2, ArrayList<String> Image_ArrayList2) {
        super(context2, R.layout.sf_call_detail, Image_ArrayList2);
        context = context2;
        Image_ArrayList = Image_ArrayList2;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(R.layout.imagelist, (ViewGroup) null, true);
        Bitmap bm = ShrinkBitmap(Image_ArrayList.get(position), HttpStatus.SC_MULTIPLE_CHOICES, HttpStatus.SC_MULTIPLE_CHOICES);
        new ByteArrayOutputStream();
        ((ImageView) rowView.findViewById(R.id.rowImageView)).setImageBitmap(bm);
        EditImage_Description = (EditText) rowView.findViewById(R.id.Image_Description);
        return rowView;
    }

    private Bitmap ShrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap decodeFile = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        int heightRatio = (int) Math.ceil((double) (((float) bmpFactoryOptions.outHeight) / ((float) height)));
        int widthRatio = (int) Math.ceil((double) (((float) bmpFactoryOptions.outWidth) / ((float) width)));
        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }
        bmpFactoryOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, bmpFactoryOptions);
    }
}
