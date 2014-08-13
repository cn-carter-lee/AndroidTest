package com.liwuso.app.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.liwuso.app.AppContext;
import com.liwuso.app.AppException;
import com.liwuso.app.R;
import com.liwuso.app.adapter.ImageAdapter;
import com.liwuso.app.adapter.ListViewAgeAdapter;
import com.liwuso.app.adapter.ListViewFavoriteProductAdapter;
import com.liwuso.app.adapter.ListViewFemaleAdapter;
import com.liwuso.app.adapter.ListViewMaleAdapter;
import com.liwuso.app.adapter.ListViewProductAdapter;
import com.liwuso.app.adapter.ListViewPurposeAdapter;
import com.liwuso.app.common.UIHelper;
import com.liwuso.app.widget.PullToRefreshGridView;
import com.liwuso.app.widget.PullToRefreshListView;
import com.liwuso.app.widget.ScrollLayout;
import com.pys.liwuso.bean.Age;
import com.pys.liwuso.bean.AgeList;
import com.pys.liwuso.bean.Notice;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;
import com.pys.liwuso.bean.Product;
import com.pys.liwuso.bean.ProductList;
import com.pys.liwuso.bean.Purpose;
import com.pys.liwuso.bean.PurposeList;

public class Main extends BaseActivity {

	private Person currentPerson = null;
	private Age currentAge = null;
	private Purpose currentPurpose = null;

	private TextView txtSoAgePersonName;
	private TextView txtSoPurposePersoname;
	private TextView txtSoPurposeAgeName;
	private TextView txtSoProductPersonName;
	private TextView txtSoProductAgeName;
	private TextView txtSoProductPurposeName;

	private RelativeLayout mainHeaderBar;
	private int[] slResourceArray = { R.id.main_scrolllayout_so,
			R.id.main_scrolllayout_search, R.id.main_scrolllayout_favorite,
			R.id.main_scrolllayout_more };
	private ScrollLayout[] slArray = new ScrollLayout[4];
	private int currentSlIndex = 0;

	private ProgressBar mHeadProgress;
	private ProgressBar lvFemale_foot_progress;
	private ProgressBar lvMale_foot_progress;

	private PullToRefreshListView lvFemale;
	private PullToRefreshListView lvMale;
	private PullToRefreshListView lvAge;
	private PullToRefreshListView lvPurpose;
	private PullToRefreshListView lvProduct;
	private PullToRefreshListView lvFavoriteProduct;

	private Handler lvFemaleHandler;
	private Handler lvMaleHandler;
	private Handler lvAgeHandler;
	private Handler lvPurposeHandler;
	private Handler lvProductHandler;
	private Handler lvFavoriteProductHandler;

	private ListViewFemaleAdapter lvFemaleAdapter;
	private ListViewMaleAdapter lvMaleAdapter;
	private ListViewAgeAdapter lvAgeAdapter;
	private ListViewPurposeAdapter lvPurposeAdapter;
	private ListViewProductAdapter lvProductAdapter;
	private ListViewFavoriteProductAdapter lvFavoriteProductAdapter;

	private List<Person> lvFemaleData = new ArrayList<Person>();
	private List<Person> lvMaleData = new ArrayList<Person>();
	private List<Age> lvAgeData = new ArrayList<Age>();
	private List<Purpose> lvPurposeData = new ArrayList<Purpose>();
	private List<Product> lvProductData = new ArrayList<Product>();
	private List<Product> lvFavoriteProductData = new ArrayList<Product>();

	private int lvFemaleSumData;
	private int lvMaleSumData;
	private int lvAgeSumData;
	private int lvPurposeSumData;
	private int lvProductSumData;
	private int lvFavoriteProductSumData;

	private int mCurSel;

	private TextView lvFemale_foot_more;

	LinearLayout frame_layout_female;
	View frame_layout_sepeartor;
	LinearLayout frame_layout_male;

