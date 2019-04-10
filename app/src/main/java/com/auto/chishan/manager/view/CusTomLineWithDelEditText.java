package com.auto.chishan.manager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.auto.chishan.manager.R;



/**
 * 自定义带下滑线的EditText
 * @author jun
 */
public class CusTomLineWithDelEditText extends AppCompatEditText {
	
	
	/**下滑线的颜色**/
	private int line_corlor  ;
	
	/**下滑线的高度**/
	private int line_height = 3 ;

    /**判断有无消除按钮**/
    private boolean type;

	/**画笔**/
	private Paint mPaint ;

	private Drawable imgAble;
	private Context mContext;
	
	
	public CusTomLineWithDelEditText(Context context){
		//调用两个参数的构造方法
		this(context,null) ;
	}
	/**
	 * 加载xml的时候调用这个构造方法
	 * @param context
	 * @param attrs
	 */
	public CusTomLineWithDelEditText(Context context, AttributeSet attrs){
		//调用三个参数的构造方法
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public CusTomLineWithDelEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setBackground(null);
		initTypeArray(context, attrs, defStyle);
		initPaint();
		init();
	}
	/**初始化Paint***/
	private void initPaint() {
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(line_height) ;
		mPaint.setColor(line_corlor);
	}
	/***
	 * 初始化TypedArray
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	private void initTypeArray(Context context, AttributeSet attrs, int defStyle) {
		line_corlor = ContextCompat.getColor(context,R.color.color_accent_blue);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.edittext_bg, defStyle, 0) ;
		int n = a.getIndexCount() ;
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i) ;
			switch (attr) {
			case R.styleable.edittext_bg_line_color:
				//从xml取得下滑线的颜色 默认是绿色
//					line_corlor = ContextCompat.getColor(context,R.color.color_accent_blue);
					line_corlor = a.getColor(R.styleable.edittext_bg_line_color,getResources().getColor(R.color.color_accent_blue));
				break;
			case R.styleable.edittext_bg_line_height:
				//从xml取得下滑线的高 默认是1dip   dip to px
				line_height = a.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())) ;
				break ;
            case R.styleable.edittext_bg_line_type:
                type   = a.getBoolean(attr,false);
				break;
			}
		}
		//使用完一定要recycle 保证资源回收
		a.recycle() ;
	}

	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画底线
		canvas.drawLine(0, this.getHeight()-1, this.getWidth()-1,this.getHeight()-1, mPaint);
	}

	// 处理删除事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 50;
			if(rect.contains(eventX, eventY)) {
				setText("");
			}

		}
		return super.onTouchEvent(event);
	}

	public void setType(boolean type){
		this.type = type;
	}

	private void init() {
			imgAble = ContextCompat.getDrawable(getContext(),R.drawable.ic_search_delete);
			addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setDrawable();
	}

	//设置删除图片
	private void setDrawable() {
			if (TextUtils.isEmpty(getText())||!type) {
				setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			} else {
				setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
			}
	}


	
}
