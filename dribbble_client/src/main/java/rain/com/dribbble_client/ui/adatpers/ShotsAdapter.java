package rain.com.dribbble_client.ui.adatpers;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import rain.com.dribbble_client.R;
import rain.com.dribbble_client.mvp.model.Shots;

/**
 * Created by HwanJ.Choi on 2017-5-17.
 */

public class ShotsAdapter extends BaseAdatper<Shots> {

    public ShotsAdapter(Context c) {
        super(c);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_shots;
    }

    @Override
    public void convert(VH viewHolder, Shots shot, int posistion) {
        viewHolder.setText(R.id.tv_item_shots_title, shot.getTitle());
        viewHolder.setText(R.id.tv_item_shots_description, Html.fromHtml(shot.getDescription() == null ? "" : shot.getDescription()));
        Glide.with(mContext).load(shot.getImages().getNormal()).fitCenter().placeholder(R.color.color_palce_holder)
                .into((ImageView) viewHolder.getView(R.id.iv_item_shots_image));
    }
}
