package com.my.commonlibrary.adapter;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.my.commonlibrary.R;
import com.my.commonlibrary.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;
public class UploadImgAdapter extends RecyclerView.Adapter<UploadImgAdapter.SelectedPicViewHolder> {
    private int maxImgCount = 100;
    private Context mContext;
    private List<ImageItem> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private boolean isAdded;   //是否额外添加了最后一个图片
    private DeleteListener deleteListener;
    private boolean showDelete;
    public interface DeleteListener{
        void delete(int layoutPosition);
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageItem());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
        if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
        else return mData;
    }

    public UploadImgAdapter(Context mContext, List<ImageItem> data, int maxImgCount,boolean showDelete) {
        this.mContext = mContext;
        //没有最大图片数限制
//        this.maxImgCount = maxImgCount;
        this.mInflater = LayoutInflater.from(mContext);
        this.showDelete = showDelete;
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.item_image2, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_img;
        private RelativeLayout rl;
        private Button deleteBtn;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.img);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
        }

        public void bind(final int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            //item的padding5，recyclerview marginLeft，marginRight各是10
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams((SizeUtils.getScreenWidtth((Activity) mContext) - 50) / 3, (SizeUtils.getScreenWidtth((Activity) mContext) - 50) / 3);
            rl.setLayoutParams(param);
            //根据条目位置设置图片
            ImageItem item = mData.get(position);

            if (isAdded && position == getItemCount() - 1) {
                iv_img.setImageResource(R.drawable.selector_image_add);
                iv_img.setScaleType(ImageView.ScaleType.FIT_XY);
                clickPosition = -1;
//                if(showDelete){
//                    deleteBtn.setVisibility(View.VISIBLE);
//                }else{
                    deleteBtn.setVisibility(View.GONE);
//                }
            } else {
                iv_img.setImageResource(R.mipmap.default_image);
                if(!TextUtils.isEmpty(item.id)){
                    ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.getPath(), iv_img, 0, 0);
                }else{
                    ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
                }
                iv_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                clickPosition = position;
                if(showDelete){
                    deleteBtn.setVisibility(View.VISIBLE);
                }else{
                    deleteBtn.setVisibility(View.GONE);
                }
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(deleteListener != null){
                        deleteListener.delete(position);
                    }
                }
            });

        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, clickPosition);
        }
    }

    public void setDeleteListener(DeleteListener deleteListener){
        this.deleteListener = deleteListener;
    }
}