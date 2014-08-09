package com.liwuso.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.liwuso.app.AppContext;
import com.liwuso.app.AppException;
import com.liwuso.app.R;
import com.liwuso.app.adapter.ListViewMaleAdapter;
import com.liwuso.app.adapter.ListViewFemaleAdapter;
import com.liwuso.app.common.UIHelper;
import com.liwuso.app.widget.PullToRefreshListView;
import com.pys.liwuso.bean.NewsList;
import com.pys.liwuso.bean.Notice;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;

public class Main extends BaseActivity {

	private ProgressBar mHeadProgress;
	private ProgressBar lvFemale_foot_progress;
	private ProgressBar lvMale_foot_progress;

	// private int curNewsCatalog = NewsList.CATALOG_ALL;

	private PullToRefreshListView lvFemale;
	private PullToRefreshListView lvMale;

	private Handler lvFemaleHandler;
	private Handler lvMaleHandler;

	private ListViewFemaleAdapter lvFemaleAdapter;
	private ListViewMaleAdapter lvMaleAdapter;

	private List<Person> lvFemaleData = new ArrayList<Person>();
	private List<Person> lvMaleData = new ArrayList<Person>();

	private int lvFemaleSumData;
	private int lvMaleSumData;
	
	private int mCurSel;

	private TextView lvFemale_foot_more;

	LinearLayout frame_layout_female;
	View frame_layout_sepeartor;
	LinearLayout frame_layout_male;

	private Button framebtn_All;
	private Button framebtn_Female;
	private Button framebtn_Male;

	private Button fbLiwuso;
	private Button fbSearch;
	private Button fbFavorite;
	private Button fbMore;

	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		appContext = (AppContext) getApplication();

		this.initHeadView();
		this.initFootBar();

