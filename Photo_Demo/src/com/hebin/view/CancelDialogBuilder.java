package com.hebin.view;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hebin.picturetest.R;

public class CancelDialogBuilder extends Dialog implements DialogInterface {
	private LinearLayout mLinearLayoutView;
	private RelativeLayout mRelativeLayoutView;
	private LinearLayout mLinearLayoutTopView;
	private View mDialogView;
	private int mDuration = -1;
	private static int mOrientation = 1;
	private boolean isCancelable = true;
	private volatile static CancelDialogBuilder instance;
	private TextView dialog_title;



    private TextView tv_dialog_title;
    private Button btn_cancel;
    private Button btn_ok;
    private Button btn_resume;
    private EditText et_modify_location;
    private LinearLayout ll_linearLayout_all;

	public CancelDialogBuilder(Context context) {
		super(context);
		init(context);

	}

	public CancelDialogBuilder(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = ViewGroup.LayoutParams.MATCH_PARENT;
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(
				(WindowManager.LayoutParams) params);

	}

	public static CancelDialogBuilder getInstance(Context context) {

		int ort = context.getResources().getConfiguration().orientation;
		if (mOrientation != ort) {
			mOrientation = ort;
			instance = null;
		}

		instance = new CancelDialogBuilder(context,
				R.style.dialog_untran);
		return instance;

	}

	private void init(Context context) {

		mDialogView = View
				.inflate(context, R.layout.dialog_modify_location, null);

//		mLinearLayoutView = (LinearLayout) mDialogView
//				.findViewById(R.id.cancle_parentPanel);
//		mRelativeLayoutView = (RelativeLayout) mDialogView
//				.findViewById(R.id.cancle_main);
//		mLinearLayoutTopView = (LinearLayout) mDialogView
//				.findViewById(R.id.cancle_topPanel);

        tv_dialog_title=(TextView)mDialogView.findViewById(R.id.tv_dialog_title);
        btn_cancel=(Button)mDialogView.findViewById(R.id.btn_cancel);
        btn_ok=(Button)mDialogView.findViewById(R.id.btn_ok);
        btn_resume=(Button)mDialogView.findViewById(R.id.btn_resume);
        et_modify_location=(EditText)mDialogView.findViewById(R.id.et_modify_location);
        ll_linearLayout_all=(LinearLayout)mDialogView.findViewById(R.id.ll_linearLayout_all);

		setContentView(mDialogView);

		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {

                ll_linearLayout_all.setVisibility(View.VISIBLE);
			}
		});
//        ll_linearLayout_all.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (isCancelable)
//					dismiss();
//			}
//		});
	}

	public void setTitleText(String text) {
		// TODO Auto-generated method stub
//		dialog_title = (TextView) mDialogView.findViewById(R.id.dialog_title);
//		dialog_title.setText(text);
        tv_dialog_title.setText(text);
	}
//	public void setDetermineText(String text) {
//		// TODO Auto-generated method stub
//		 ((TextView) mDialogView.findViewById(R.id.cancle_determine)).setText(text);
//	}

	public CancelDialogBuilder setCancelClick(View.OnClickListener click) {

//		mDialogView.findViewById(R.id.cancle_btncancle).setOnClickListener(
//				click);
        btn_cancel.setOnClickListener(click);
		return this;
	}

	public CancelDialogBuilder setOkClick(View.OnClickListener click) {
//		mDialogView.findViewById(R.id.cancle_determine).setOnClickListener(
//				click);
        btn_ok.setOnClickListener(click);
		return this;
	}
    public CancelDialogBuilder setResumeClick(View.OnClickListener click) {
//		mDialogView.findViewById(R.id.cancle_determine).setOnClickListener(
//				click);
        btn_resume.setOnClickListener(click);
        return this;
    }


	public CancelDialogBuilder isCancelableOnTouchOutside(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCanceledOnTouchOutside(cancelable);
		return this;
	}

	public CancelDialogBuilder isCancelable(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCancelable(cancelable);
		return this;
	}

}