	private Button framebtn_All;
	private Button framebtn_Female;
	private Button framebtn_Male;

	private int[] fbResourceArray = { R.id.bottom_btn_so,
			R.id.bottom_btn_search, R.id.bottom_btn_favorite,
			R.id.bottom_btn_more };
	private Button[] footBtnArray = new Button[4];

	private LinearLayout searchCategoryBar;
	private Button[] searchTabButtons;
	private Button btnSearch;
	private int[] moreBtnResourceArray = { R.id.btn_more_about,
			R.id.btn_more_question, R.id.btn_more_agreement,
			R.id.btn_more_contact, R.id.btn_more_advice,
			R.id.btn_more_check_vertion };
	private Button[] moreBtnArray = new Button[6];

	// Top nav bar
	private Button btnTopNavPre;
	// private int currentTopNavIndex = 0;
	// Search
	private PullToRefreshGridView gvSearchProduct;

	private Button btnMoreAdviceSubmit;
	private Button btnMoreAdviceQuersion;

	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		appContext = (AppContext) getApplication();
		this.initMainView();
		this.initFrameButtons();
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

	private void initMainView() {
		// Top
		mainHeaderBar = (RelativeLayout) findViewById(R.id.main_header_bar);
		btnTopNavPre = (Button) findViewById(R.id.btnTopNavPre);
		btnTopNavPre.setOnClickListener(frameTopNavPreBtnClick());
		mHeadProgress = (ProgressBar) findViewById(R.id.main_head_progress);
		// TODO: lvPerson_foot_progress = (ProgressBar)
		// findViewById(R.id.main_head_progress);
		// Body
		for (int i = 0; i < slArray.length; i++) {
			ScrollLayout scrollLayout = (ScrollLayout) findViewById(slResourceArray[i]);
			slArray[i] = scrollLayout;
		}
		// Footer
		for (int i = 0; i < footBtnArray.length; i++) {
			Button footBtn = (Button) findViewById(fbResourceArray[i]);
			footBtn.setOnClickListener(selectFootBar(i));
			footBtnArray[i] = footBtn;
		}

	}

	private void initFrameButtons() {
		// So
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

		txtSoAgePersonName = (TextView) findViewById(R.id.txt_so_age_personname);
		txtSoPurposePersoname = (TextView) findViewById(R.id.txt_so_purpose_personname);
		txtSoPurposeAgeName = (TextView) findViewById(R.id.txt_so_purpose_agename);
		txtSoProductPersonName = (TextView) findViewById(R.id.txt_so_product_personname);
		txtSoProductAgeName = (TextView) findViewById(R.id.txt_so_product_agename);
		txtSoProductPurposeName = (TextView) findViewById(R.id.txt_so_product_purposename);
		;

		// Search
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(frameSearchBtnClick());
		searchCategoryBar = (LinearLayout) findViewById(R.id.search_category_bar);
		searchTabButtons = new Button[4];
		for (int i = 0; i < 4; i++) {
			Button tempButton = (Button) getLayoutInflater().inflate(
					R.anim.search_tab_button, searchCategoryBar, false);
			tempButton.setText(String.valueOf(i));

			tempButton.setOnClickListener(frameSearchTabBtnClick(i));

			searchTabButtons[i] = tempButton;

			searchCategoryBar.addView(tempButton);
		}
		if (searchCategoryBar.getChildCount() > 0)
			searchCategoryBar.getChildAt(0).setEnabled(false);

		gvSearchProduct = (PullToRefreshGridView) findViewById(R.id.frame_search_gridview_product);
		this.loadSearchProduct();
		// Favorite

		// More
		for (int i = 0; i < moreBtnArray.length; i++) {
			Button moreButton = (Button) findViewById(moreBtnResourceArray[i]);
			moreBtnArray[i] = moreButton;
			moreButton.setOnClickListener(frameMoreBtnClick(i));
		}

		btnMoreAdviceSubmit = (Button) findViewById(R.id.btn_more_advice_submit);
		btnMoreAdviceSubmit.setOnClickListener(frameMoreAdviceBtnClick());
		btnMoreAdviceQuersion = (Button) findViewById(R.id.btn_more_advice_quersion);
		btnMoreAdviceQuersion.setOnClickListener(frameMoreBtnClick(1));
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
		this.initAgeListView();
		this.initPurposeView();
		this.initProductListView();
		this.initFavoriteProductListView();
		this.initFrameListViewData();
	}

