package com.foxconn.paperless.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.widget.ImageView;
/**
 * 圓形的ImageView
 *@ClassName CircleImageView
 *@Author wunian
 *@Date 2017/10/20 上午10:30:28
 */
public class CircleImageView extends ImageView {
	
	private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;// 均衡的缩放图像（保持图像原始比例）使图片的两个坐标（宽、高）都大于等于
	// 相应的视图坐标（负的内边距）。
	private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;// 位图位数越高代表其可以存储的颜色信息越多，当然图像也就越逼真
	private static final int COLORDRAWABLE_DIMENSION = 2;// ColorDrawable_dimension

	// 圆形边框的厚度默认值2。
	// 如果是0，则没有天蓝色渐变的边框。
	private static final int DEFAULT_BORDER_WIDTH = 0;// default_border_width

	private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
	/**
	 * 构造一个矩形
	 */
	private final RectF mDrawableRect = new RectF();// 构造一个矩形
	private final RectF mBorderRect = new RectF();

	private final Matrix mShaderMatrix = new Matrix();// Android的2D变形（包括缩放，扭曲，平移，旋转等）可以通过Matrix来实现
	/**
	 * 设置一个绘图的Paint
	 */
	private final Paint mBitmapPaint = new Paint();// 设置一个绘图的Paint
	private final Paint mBorderPaint = new Paint();
	/**
	 * 颜色
	 */
	private int mBorderColor = DEFAULT_BORDER_COLOR;
	/**
	 * 宽度
	 */
	private int mBorderWidth = DEFAULT_BORDER_WIDTH;

	/**
	 * 绘制图形
	 */
	private Bitmap mBitmap;// 绘制图形

	/**
	 * 渲染图像。如果需要将一张图片载剪成椭圆形或圆形等形状井显示到屏幕上 ，就可以使用BitmapShader类来实现
	 */
	private BitmapShader mBitmapShader;// 渲染图像，使用图像为绘制图形着色
	private int mBitmapWidth;
	private int mBitmapHeight;

	private float mDrawableRadius;

	/**
	 * 圆角的大小
	 */
	private float mBorderRadius;// 圆角的大小
	/**
	 * boolean true or false
	 */
	private boolean mReady;
	/**
	 * boolean true or false
	 */
	private boolean mSetupPending;
	private final Paint mFlagBackgroundPaint = new Paint();// 点
	private final TextPaint mFlagTextPaint = new TextPaint();
	private String mFlagText;
	/**
	 * boolean true or false
	 */
	private boolean mShowFlag = false;
	/**
	 * 绘制矩形
	 */
	private Rect mFlagTextBounds = new Rect();// 绘制矩形

	/**
	 * 像素渲染
	 */
	Shader mSweepGradient = null;// 像素渲染

	public CircleImageView(Context context) {
		super(context);

		init();
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		super.setScaleType(SCALE_TYPE);
		mReady = true;

		if (mSetupPending) {
			setup();
			mSetupPending = false;
		}
	}

	@Override
	public ScaleType getScaleType() {
		return SCALE_TYPE;
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (scaleType != SCALE_TYPE) {
			throw new IllegalArgumentException(String.format(
					"ScaleType %s not supported.", scaleType));
		}
	}

	@Override
	public void setAdjustViewBounds(boolean adjustViewBounds) {
		if (adjustViewBounds) {
			throw new IllegalArgumentException(
					"adjustViewBounds not supported.");
		}
	}

