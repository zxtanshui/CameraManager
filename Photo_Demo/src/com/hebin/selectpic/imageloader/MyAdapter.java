package com.hebin.selectpic.imageloader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.hebin.picturetest.R;
import com.hebin.selectpic.utils.CommonAdapter;
import com.hebin.selectpic.utils.ViewHolder;


public class MyAdapter extends CommonAdapter<String> implements ListAdapter {
	private OnGetCountPicListener mOnGetCountPicListener;
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public LinkedList<String> mSelectedImage = new LinkedList<String>();

	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
	}

	@Override
	public void convert(final ViewHolder helper, final String item) {
		// 设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		// 设置no_selected
		helper.setImageResource(R.id.id_item_select,
				R.drawable.picture_unselected);
		// 设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);

		mImageView.setColorFilter(null);
		// 设置ImageView的点击事件
		mImageView.setOnClickListener(new OnClickListener() {
			// 选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v) {

				// 已经选择过该图片
				if (mSelectedImage.contains(mDirPath + "/" + item)) {
					mSelectedImage.remove(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.picture_unselected);
					mImageView.setColorFilter(null);
				} else
				// 未选择该图片
				{
					if (mSelectedImage.size() >= 9) {
						Toast.makeText(mContext, "最多只能选择9张图片", Toast.LENGTH_SHORT).show();
						return;
					}
					mSelectedImage.add(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.pictures_selected);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
				}
				if (mOnGetCountPicListener != null) {
					mOnGetCountPicListener.onGetCountPic(mSelectedImage.size());
				}
			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item)) {
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}

	public interface OnGetCountPicListener {
		void onGetCountPic(int count);
	}

	public void setOnGetCountPicListener(
			OnGetCountPicListener onGetCountPicListener) {
		mOnGetCountPicListener = onGetCountPicListener;
	}

	public ArrayList<String> getmSelectedImage() {
		ArrayList<String> mSelectedImage_new = new ArrayList<String>();
		if (mSelectedImage != null && mSelectedImage.size() > 0) {
			for (int i = 0; i < mSelectedImage.size(); i++) {
				mSelectedImage_new.add(mSelectedImage.get(i));
			}
		}

		return mSelectedImage_new;
	}
}