	private void initFemaleListView() {
		lvFemaleAdapter = new ListViewFemaleAdapter(this, lvFemaleData);
		lvFemale = (PullToRefreshListView) findViewById(R.id.frame_listview_person_female);
		lvFemale.setAdapter(lvFemaleAdapter);
		lvFemale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentPerson = lvFemaleData.get(position - 1);
				setSoNavInfo();
				slArray[currentSlIndex].currentVisibleScreen++;
				slArray[currentSlIndex]
						.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
				loadLvData(1, 0, lvAgeHandler, UIHelper.LISTVIEW_ACTION_INIT,
						UIHelper.LISTVIEW_DATATYPE_AGE);
				setTopTitle();
			}
		});
		lvFemale.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvFemale.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码�?
				if (lvFemaleData.isEmpty())
					return;

				// 判断是否滚动到底�?
				int pageIndex = 1;
				loadLvData(0, pageIndex, lvFemaleHandler,
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
				loadLvData(0, 0, lvFemaleHandler,
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
				currentPerson = lvMaleData.get(position - 1);
				setSoNavInfo();
				slArray[currentSlIndex].currentVisibleScreen++;
				slArray[currentSlIndex]
						.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
				setTopBtnPreVisible(true);
				loadLvData(1, 0, lvAgeHandler, UIHelper.LISTVIEW_ACTION_INIT,
						UIHelper.LISTVIEW_DATATYPE_AGE);
				setTopTitle();
			}
		});
		lvMale.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvMale.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码�?
				if (lvMaleData.isEmpty())
					return;
				// 判断是否滚动到底�?
				int pageIndex = 1;
				loadLvData(1, pageIndex, lvFemaleHandler,
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
				loadLvData(1, 0, lvFemaleHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.LISTVIEW_DATATYPE_MALE);
			}
		});
	}

	private void initAgeListView() {
		lvAgeAdapter = new ListViewAgeAdapter(this, lvAgeData);
		lvAge = (PullToRefreshListView) findViewById(R.id.frame_listview_age);
		lvAge.setAdapter(lvAgeAdapter);

		lvAge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentAge = lvAgeData.get(position - 1);
				slArray[currentSlIndex].currentVisibleScreen++;
				slArray[currentSlIndex]
						.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
				setSoNavInfo();
				loadLvData(1, 0, lvPurposeHandler,
						UIHelper.LISTVIEW_ACTION_INIT,
						UIHelper.LISTVIEW_DATATYPE_PURPOSE);
				setTopTitle();
			}
		});
		lvAge.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvAge.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码�?
				if (lvAgeData.isEmpty())
					return;
				// 判断是否滚动到底�?
				int pageIndex = 1;
				loadLvData(1, pageIndex, lvAgeHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.LISTVIEW_DATATYPE_AGE);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvAge.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvAge.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvData(1, 0, lvAgeHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.LISTVIEW_DATATYPE_AGE);
			}
		});
	}

	private void initPurposeView() {
		lvPurposeAdapter = new ListViewPurposeAdapter(this, lvPurposeData);
		lvPurpose = (PullToRefreshListView) findViewById(R.id.frame_listview_purpose);
		lvPurpose.setAdapter(lvPurposeAdapter);

		lvPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentPurpose = lvPurposeData.get(position - 1);
				final WaitDialog waitDialog = new WaitDialog();
				waitDialog.show(getSupportFragmentManager(), "");
				Handler sleepHandler = new Handler();
				sleepHandler.postDelayed(new Runnable() {
					public void run() {
						waitDialog.dismiss();
						setSoNavInfo();
						slArray[currentSlIndex].currentVisibleScreen++;
						slArray[currentSlIndex]
								.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
						loadLvData(1, 0, lvProductHandler,
								UIHelper.LISTVIEW_ACTION_INIT,
								UIHelper.LISTVIEW_DATATYPE_PRODUCT);
						setTopTitle();
					}
				}, 3000);
			}
		});
		lvPurpose.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvPurpose.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码�?
				if (lvPurposeData.isEmpty())
					return;
				// 判断是否滚动到底�?
				int pageIndex = 1;
				loadLvData(1, pageIndex, lvPurposeHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.LISTVIEW_DATATYPE_PURPOSE);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvPurpose.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvPurpose
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						loadLvData(1, 0, lvPurposeHandler,
								UIHelper.LISTVIEW_ACTION_REFRESH,
								UIHelper.LISTVIEW_DATATYPE_PURPOSE);
					}
				});
	}

	private void initProductListView() {
		lvProductAdapter = new ListViewProductAdapter(this, lvProductData);
		lvProduct = (PullToRefreshListView) findViewById(R.id.frame_listview_product);
		lvProduct.setAdapter(lvProductAdapter);

		lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		lvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvProduct.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码�?
				if (lvProductData.isEmpty())
					return;
				// 判断是否滚动到底�?
				int pageIndex = 1;
				loadLvData(1, pageIndex, lvProductHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.LISTVIEW_DATATYPE_PRODUCT);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvProduct.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvProduct
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						loadLvData(1, 0, lvProductHandler,
								UIHelper.LISTVIEW_ACTION_REFRESH,
								UIHelper.LISTVIEW_DATATYPE_MALE);
					}
				});
	}

	private void initFavoriteProductListView() {
		lvFavoriteProductAdapter = new ListViewFavoriteProductAdapter(this,
				lvFavoriteProductData);
		lvFavoriteProduct = (PullToRefreshListView) findViewById(R.id.frame_listview_favorite_product);
		lvFavoriteProduct.setAdapter(lvFavoriteProductAdapter);

		lvFavoriteProduct
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO:
					}
				});
		lvFavoriteProduct
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						lvPurpose.onScrollStateChanged(view, scrollState);
						// 数据为空--不用继续下面代码�?
						if (lvFavoriteProductData.isEmpty())
							return;
						// 判断是否滚动到底�?
						int pageIndex = 1;
						loadLvData(1, pageIndex, lvFavoriteProductHandler,
								UIHelper.LISTVIEW_ACTION_SCROLL,
								UIHelper.LISTVIEW_DATATYPE_PURPOSE);
					}

					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						lvPurpose.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}
				});
		lvFavoriteProduct
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						loadLvData(1, 0, lvFavoriteProductHandler,
								UIHelper.LISTVIEW_ACTION_REFRESH,
								UIHelper.LISTVIEW_DATATYPE_PURPOSE);
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

		lvAgeHandler = this.getLvHandler(lvAge, lvAgeAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);
		lvPurposeHandler = this.getLvHandler(lvPurpose, lvPurposeAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);
		lvProductHandler = this.getLvHandler(lvProduct, lvProductAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);
		lvFavoriteProductHandler = this.getLvHandler(lvFavoriteProduct,
				lvFavoriteProductAdapter, lvFemale_foot_more,
				lvFemale_foot_progress, AppContext.PAGE_SIZE);

		// Load Person data
		if (lvFemaleData.isEmpty()) {
			loadLvData(0, 0, lvFemaleHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_FEMALE);
			loadLvData(1, 0, lvMaleHandler, UIHelper.LISTVIEW_ACTION_INIT,
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
					// 有异�?-显示加载出错 & 弹出错误消息
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

	private void loadLvData(final int catalog, final int pageIndex,
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
					switch (dataType) {
					case UIHelper.LISTVIEW_DATATYPE_FEMALE:
					case UIHelper.LISTVIEW_DATATYPE_MALE:
						PersonList personlist = appContext.getPersonList(
								catalog, pageIndex, isRefresh);
						msg.what = personlist.getPageSize();
						msg.obj = personlist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_AGE:
						AgeList agelist = appContext.getAgeList(catalog,
								pageIndex, isRefresh);
						msg.what = agelist.getPageSize();
						msg.obj = agelist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_PURPOSE:
						PurposeList purposelist = appContext.getPurposeList(
								catalog, pageIndex, isRefresh);
						msg.what = purposelist.getPageSize();
						msg.obj = purposelist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_PRODUCT:
						ProductList productlist = appContext.getProductList(
								catalog, pageIndex, isRefresh);
						msg.what = productlist.getPageSize();
						msg.obj = productlist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_FAVORITEPRODUCT:
						ProductList favoriteproductlist = appContext
								.getFavoriteProductList(catalog, pageIndex,
										isRefresh);
						msg.what = favoriteproductlist.getPageSize();
						msg.obj = favoriteproductlist;
						break;

					}
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
		switch (objtype) {
		case UIHelper.LISTVIEW_DATATYPE_FEMALE:
			handleLvDataFemale(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_MALE:
			handleLvDataMale(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_AGE:
			handleLvDataAge(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_PURPOSE:
			handleLvDataPurpose(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_PRODUCT:
			handleLvDataProduct(what, obj, objtype, actiontype);
		case UIHelper.LISTVIEW_DATATYPE_FAVORITEPRODUCT:
			handleLvDataFavoriteProduct(what, obj, objtype, actiontype);
			break;
		}

		return notice;
	}

	private void handleLvDataFemale(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			PersonList femalelist = (PersonList) obj;
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

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			PersonList list = (PersonList) obj;
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
		}
	}

	private void handleLvDataMale(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			PersonList malelist = (PersonList) obj;
			lvMaleSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvMaleData.size() > 0) {
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

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			PersonList list = (PersonList) obj;
			lvMaleSumData += what;
			if (lvMaleData.size() > 0) {
				for (Person person1 : list.getPersonList()) {
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
				lvMaleData.addAll(list.getPersonList());
			}

			break;
		}
	}

	private void handleLvDataProduct(int what, Object obj, int objtype,
			int actiontype) {
		setSoNavInfo();
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			ProductList productlist = (ProductList) obj;
			lvProductSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvFemaleData.size() > 0) {
					for (Product product1 : productlist.getProductList()) {
						boolean b = false;
						for (Product product2 : lvProductData) {
							if (product1.getId() == product2.getId()) {
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
			lvProductData.clear();
			lvProductData.addAll(productlist.getProductList());

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			ProductList list = (ProductList) obj;
			lvProductSumData += what;
			if (lvProductData.size() > 0) {
				for (Product product1 : list.getProductList()) {
					boolean b = false;
					for (Product product2 : lvProductData) {
						if (product1.getId() == product2.getId()) {
							b = true;
							break;
						}
					}
					if (!b)
						lvProductData.add(product1);
				}
			} else {
				lvProductData.addAll(list.getProductList());
			}

			break;
		}
	}

	private void handleLvDataAge(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			AgeList agelist = (AgeList) obj;
			lvAgeSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvAgeData.size() > 0) {
					for (Age age1 : agelist.getAgeList()) {
						boolean b = false;
						for (Age age2 : lvAgeData) {
							if (age1.getId() == age2.getId()) {
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
			lvAgeData.clear();
			lvAgeData.addAll(agelist.getAgeList());

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			AgeList list = (AgeList) obj;
			lvAgeSumData += what;
			if (lvAgeData.size() > 0) {
				for (Age age1 : list.getAgeList()) {
					boolean b = false;
					for (Age age2 : lvAgeData) {
						if (age1.getId() == age2.getId()) {
							b = true;
							break;
						}
					}
					if (!b)
						lvAgeData.add(age1);
				}
			} else {
				lvAgeData.addAll(list.getAgeList());
			}

			break;
		}
	}

	private void handleLvDataPurpose(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			PurposeList purposelist = (PurposeList) obj;
			lvProductSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvPurposeData.size() > 0) {
					for (Purpose purpose1 : purposelist.getPurposeList()) {
						boolean b = false;
						for (Purpose purpose2 : lvPurposeData) {
							if (purpose1.getId() == purpose2.getId()) {
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
			lvPurposeData.clear();
			lvPurposeData.addAll(purposelist.getPurposeList());

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			PurposeList list = (PurposeList) obj;
			lvProductSumData += what;
			if (lvProductData.size() > 0) {
				for (Purpose purpose1 : list.getPurposeList()) {
					boolean b = false;
					for (Purpose purpose2 : lvPurposeData) {
						if (purpose1.getId() == purpose2.getId()) {
							b = true;
							break;
						}
					}
					if (!b)
						lvPurposeData.add(purpose1);
				}
			} else {
				lvPurposeData.addAll(list.getPurposeList());
			}

			break;
		}
	}

	private void handleLvDataFavoriteProduct(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			ProductList productlist = (ProductList) obj;
			lvFavoriteProductSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvFavoriteProductData.size() > 0) {
					for (Product product1 : productlist.getProductList()) {
						boolean b = false;
						for (Product product2 : lvFavoriteProductData) {
							if (product1.getId() == product2.getId()) {
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
			lvFavoriteProductData.clear();
			lvFavoriteProductData.addAll(productlist.getProductList());

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			ProductList list = (ProductList) obj;
			lvProductSumData += what;
			if (lvProductData.size() > 0) {
				for (Product purpose1 : list.getProductList()) {
					boolean b = false;
					for (Product purpose2 : lvFavoriteProductData) {
						if (purpose1.getId() == purpose2.getId()) {
							b = true;
							break;
						}
					}
					if (!b)
						lvFavoriteProductData.add(purpose1);
				}
			} else {
				lvFavoriteProductData.addAll(list.getProductList());
			}

			break;
		}
	}

	// So nav
	private void setSoNavInfo() {
		if (currentPerson != null) {
			txtSoAgePersonName.setText(currentPerson.Name);
			txtSoPurposePersoname.setText(currentPerson.Name);
			txtSoProductPersonName.setText(currentPerson.Name);
		}
		if (currentAge != null) {
			txtSoPurposeAgeName.setText(currentAge.Name);
			txtSoProductAgeName.setText(currentAge.Name);
		}
		if (currentPurpose != null) {
			txtSoProductPurposeName.setText(currentPurpose.Name);
		}
	}

	// Top
	private void setTopNavBarVisibility(boolean visibility) {
		if (visibility)
			mainHeaderBar.setVisibility(View.VISIBLE);
		else
			mainHeaderBar.setVisibility(View.GONE);
	}

	private void setTopTitle() {
		String strTitle = "";
		switch (currentSlIndex) {
		case 0:
			switch (slArray[currentSlIndex].currentVisibleScreen) {
			case 0:
				strTitle = "您要送谁礼物?";
				break;
			case 1:
				strTitle = String.format("请选择%s的年龄", currentPerson.Name);
				break;
			case 2:
				strTitle = "请选择送礼目的";
				break;
			case 3:
				strTitle = "礼物推荐";
				break;
			}
			break;
		case 1:
			strTitle = "";
			break;
		case 2:
			strTitle = "我的收藏({2})";
			break;
		case 3:
			switch (slArray[currentSlIndex].currentVisibleScreen) {
			case 0:
				strTitle = "更多";
				break;
			default:
				strTitle = getResources().getStringArray(
						R.array.more_info_title)[slArray[currentSlIndex].currentVisibleScreen - 1];
				break;
			}
			break;
		}
		setTopNavBarVisibility(currentSlIndex != 1);
		if (slArray[currentSlIndex].currentVisibleScreen > 0)
			setTopBtnPreVisible(true);
		TextView titleView = (TextView) this.findViewById(R.id.navbar_title);
		titleView.setText(strTitle);
	}

	private View.OnClickListener frameTopNavPreBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				switch (currentSlIndex) {
				case 0:
					slArray[currentSlIndex].currentVisibleScreen--;
					slArray[currentSlIndex]
							.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
					if (slArray[currentSlIndex].currentVisibleScreen == 0)
						setTopBtnPreVisible(false);
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					slArray[currentSlIndex].currentVisibleScreen = 0;
					slArray[currentSlIndex].scrollToScreen(0);
					setTopBtnPreVisible(false);
					break;
				}
				setTopTitle();
			}
		};
	}

	private void setTopBtnPreVisible(boolean visibility) {
		if (visibility)
			btnTopNavPre.setVisibility(View.VISIBLE);
		else
			btnTopNavPre.setVisibility(View.GONE);
	}

	// Footer

	// Foot
	private View.OnClickListener selectFootBar(final int itemIndex) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				currentSlIndex = itemIndex;
				for (int i = 0; i < footBtnArray.length; i++) {
					footBtnArray[i].setEnabled(i != itemIndex);
					if (i == itemIndex)
						slArray[i].setVisibility(View.VISIBLE);
					else
						slArray[i].setVisibility(View.GONE);
				}

				// Set nav pre button
				if (slArray[itemIndex].currentVisibleScreen > 0)
					btnTopNavPre.setVisibility(View.VISIBLE);
				else
					btnTopNavPre.setVisibility(View.GONE);
				currentSlIndex = itemIndex;
				setTopTitle();
			}
		};
	}

	// Search
	private void loadSearchProduct() {
		gvSearchProduct.setAdapter(new ImageAdapter(this, this));
		gvSearchProduct.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

			}
		});
	}

	private View.OnClickListener frameSearchBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				loadSearchProduct();
			}
		};
	}

	private View.OnClickListener frameSearchTabBtnClick(final int itemIndex) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				for (int i = 0; i < searchTabButtons.length; i++) {
					searchTabButtons[i].setEnabled(i != itemIndex);
				}

				// Load data
				loadSearchProduct();
			}
		};
	}

	// Favorite
	private void loadFavorite() {
		if (lvFavoriteProductData.isEmpty())
			loadLvData(1, 0, lvFavoriteProductHandler,
					UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_FAVORITEPRODUCT);
	}

	// More
	private View.OnClickListener frameMoreBtnClick(final int item_index) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				if (item_index < 4) {
					TextView myTextView = (TextView) findViewById(R.id.txtMoreInfo);
					myTextView.setText(Html.fromHtml(getResources()
							.getStringArray(R.array.more_info)[item_index]));
					slArray[currentSlIndex].scrollToScreen(1);
				} else if (item_index == 4) {
					// Advice
					slArray[3].scrollToScreen(2);
				} else if (item_index == 5) {
					// Check version
					CustomDialog m = new CustomDialog(
							(getString(R.string.dialog_version)));
					m.show(getSupportFragmentManager(), "");
					return;
				}

				slArray[currentSlIndex].currentVisibleScreen = item_index + 1;
				setTopTitle();
			}
		};
	}

	private View.OnClickListener frameMoreAdviceBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				CustomDialog m = new CustomDialog(
						(getString(R.string.dialog_submit)));
				m.show(getSupportFragmentManager(), "");
			}
		};
	}

	// Exit application
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			UIHelper.Exit(this);
		}
		return flag;
	}

}
