package me.andika.lockscreen;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UnlockBar extends RelativeLayout
{
	private OnUnlockListener listenerLeft = null;
	private OnUnlockListener listenerRight = null;
	
	private TextView text_label = null;
	private ImageView img_thumb = null;
	
	private int thumbWidth = 0;
	boolean sliding = false;
	private int sliderPosition = 0;
	int initialSliderPosition = 0;
	float initialSlidingX = 0;

	public UnlockBar(Context context)
	{
		super(context);
		init(context, null);

	}

	public UnlockBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs);
	}

	public UnlockBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public void setOnUnlockListenerLeft(OnUnlockListener listener)
	{
		this.listenerLeft = listener;
	}

	public void setOnUnlockListenerRight(OnUnlockListener listener)
	{
		this.listenerRight = listener;
	}

	public void reset()
	{
		final LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
		ValueAnimator animator = ValueAnimator.ofInt(params.leftMargin, (getMeasuredWidth()-thumbWidth)/2);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
		    @Override
		    public void onAnimationUpdate(ValueAnimator valueAnimator)
		    {
		        params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
		        img_thumb.requestLayout();
		    }
		});
		animator.setDuration(300);
		animator.start();
		text_label.setAlpha(1f);
	}

	private void init(Context context, AttributeSet attrs)
	{		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.unlock_main, this, true);

		// Retrieve layout elements
		text_label = (TextView) findViewById(R.id.text_label);
		img_thumb = (ImageView) findViewById(R.id.img_thumb);



		// Get padding
		//thumbWidth = dpToPx(120); // 60dp + 2*10dp

		ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					UnlockBar.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					thumbWidth = img_thumb.getWidth()+dpToPx(20);
					sliderPosition = (UnlockBar.this.getWidth()-thumbWidth)/2;
					LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
					params.setMargins((UnlockBar.this.getWidth()-thumbWidth)/2, 0, 0, 0);
					img_thumb.setLayoutParams(params);


				}
			});
		}


	}


	@Override
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event)
	{
		super.onTouchEvent(event);


		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (event.getX() > sliderPosition && event.getX() < (sliderPosition + thumbWidth))
			{
				Log.d("event","event "+event.getX());
				sliding = true;
				initialSlidingX = event.getX();
				initialSliderPosition = sliderPosition;
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE)
		{
			if (sliderPosition >= (getMeasuredWidth() - thumbWidth))
			{
				if (listenerRight != null) listenerRight.onUnlock();
			}else if (sliderPosition <= 0)
			{
				if (listenerLeft != null) listenerLeft.onUnlock();
			}
			else
			{
				sliding = false;
				sliderPosition =  (getMeasuredWidth()-thumbWidth)/2;
				reset();
			}
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE && sliding)
		{
			sliderPosition = (int) (initialSliderPosition + (event.getX() - initialSlidingX));
			if (sliderPosition <= 0) sliderPosition = 0;
			
			if (sliderPosition >= (getMeasuredWidth() - thumbWidth))
			{
				sliderPosition = (int) (getMeasuredWidth()  - thumbWidth);
			}
			else
			{
				int max = (int) (getMeasuredWidth() - thumbWidth);
				int progress = (int) (sliderPosition * 100 / (max * 1.0f));
				text_label.setAlpha(1f - progress * 0.02f);
			}
			setMarginLeft(sliderPosition);
		}
		
		return true;
	}
	
	private void setMarginLeft(int margin)
	{
		if (img_thumb == null) return;
		LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
		params.setMargins(margin, 0, 0, 0);
		img_thumb.setLayoutParams(params);
	}
	
	private int dpToPx(int dp)
	{
		float density = getResources().getDisplayMetrics().density;
	    return Math.round((float)dp * density);
	}
	
	public static interface OnUnlockListener {
		void onUnlock();
	}
}
