package suvarnabhalekar.test;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by user on 3/8/16.
 */
public class HorizontalListAdapter extends BaseAdapter{

    private LayoutInflater inflator;

    /** A list containing some sample data to show. */
    private List<Integer> images;
    private List<String> title;

    public HorizontalListAdapter(LayoutInflater inflator , List<Integer> images, List<String> title) {
        super();
        this.inflator = inflator;

        this.images=images;
        this.title=title;


    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {



        // We only create the view if its needed
        view=null;

        if (view == null) {
            view = inflator.inflate(R.layout.horizontal_list_item, null);
        }



        ImageView image=(ImageView)view.findViewById(R.id.image);
        TextView title1=(TextView)view.findViewById(R.id.textView5);

        title1.setText(title.get(position));

        image.setImageBitmap(
                decodeSampledBitmapFromResource(inflator.getContext().getResources(), images.get(position), 120, 150));

        return view;

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
