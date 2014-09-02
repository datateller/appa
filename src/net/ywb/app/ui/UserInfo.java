package net.ywb.app.ui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.client.utils.URIUtils;

import net.ywb.app.AppContext;
import net.ywb.app.AppException;
import net.ywb.app.R;
import net.ywb.app.bean.APIResult;
import net.ywb.app.bean.FriendList;
import net.ywb.app.bean.MyInformation;
import net.ywb.app.bean.Result;
import net.ywb.app.common.FileUtils;
import net.ywb.app.common.ImageUtils;
import net.ywb.app.common.StringUtils;
import net.ywb.app.common.UIHelper;
import net.ywb.app.widget.LoadingDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 用户资料
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class UserInfo extends BaseActivity {

	private ImageView back;
	private ImageView refresh;
	private ImageView face;
	private ImageView gender;
	private Button editer;
	private TextView name;
	private TextView birthday;
	private TextView homeaddr;
	private TextView weight;
	private TextView height;
	private TextView favorites;
	private LinearLayout favorites_ll;
	private LoadingDialog loading;
	private MyInformation user;
	private Handler mHandler;

	private final static int CROP = 200;
	private final static String FILE_SAVEPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/OSChina/Portrait/";
	private Uri origUri;
	private Uri cropUri;
	private File protraitFile;
	private Bitmap protraitBitmap;
	private String protraitPath;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);

		// 初始化视图控件
		this.initView();
		// 初始化视图数据
		this.initData();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.user_info_back);
		refresh = (ImageView) findViewById(R.id.user_info_refresh);
		editer = (Button) findViewById(R.id.user_info_editer);
		back.setOnClickListener(UIHelper.finish(this));
		refresh.setOnClickListener(refreshClickListener);
		editer.setOnClickListener(editerClickListener);

		face = (ImageView) findViewById(R.id.user_info_userface);
		gender = (ImageView) findViewById(R.id.user_info_gender);
		name = (TextView) findViewById(R.id.user_info_username);
		birthday = (TextView) findViewById(R.id.baby_info_birthday);
		homeaddr = (TextView) findViewById(R.id.baby_info_homeaddr);
		height = (TextView) findViewById(R.id.baby_info_height);
		weight = (TextView) findViewById(R.id.baby_info_weight);
		favorites = (TextView) findViewById(R.id.user_info_favorites);
		favorites_ll = (LinearLayout) findViewById(R.id.user_info_favorites_ll);
	}

	private void initData() {
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (loading != null)
					loading.dismiss();
				if (msg.what == 1 && msg.obj != null) {
					user = (MyInformation) msg.obj;

					// 加载用户头像
					UIHelper.showUserFace(face, user.getFace());

					// 用户性别
					if (user.getGender() == 1)
						gender.setImageResource(R.drawable.widget_gender_man);
					else
						gender.setImageResource(R.drawable.widget_gender_woman);

					// 其他资料
					name.setText(user.baby.getBabyname());
					birthday.setText(user.baby.getBirthday());
					homeaddr.setText(user.baby.getHomeaddr());
					height.setText(user.baby.getHeight());
					weight.setText(user.baby.getWeight());
					favorites.setText(user.baby.getId() + "");

					favorites_ll.setOnClickListener(favoritesClickListener);

				} else if (msg.obj != null) {
					((AppException) msg.obj).makeToast(UserInfo.this);
				}
			}
		};
		this.loadUserInfoThread(false);
	}

	private void loadUserInfoThread(final boolean isRefresh) {
		loading = new LoadingDialog(this);
		loading.show();

		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					MyInformation user = ((AppContext) getApplication())
							.getMyInformation(isRefresh);
					msg.what = 1;
					msg.obj = user;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				mHandler.sendMessage(msg);
			}
		}.start();
	}

	private View.OnClickListener editerClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			CharSequence[] items = { getString(R.string.img_from_album),
					getString(R.string.img_from_camera) };
			imageChooseItem(items);
		}
	};

	private View.OnClickListener refreshClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			loadUserInfoThread(true);
		}
	};

	private View.OnClickListener favoritesClickListener = new View.OnClickListener() {
		public void onClick(View v) {
//			UIHelper.showUserFavorite(v.getContext());
		}
	};


	// 裁剪头像的绝对路径
	private Uri getUploadTempFile(Uri uri) {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.ToastMessage(UserInfo.this, "无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (StringUtils.isEmpty(thePath)) {
			thePath = ImageUtils.getAbsoluteImagePath(UserInfo.this, uri);
		}
		String ext = FileUtils.getFileFormat(thePath);
		ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
		// 照片命名
		String cropFileName = "osc_crop_" + timeStamp + "." + ext;
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);

		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}

	// 拍照保存的绝对路径
	private Uri getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.ToastMessage(UserInfo.this, "无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// 照片命名
		String cropFileName = "osc_camera_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		this.origUri = this.cropUri;
		return this.cropUri;
	}

	/**
	 * 操作选择
	 * 
	 * @param items
	 */
	public void imageChooseItem(CharSequence[] items) {
		AlertDialog imageDialog = new AlertDialog.Builder(this)
				.setTitle("上传头像").setIcon(android.R.drawable.btn_star)
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 相册选图
						if (item == 0) {
							startImagePick();
						}
						// 手机拍照
						else if (item == 1) {
							startActionCamera();
						}
					}
				}).create();

		imageDialog.show();
	}

	/**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	private void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	/**
	 * 相机拍照
	 * 
	 * @param output
	 */
	private void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	/**
	 * 拍照后裁剪
	 * 
	 * @param data
	 *            原始图片
	 * @param output
	 *            裁剪后图片
	 */
	private void startActionCrop(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", this.getUploadTempFile(data));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP);// 输出图片大小
		intent.putExtra("outputY", CROP);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	/**
	 * 上传新照片
	 */
	private void uploadNewPhoto() {
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (loading != null)
					loading.dismiss();
				if (msg.what == 1 && msg.obj != null) {
					APIResult res = (APIResult) msg.obj;
					// 提示信息
					UIHelper.ToastMessage(UserInfo.this, res.status);
					if (res.OK()) {
						// 显示新头像
						face.setImageBitmap(protraitBitmap);
					}
				} else if (msg.what == -1 && msg.obj != null) {
					((AppException) msg.obj).makeToast(UserInfo.this);
				}
			}
		};

		if (loading != null) {
			loading.setLoadText("正在上传头像···");
			loading.show();
		}

		new Thread() {
			public void run() {
				// 获取头像缩略图
				if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
					protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath,
							200, 200);
				} else {
					loading.setLoadText("图像不存在，上传失败·");
					loading.hide();
				}

				if (protraitBitmap != null) {
					Message msg = new Message();
					try {
						APIResult res = ((AppContext) getApplication())
								.updatePortrait(protraitFile);
						if (res != null && res.OK()) {
							// 保存新头像到缓存
							String filename = FileUtils.getFileName(user
									.getFace());
							ImageUtils.saveImage(UserInfo.this, filename,
									protraitBitmap);
						}
						msg.what = 1;
						msg.obj = res;
					} catch (AppException e) {
						loading.setLoadText("上传出错·");
						loading.hide();
						msg.what = -1;
						msg.obj = e;
					} catch (IOException e) {
						e.printStackTrace();
					}
					handler.sendMessage(msg);
				} else {
					loading.setLoadText("图像不存在，上传失败·");
					loading.hide();
				}
			};
		}.start();
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
			startActionCrop(origUri);// 拍照后裁剪
			break;
		case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
			startActionCrop(data.getData());// 选图后裁剪
			break;
		case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
			uploadNewPhoto();// 上传新照片
			break;
		}
	}
}
