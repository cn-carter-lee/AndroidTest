package com.liwuso.app.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

	//
	private ScrollLayout slSo;
	private ScrollLayout slSearch;
	private ScrollLayout slFavorite;
	private ScrollLayout slMore;

	private ProgressBar mHeadProgress;
	private ProgressBar lvFemale_foot_progress;
	private ProgressBar lvMale_foot_progress;

	// private int curNewsCatalog = NewsList.CATALOG_ALL;

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

	private Button fbLiwuso;
	private Button fbSearch;
	private Button fbFavorite;
	private Button fbMore;

	private LinearLayout searchCategoryBar;

	private Button btnMoreAbout;
	private Button btnMoreQuestion;
	private Button btnMoreAgreement;
	private Button btnMoreAdvice;
	private Button btnMoreContact;
	private Button btnMoreCheckVertion;

	// Search
	private PullToRefreshGridView gvSearchProduct;

	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		appContext = (AppContext) getApplication();
		this.initHeadView();
		this.initBody();
		this.initFootBar();
		this.initFrameButton();
		this.initFrameListView();
		this.setTitle("您要送谁礼物?");
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

	private void initBody() {
		slSo = (ScrollLayout) findViewById(R.id.main_scrolllayout_so);
		slSearch = (ScrollLayout) findViewById(R.id.main_scrolllayout_search);
		slFavorite = (ScrollLayout) findViewById(R.id.main_scrolllayout_favorite);
		slMore = (ScrollLayout) findViewById(R.id.main_scrolllayout_more);
	}

	private void initFootBar() {
		fbLiwuso = (Button) findViewById(R.id.bottom_btn_so);
		fbLiwuso.setOnClickListener(selectFootBar(1));

		fbSearch = (Button) findViewById(R.id.bottom_btn_search);
		fbSearch.setOnClickListener(selectFootBar(2));

		fbFavorite = (Button) findViewById(R.id.bottom_btn_favorite);
		fbFavorite.setOnClickListener(selectFootBar(3));

		fbMore = (Button) findViewById(R.id.bottom_btn_more);
		fbMore.setOnClickListener(selectFootBar(4));
	}

	private void initFrameButton() {
		// ScrollLayout
		slSo = (ScrollLayout) findViewById(R.id.main_scrolllayout_so);
		slMore = (ScrollLayout) findViewById(R.id.main_scrolllayout_more);

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

		// Search
		searchCategoryBar = (LinearLayout) findViewById(R.id.search_category_bar);

		for (int i = 0; i < 4; i++) {
			Button tempButton = (Button) getLayoutInflater().inflate(
					R.anim.search_tab_button, searchCategoryBar, false);
			tempButton.setText(String.valueOf(i));
			searchCategoryBar.addView(tempButton);
		}
		if (searchCategoryBar.getChildCount() > 0)
			searchCategoryBar.getChildAt(0).setEnabled(false);

		gvSearchProduct = (PullToRefreshGridView) findViewById(R.id.frame_search_gridview_product);
		this.loadSearchProduct();
		// Favorite

		// More
		btnMoreAbout = (Button) findViewById(R.id.btn_more_about);
		btnMoreQuestion = (Button) findViewById(R.id.btn_more_question);
		btnMoreAgreement = (Button) findViewById(R.id.btn_more_agreement);
		btnMoreContact = (Button) findViewById(R.id.btn_more_contact);
		btnMoreAbout.setOnClickListener(frameMoreBtnClick(0));
		btnMoreQuestion.setOnClickListener(frameMoreBtnClick(1));
		btnMoreAgreement.setOnClickListener(frameMoreBtnClick(2));
		btnMoreContact.setOnClickListener(frameMoreBtnClick(3));

		btnMoreAdvice = (Button) findViewById(R.id.btn_more_advice);
		btnMoreCheckVertion = (Button) findViewById(R.id.btn_more_check_vertion);

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
				// TODO:
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
				// TODO:
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

	private void initProductListView() {
		lvProductAdapter = new ListViewProductAdapter(this, lvProductData);
		lvProduct = (PullToRefreshListView) findViewById(R.id.frame_listview_product);
		lvProduct.setAdapter(lvProductAdapter);

		lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO:
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

	private void initAgeListView() {
		lvAgeAdapter = new ListViewAgeAdapter(this, lvAgeData);
		lvAge = (PullToRefreshListView) findViewById(R.id.frame_listview_age);
		lvAge.setAdapter(lvAgeAdapter);

		lvAge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO:
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
				// TODO:
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

			loadLvData(1, 0, lvAgeHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_AGE);

			loadLvData(1, 0, lvPurposeHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_PURPOSE);

			loadLvData(1, 0, lvProductHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_PRODUCT);

			loadLvData(1, 0, lvFavoriteProductHandler,
					UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_FAVORITEPRODUCT);
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

	// Top
	private void setTitle(String title) {
		TextView titleView = (TextView) this.findViewById(R.id.navbar_title);
		titleView.setText(title);
	}

	// Body
	private void showBodyLayout(int i) {
		if (i == 1)
			slSo.setVisibility(View.VISIBLE);
		else
			slSo.setVisibility(View.GONE);

		if (i == 2)
			slSearch.setVisibility(View.VISIBLE);
		else
			slSearch.setVisibility(View.GONE);

		if (i == 3)
			slFavorite.setVisibility(View.VISIBLE);
		else
			slFavorite.setVisibility(View.GONE);

		if (i == 4)
			slMore.setVisibility(View.VISIBLE);
		else
			slMore.setVisibility(View.GONE);
	}

	// Footer
	private View.OnClickListener selectFootBar(final int i) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				fbLiwuso.setEnabled(i != 1);
				fbSearch.setEnabled(i != 2);
				fbFavorite.setEnabled(i != 3);
				fbMore.setEnabled(i != 4);
				showBodyLayout(i);
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

	// Favorite

	// More

	private View.OnClickListener frameMoreBtnClick(final int item_index) {
		return new View.OnClickListener() {
			public void onClick(View v) {

				// this.setNavgationTitle(getResources().getStringArray(
				// R.array.more_info_title)[item_index]);
				// View rootView = inflater.inflate(R.layout.more_info,
				// container, false);
				int cuuuuu = slMore.getCurScreen();

				slMore.scrollToScreen(cuuuuu);
				TextView myTextView = (TextView) findViewById(R.id.txtMoreInfo);
				myTextView.setText(Html.fromHtml(getResources().getStringArray(
						R.array.more_info)[item_index]));

				// MoreActivity a = (MoreActivity) this.getActivity();
				// a.setActionBarText(getResources().getStringArray(
				// R.array.gift_more_about_title)[item_index]);

				//
				// framebtn_All.setEnabled(framebtn_All != btn);
				// framebtn_Female.setEnabled(framebtn_Female != btn);
				// framebtn_Male.setEnabled(framebtn_Male != btn);
				//
				// // curNewsCatalog = catalog;
				// if (btn == framebtn_All) {
				// frame_layout_female.setVisibility(View.VISIBLE);
				// frame_layout_sepeartor.setVisibility(View.VISIBLE);
				// frame_layout_male.setVisibility(View.VISIBLE);
				// } else if (btn == framebtn_Female) {
				// frame_layout_female.setVisibility(View.VISIBLE);
				// frame_layout_male.setVisibility(View.GONE);
				// frame_layout_sepeartor.setVisibility(View.GONE);
				// } else if (btn == framebtn_Male) {
				// frame_layout_male.setVisibility(View.VISIBLE);
				// frame_layout_female.setVisibility(View.GONE);
				// frame_layout_sepeartor.setVisibility(View.GONE);
				//
				// }
			}
		};
	}

}