	// 在这里我们将测试canvas提供的绘制图形方法 canvas提供的绘制图形的方法都是以draw开头
	@Override
	protected void onDraw(Canvas canvas) {
		// cancvas在bitmap上执行绘制方法
		if (getDrawable() == null) {
			return;
		}

		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius,
				mBitmapPaint);// 绘制圆形画圆圈
		if (mBorderWidth != 0) {
			canvas.save();// Canvas方便一些转换操作 还提供了保存和回滚属性的方法(save和restore)
			canvas.rotate(20, getWidth() / 2, getHeight() / 2);// 对canvas执行旋转变化
			// 旋转画纸
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius,
					mBorderPaint);// 绘制圆形画圆圈 圆形边框
			canvas.restore();// 回滚属性方法restore 以便让画纸回到初始点 画出图形
		}

		if (mShowFlag && mFlagText != null) {// 画圆弧
			canvas.drawArc(mBorderRect, 40, 100, false, mFlagBackgroundPaint); // 绘制弧
			// mBorderRect弧线所使用的矩形区域大小,
			// 40开始角度,
			// 100扫过的角度,
			// false是否使用中心,
			// mFlagBackgroundPaint
			mFlagTextPaint.getTextBounds(mFlagText, 0, mFlagText.length(),
					mFlagTextBounds);
			canvas.drawText(mFlagText, getWidth() / 2,
					(3 + FloatMath.cos((float) (Math.PI * 5 / 18)))
							* getHeight() / 4 + mFlagTextBounds.height() / 3,
					mFlagTextPaint);// 绘制字符串
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);// 圆角图片的范围
		setup();
	}

	public int getBorderColor() {
		return mBorderColor;
	}

	public void setBorderColor(int borderColor) {
		if (borderColor == mBorderColor) {
			return;
		}

		mBorderColor = borderColor;
		mBorderPaint.setColor(mBorderColor);
		invalidate();// 刷新view
	}

	public int getBorderWidth() {
		return mBorderWidth;
	}

	/**
	 * @param borderWidth
	 *            圆形的边框厚度。
	 */
	public void setBorderWidth(int borderWidth) {
		if (borderWidth == mBorderWidth) {
			return;
		}

		mBorderWidth = borderWidth;
		setup();
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		mBitmap = bm;
		setup();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		mBitmap = getBitmapFromDrawable(drawable);// 对drawable转化为的Bitmap;
		setup();
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		mBitmap = getBitmapFromDrawable(getDrawable());// 对drawable转化为的Bitmap;
		setup();
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		mBitmap = getBitmapFromDrawable(getDrawable());
		setup();
	}

	private Bitmap getBitmapFromDrawable(Drawable drawable) {// 将drawable转化为的Bitmap;
		if (drawable == null) {
			return null;
		}

		if (drawable instanceof BitmapDrawable) {// 动画
			return ((BitmapDrawable) drawable).getBitmap();
		}

		try {
			Bitmap bitmap;

			if (drawable instanceof ColorDrawable) {
				bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,// Bitmap.createBitmap(图片宽度，图片高度，配置信息)
						COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);// 根据参数创建新位图
			} else {
				bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(), BITMAP_CONFIG);
			}

			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	private void setup() {
		if (!mReady) {
			mSetupPending = true;
			return;
		}

		if (mBitmap == null) {
			return;
		}

		// 定义了平铺的3种模式：
		// static final Shader.TileMode CLAMP: 边缘拉伸.
		//
		// static final Shader.TileMode MIRROR：在水平方向和垂直方向交替景象, 两个相邻图像间没有缝隙.
		//
		// Static final Shader.TillMode REPETA：在水平方向和垂直方向重复摆放,两个相邻图像间有缝隙缝隙.

		mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,
				Shader.TileMode.CLAMP);// 将mBitmapShader作为着色器，就是在指定区域内绘制mBitmapShader

		mBitmapPaint.setAntiAlias(true);// 去锯齿
		mBitmapPaint.setShader(mBitmapShader);// 设置画笔的填充效果

		mBorderPaint.setStyle(Paint.Style.STROKE);// 设置paint的填充风格
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor);// 设置颜色
		mBorderPaint.setStrokeWidth(mBorderWidth);// 设置画笔的笔触宽度大小

		mBitmapHeight = mBitmap.getHeight();
		mBitmapWidth = mBitmap.getWidth();

		mBorderRect.set(0, 0, getWidth(), getHeight());// 4个边的坐标来表示一个矩形
		mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2,
				(mBorderRect.width() - mBorderWidth) / 2);// 拿取其中的最小值
		Log.i(">>>>heig>", mBorderRect.height() + ">>>"
				+ (mBorderRect.height() - mBorderWidth) / 2);
		Log.i(">>>>heig2>", mBorderRect.width() + ">>>"
				+ (mBorderRect.width() - mBorderWidth) / 2);
		mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width()
				- mBorderWidth, mBorderRect.height() - mBorderWidth);
		mDrawableRadius = Math.min(mDrawableRect.height() / 2,
				mDrawableRect.width() / 2);// 拿取其中的最小值

		mFlagBackgroundPaint.setColor(Color.BLACK & 0x66FFFFFF);
		mFlagBackgroundPaint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

		mFlagTextPaint.setFlags(TextPaint.ANTI_ALIAS_FLAG);
		mFlagTextPaint.setTextAlign(Align.CENTER);// 去锯齿
		mFlagTextPaint.setColor(Color.WHITE);
		mFlagTextPaint
				.setTextSize(getResources().getDisplayMetrics().density * 18);// 设置绘制文本时的字体大小
		//
		// mSweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2,
		// new int[] { Color.rgb(255, 255, 255), Color.rgb(1, 209, 255) },
		// null);//圆形边框的颜色填充 渐变

		// Parameters:
		// public SweepGradient (float cx, float cy, int[] colors, float[]
		// positions)
		// cx
		// 渲染中心点x 坐标
		// cy
		// 渲染中心y 点坐标
		// colors
		// 围绕中心渲染的颜色数组，至少要有两种颜色值
		// positions
		// 相对位置的 颜色 数组 ,可为null, 若为null,可为null, 颜色 沿渐变线 均匀分布

		mSweepGradient = new SweepGradient(getWidth() / 1, getHeight() / 5,
				new int[] { Color.YELLOW, Color.BLUE }, null);// 圆形边框的颜色填充 渐变
																// new int[] {
																// Color.WHITE
																// 起始,
																// Color.CYAN
																// 结束}

		mBorderPaint.setShader(mSweepGradient);// 设置画笔的填充颜色

		updateShaderMatrix();
		invalidate();// 刷新view
	}

	private void updateShaderMatrix() {
		float scale;
		float dx = 0;
		float dy = 0;

		mShaderMatrix.set(null);

		if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width()
				* mBitmapHeight) {
			scale = mDrawableRect.height() / (float) mBitmapHeight;
			dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
		} else {
			scale = mDrawableRect.width() / (float) mBitmapWidth;
			dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
		}

		mShaderMatrix.setScale(scale, scale);// 设置matrix进行缩放 参数为缩放比例
		mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth,
				(int) (dy + 0.5f) + mBorderWidth);// 移动
		mBitmapShader.setLocalMatrix(mShaderMatrix);// 设置变换矩阵
	}

	public void setShowFlag(boolean show) {
		mShowFlag = show;
		invalidate();// 刷新view
	}

	public void setFlagText(String text) {
		mFlagText = text;
		invalidate();// 刷新view
	}
}
