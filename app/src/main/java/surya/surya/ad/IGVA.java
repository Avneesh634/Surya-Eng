package surya.surya.ad;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import surya.surya.surya.surya.R;


/**
 * Created by Anand Rai on 19-07-2016.
 */

// Class from the Adapter for product
public class IGVA extends ArrayAdapter<GIItem> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GIItem> mGridData = new ArrayList<GIItem>();

    public IGVA(Context mContext, int layoutResourceId, ArrayList<GIItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<GIItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;

        if (row == null) {

            // Inflate the Activity
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GIItem item = mGridData.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
        //Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        Picasso.get()    /// Download the Image With Picasso
                .load(item.getImage())
                .error(android.R.drawable.ic_dialog_alert)
                .into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}