package com.liwuso.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liwuso.app.AppContext;
import com.liwuso.app.AppException;
import com.liwuso.app.R;
import com.liwuso.app.adapter.ListViewNewsAdapter;
import com.liwuso.app.adapter.PersonAdapter;
import com.liwuso.app.common.UIHelper;
import com.liwuso.widget.PullToRefreshListView;
import com.pys.liwuso.bean.NewsList;
import com.pys.liwuso.bean.Notice;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;
import com.pys.liwuso.bean.Product;

public class Main extends BaseActivity {

	private ProgressBar mHeadProgress;
	private int curNewsCatalog = NewsList.CATALOG_ALL;

	private PullToRefreshListView lvNews;
	private PullToRefreshListView lvPerson;

	private Handler lvNewsHandler;
	private Handler lvPersonHandler;

	private PersonAdapter lvPersonAdapter;

	private ListViewNewsAdapter lvNewsAdapter;
	private List<Product> lvNewsData = new ArrayList<Product>();
	private List<Person> lvPersonData = new ArrayList<Person>();

	private TextView lvNews_foot_more;
	private TextView lvPerson_foot_more;

	private ProgressBar lvPerson_foot_progress;
	private ProgressBar lvNews_foot_progress;

	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		appContext = (AppContext) getApplication();

		// /getFragmentManager().beginTransaction()
		// .add(R.id.container, new FragmentPerson()).commit();

		// this.initFrameListView();
	}

	private void initFrameListView() {
		// 初始化listview控件
		this.initPersonListView();
		// 加载listview数据
		this.initFrameListViewData();
	}

	private void initFrameListViewData() {
		// 初始化Handler
		lvNewsHandler = this.getLvHandler(lvNews, lvNewsAdapter,
				lvNews_foot_more, lvNews_foot_progress, AppContext.PAGE_SIZE);

		lvPersonHandler = this.getLvHandler(lvPerson, lvPersonAdapter,
				lvPerson_foot_more, lvPerson_foot_progress,
				AppContext.PAGE_SIZE);

		// Load Person data

		if (lvPersonData.isEmpty()) {
			loadLvPersonData(curNewsCatalog, 0, lvNewsHandler,
					UIHelper.LISTVIEW_ACTION_INIT);
		}

		//
		// if (lvNewsData.isEmpty()) {
		// loadLvNewsData(curNewsCatalog, 0, lvNewsHandler,
		// UIHelper.LISTVIEW_ACTION_INIT);
		// }
	}

	private void loadLvNewsData(final int catalog, final int pageIndex,
			final Handler handler, final int action) {
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
	}

	private void loadLvPersonData(final int catalog, final int pageIndex,
			final Handler handler, final int action) {
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					PersonList list = appContext.getPersonList(catalog,
							pageIndex, isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_PERSON;
				if (curNewsCatalog == catalog)
					handler.sendMessage(msg);
			}
		}.start();
	}

	private void initPersonListView() {
		String[] data = {};
		lvPersonAdapter = new PersonAdapter(this, data);
	}

	public void clickBar(View view) {
		int viewId = view.getId();
		switch (viewId) {
		case R.id.bottom_btn0:
			clickFragment(new FragmentPerson(), 0);
			break;
		case R.id.bottom_btn1:
			clickFragment(new FragmentSearch(), 1);
			break;
		case R.id.bottom_btn2:
			clickFragment(new FavoriteFrament(), 2);
			break;
		case R.id.bottom_btn3:
			clickFragment(new FragmentMore(), 3);
			break;
		}
	}

	public void searchPerson(View view) {
		relaceFragment(new FragmentAge());
	}

	public void searchAge(View view) {
		relaceFragment(new FragmentPurpose());
	}

	public void searchPurpose(View view) {
		relaceFragment(new FragmentProduct());
	}

	public void clickMore(View view) {
		switch (view.getId()) {
		case R.id.btnAbout:
			relaceFragment(new FragmentMoreInfo(0));
			break;
		case R.id.btnQuestion:
			relaceFragment(new FragmentMoreInfo(1));
			break;
		case R.id.btnAgreement:
			relaceFragment(new FragmentMoreInfo(2));
			break;
		case R.id.btnContact:
			relaceFragment(new FragmentMoreInfo(3));
			break;
		case R.id.btnAdvice:
			relaceFragment(new FragmentAdvice());
			break;
		case R.id.btnCheckVertion:
			relaceFragment(new FragmentVersion());
			break;
		}
	}

	public void checkVersion(View view) {
		MyDialog m = new MyDialog();
		// m.show(getSupportFragmentManager(), "");
	}

	private void relaceFragment(Fragment newFragment) {
		//
		// FragmentTransaction transaction = getSupportFragmentManager()
		// .beginTransaction();
		// transaction.replace(R.id.container, newFragment);
		// transaction.addToBackStack(null);
		// transaction.commit();
	}

	private void clickFragment(Fragment newFragment, int iconIndex) {
		for (int i = 0; i < 4; i++) {
			// if (i == iconIndex)
			// menu.getItem(i).setIcon(pressedIcons[i]);
			// else
			// menu.getItem(i).setIcon(unPressedIcons[i]);
		}
		relaceFragment(newFragment);
	}

	public void setTitle(String title) {
		TextView titleView = (TextView) this.findViewById(R.id.navbar_title);
		titleView.setText(title);
	}

	private Handler getLvHandler(final PullToRefreshListView lv,
			final BaseAdapter adapter, final TextView more,
			final ProgressBar progress, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					Notice notice = handleLvData(msg.what, msg.obj, msg.arg2,
							msg.arg1);

					if (msg.what < pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);

						// 特殊处理-热门动弹不能翻页
						/*
						 * if(lv == lvTweet) { TweetList tlist =
						 * (TweetList)msg.obj; if(lvTweetData.size() ==
						 * tlist.getTweetCount()){
						 * lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						 * more.setText(R.string.load_full); } }
						 */
					}

				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
					((AppException) msg.obj).makeToast(Main.this);
				}
				if (adapter.getCount() == 0) {
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progress.setVisibility(ProgressBar.GONE);
				mHeadProgress.setVisibility(ProgressBar.GONE);
				if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
					lv.onRefreshComplete(getString(R.string.pull_to_refresh_update)
							+ new Date().toLocaleString());
					lv.setSelection(0);
				} else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
					lv.onRefreshComplete();
					lv.setSelection(0);
				}
			}
		};
	}

	private Notice handleLvData(int what, Object obj, int objtype,
			int actiontype) {
		Notice notice = null;

		return notice;
	}

}
