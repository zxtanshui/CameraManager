package com.hebin.pagebrowse;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hebin.picturetest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class PageWidgetAdapter extends BaseAdapter {

	private Context mContext;
	private int count;
	private LayoutInflater inflater;
    private String TAG="PageWidgetAdapter";

//	private Integer[] imgs = {
//            R.drawable.photo1, R.drawable.photo2, R.drawable.photo3,
//			  R.drawable.photo4, R.drawable.photo5,
//            R.drawable.photo6,R.drawable.photo7,
//            R.drawable.photo7,R.drawable.photo8,
//            R.drawable.photo9,R.drawable.photo10,
//            R.drawable.photo11,R.drawable.photo12,
//            R.drawable.photo13,R.drawable.photo14};

    private ArrayList<String> photoList=new ArrayList<String>();
	
	public PageWidgetAdapter(Context context) {
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        count = (int) Math.ceil(photoList.size()/2.0);
        //count = (int) Math.ceil(imgs.length/2.0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		//return imgs[position];
        return photoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        Log.e(TAG,"klsdfjdslfjdsfljsdfdjsl");
		ViewGroup layout;
		if(convertView == null) {
			layout = (ViewGroup) inflater.inflate(R.layout.item_layout, null);
		} else {
			layout = (ViewGroup) convertView;
		}
		setViewContent(layout, position);
		
		return layout;
	}
	
	private void setViewContent(ViewGroup group, int position) {
//		TextView text = (TextView) group.findViewById(R.id.item_layout_leftText);
//		text.setText(String.valueOf(position*2+1));
//		ImageView image = (ImageView) group.findViewById(R.id.item_layout_leftImage);
//		image.setImageResource(imgs[position*2]);
//		text = (TextView) group.findViewById(R.id.item_layout_rightText);
//		text.setText(String.valueOf(position*2+2));
//		image = (ImageView) group.findViewById(R.id.item_layout_rightImage);
//		image.setImageResource(imgs[position*2+1]);

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.drawable.ic_stub)
                // .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        Log.e(TAG,"position--->"+position);
        TextView text = (TextView) group.findViewById(R.id.item_layout_leftText);
		text.setText(String.valueOf(position*2+1));
        ImageView image = (ImageView) group.findViewById(R.id.item_layout_leftImage);
        String imagePath = photoList.get(position*2);
        String imageUrl = ImageDownloader.Scheme.FILE.wrap(imagePath);
        //Log.e(TAG,"imageUrl=--->"+imageUrl);
        ImageLoader.getInstance().displayImage(imageUrl, image, options);



        text = (TextView) group.findViewById(R.id.item_layout_rightText);
    	text.setText(String.valueOf(position*2+2));
    	ImageView image1 = (ImageView) group.findViewById(R.id.item_layout_rightImage);
		//image.setImageResource(photoList.get(position*2+1));
        String imagePath1 = photoList.get(position*2+1);
        String imageUrl1 = ImageDownloader.Scheme.FILE.wrap(imagePath1);
        ImageLoader.getInstance().displayImage(imageUrl1, image1, options);
    }

    //设置数据

    public void setData(ArrayList<String> list){
        photoList=list;
        count = (int) Math.ceil(photoList.size()/2.0);
        Log.e(TAG,"photoList-->"+photoList.toString()+"count-->"+count);
        this.notifyDataSetChanged();
    }

//    public void initPhotoes(){
//        for(int i=0;i<13;i++){
//            photoList.add();
//        }
//    }
}