		this.initFrameButton();
		this.initFrameListView();
	}
	
    @Override
    protected void onResume() {
    	super.onResume();
		// if(mViewCount == 0) mViewCount = 4;
		// if(mCurSel == 0 && !fbNews.isChecked()) {
		// fbNews.setChecked(true);
		// fbQuestion.setChecked(false);
		// fbTweet.setChecked(false);
		// fbactive.setChecked(false);
		// }
		// //读取左右滑动配置
		// mScrollLayout.setIsScroll(appContext.isScroll());
    	
    	
    }

	private void initHeadView() {
		mHeadProgress = (ProgressBar) findViewById(R.id.main_head_progress);
		// TODO: lvPerson_foot_progress = (ProgressBar)
		// findViewById(R.id.main_head_progress);
	}

	private void initFootBar() {
		fbLiwuso = (Button) findViewById(R.id.bottom_btn_so);
		fbSearch = (Button) findViewById(R.id.bottom_btn_search);
		fbFavorite = (Button) findViewById(R.id.bottom_btn_favorite);
		fbMore = (Button) findViewById(R.id.bottom_btn_more);
	}

	private void initFrameButton() {
		frame_layout_female = (LinearLayout) findViewById(R.id.frame_layout_female);
		frame_layout_sepeartor = (View) findViewById(R.id.frame_layout_sepeartor);
		frame_layout_male = (LinearLayout) findViewById(R.id.frame_layout_male);

		framebtn_All = (Button) findViewById(R.id.frame_btn_all);
		framebtn_Female = (Button) findViewById(R.id.frame_btn_female);
		framebtn_Male = (Button) findViewById(R.id.frame_btn_male);
		framebtn_All.setEnabled(false);

		framebtn_All.setOnClickListener(framePersonBtnClick(framebtn_All, 0));
		framebtn_Female.setOnClickListener(framePersonBtnClick(framebtn_Female,
				1));
		framebtn_Male.setOnClickListener(framePersonBtnClick(framebtn_Male, 2));

	}

	private View.OnClickListener framePersonBtnClick(final Button btn,
			final int catalog) {
		return new View.OnClickListener() {
			public void onClick(View v) {

				framebtn_All.setEnabled(framebtn_All != btn);
				framebtn_Female.setEnabled(framebtn_Female != btn);
				framebtn_Male.setEnabled(framebtn_Male != btn);

				// curNewsCatalog = catalog;
				if (btn == framebtn_All) {
					frame_layout_female.setVisibility(View.VISIBLE);
					frame_layout_sepeartor.setVisibility(View.VISIBLE);
					frame_layout_male.setVisibility(View.VISIBLE);
				} else if (btn == framebtn_Female) {
					frame_layout_female.setVisibility(View.VISIBLE);
					frame_layout_male.setVisibility(View.GONE);
					frame_layout_sepeartor.setVisibility(View.GONE);
				} else if (btn == framebtn_Male) {
					frame_layout_male.setVisibility(View.VISIBLE);
					frame_layout_female.setVisibility(View.GONE);
					frame_layout_sepeartor.setVisibility(View.GONE);

				}
			}
		};
	}

	private void initFrameListView() {
		this.initFemaleListView();
		this.initMaleListView();
		this.initFrameListViewData();
	}

	private void initFemaleListView() {
		lvFemaleAdapter = new ListViewFemaleAdapter(this, lvFemaleData);
		lvFemale = (PullToRefreshListView) findViewById(R.id.frame_listview_person_female);
		lvFemale.setAdapter(lvFemaleAdapter);
		lvFemale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO:
			}
		});
		lvFemale.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvFemale.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (lvFemaleData.isEmpty())
					return;

				// 判断是否滚动到底部
				int pageIndex = 1;
				loadLvPersonData(0, pageIndex, lvFemaleHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.LISTVIEW_DATATYPE_FEMALE);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvFemale.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvFemale.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvPersonData(0, 0, lvFemaleHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.LISTVIEW_DATATYPE_FEMALE);
			}
		});

	}

	private void initMaleListView() {
		lvMaleAdapter = new ListViewMaleAdapter(this, lvMaleData);
		lvMale = (PullToRefreshListView) findViewById(R.id.frame_listview_person_male);
		lvMale.setAdapter(lvMaleAdapter);

		lvMale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO:
			}
		});
		lvMale.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvMale.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码了
				if (lvMaleData.isEmpty())
					return;
				// 判断是否滚动到底部
				int pageIndex = 1;
				loadLvPersonData(1, pageIndex, lvFemaleHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.LISTVIEW_DATATYPE_MALE);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvMale.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvMale.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvPersonData(1, 0, lvFemaleHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.LISTVIEW_DATATYPE_MALE);
			}
		});
	}

	private void initFrameListViewData() {
		lvFemaleHandler = this.getLvHandler(lvFemale, lvFemaleAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvMaleHandler = this.getLvHandler(lvMale, lvMaleAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		// Load Person data
		if (lvFemaleData.isEmpty()) {
			loadLvPersonData(0, 0, lvFemaleHandler,
					UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_FEMALE);
			loadLvPersonData(1, 0, lvMaleHandler,
					UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_MALE);
		}
	}

	private Handler getLvHandler(final PullToRefreshListView lv,
			final BaseAdapter adapter, final TextView more,
			final ProgressBar progress, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					Notice notice = handleLvData(msg.what, msg.obj, msg.arg2,
							msg.arg1);

					if (msg.what < pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						// more.setText(R.string.load_full);
					} else if (msg.what == pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						// more.setText(R.string.load_more);
					}

				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
					((AppException) msg.obj).makeToast(Main.this);
				}
				if (adapter.getCount() == 0) {
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					// more.setText(R.string.load_empty);
				}
				// progress.setVisibility(ProgressBar.GONE);
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

	private void loadLvPersonData(final int catalog, final int pageIndex,
			final Handler handler, final int action, final int dataType) {
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
				msg.arg2 = dataType;
				// if (curNewsCatalog == catalog)
				handler.sendMessage(msg);
			}
		}.start();
	}

	private Notice handleLvData(int what, Object obj, int objtype,
			int actiontype) {
		Notice notice = null;
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			switch (objtype) {
			case UIHelper.LISTVIEW_DATATYPE_FEMALE:
				PersonList femalelist = (PersonList) obj;
				notice = femalelist.getNotice();
				lvFemaleSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvFemaleData.size() > 0) {
						for (Person person1 : femalelist.getPersonList()) {
							boolean b = false;
							for (Person person2 : lvFemaleData) {
								if (person1.getId() == person2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvFemaleData.clear();
				lvFemaleData.addAll(femalelist.getPersonList());
				break;

			case UIHelper.LISTVIEW_DATATYPE_MALE:
				PersonList malelist = (PersonList) obj;
				notice = malelist.getNotice();
				lvMaleSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvFemaleData.size() > 0) {
						for (Person person1 : malelist.getPersonList()) {
							boolean b = false;
							for (Person person2 : lvMaleData) {
								if (person1.getId() == person2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvMaleData.clear();
				lvMaleData.addAll(malelist.getPersonList());
				break;

			case UIHelper.LISTVIEW_ACTION_SCROLL:
				switch (objtype) {
				case UIHelper.LISTVIEW_DATATYPE_FEMALE:
					PersonList list = (PersonList) obj;
					notice = list.getNotice();
					lvFemaleSumData += what;
					if (lvFemaleData.size() > 0) {
						for (Person person1 : list.getPersonList()) {
							boolean b = false;
							for (Person person2 : lvFemaleData) {
								if (person1.getId() == person2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								lvFemaleData.add(person1);
						}
					} else {
						lvFemaleData.addAll(list.getPersonList());
					}
					break;
				case UIHelper.LISTVIEW_DATATYPE_MALE:
					PersonList malelist2 = (PersonList) obj;
					notice = malelist2.getNotice();
					lvMaleSumData += what;
					if (lvMaleData.size() > 0) {
						for (Person person1 : malelist2.getPersonList()) {
							boolean b = false;
							for (Person person2 : lvMaleData) {
								if (person1.getId() == person2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								lvMaleData.add(person1);
						}
					} else {
						lvMaleData.addAll(malelist2.getPersonList());
					}
					break;
				}
				break;
			}
		}
		return notice;
	}

	public void clickBar(View view) {
		int viewId = view.getId();
		switch (viewId) {
		case R.id.bottom_btn_so:
			clickFragment(new FragmentPerson(), 0);
			break;
		case R.id.bottom_btn_search:
			clickFragment(new FragmentSearch(), 1);
			break;
		case R.id.bottom_btn_favorite:
			clickFragment(new FavoriteFrament(), 2);
			break;
		case R.id.bottom_btn_more:
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

}
