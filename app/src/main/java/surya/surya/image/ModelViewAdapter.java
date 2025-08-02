package surya.surya.image;

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

// Class from the Adapter for Model
public class ModelViewAdapter extends ArrayAdapter<GetModel> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GetModel> mGridDatam = new ArrayList<GetModel>();

    public ModelViewAdapter(Context mContext, int layoutResourceId, ArrayList<GetModel> mGridDatam) {
        super(mContext, layoutResourceId, mGridDatam);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridDatam = mGridDatam;
    }

    public void setGridData(ArrayList<GetModel> mGridDatam) {
        this.mGridDatam = mGridDatam;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder1 holder;
        if (row == null) {     // Inflate the Activity
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder1();
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_titlem);
            holder.idTextView = (TextView) row.findViewById(R.id.grid_item_idm);
            holder.priceTextView = (TextView) row.findViewById(R.id.grid_item_price);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_imagem);
            row.setTag(holder);
        } else {
            holder = (ViewHolder1) row.getTag();
        }

        GetModel item = mGridDatam.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getModelNo()));
        holder.idTextView.setText(Html.fromHtml(item.getModelID()));
        holder.priceTextView.setText(Html.fromHtml(item.getPrice()));
        Picasso.get()  // /// Download the Image With Picasso
                .load(item.getModelImageURL())
                .error(R.drawable.warning_icon)
                .noFade()
                .into(holder.imageView);
        return row;
    }

    static class ViewHolder1 {
        TextView titleTextView;
        ImageView imageView;
        TextView idTextView;
        TextView priceTextView;
    }
}