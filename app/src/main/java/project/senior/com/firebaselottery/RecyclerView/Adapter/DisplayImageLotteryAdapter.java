package project.senior.com.firebaselottery.RecyclerView.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.senior.com.firebaselottery.Models.ImageModel;
import project.senior.com.firebaselottery.R;

public class DisplayImageLotteryAdapter extends RecyclerView.Adapter<DisplayImageLotteryAdapter.MyViewHolder> {
    Context mContext;
    List<ImageModel> mModels;

    public DisplayImageLotteryAdapter(Context context, List<ImageModel> models){
        mContext = context;
        mModels = models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_display_image, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display_image, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageModel current = mModels.get(position);
        Picasso.with(mContext)
                .load(current.getImage_url())
                        .fit()
                        .centerCrop()
                        .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView){
            super (itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
