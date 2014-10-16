package com.liwuso.app.ui;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.liwuso.app.AppContext;
import com.liwuso.app.AppException;
import com.liwuso.app.AppManager;
import com.liwuso.app.R;
import com.liwuso.app.adapter.SearchAdapter;
import com.liwuso.app.adapter.ListViewAgeAdapter;
import com.liwuso.app.adapter.ListViewFavoriteAdapter;
import com.liwuso.app.adapter.ListViewFemaleAdapter;
import com.liwuso.app.adapter.ListViewMaleAdapter;
import com.liwuso.app.adapter.ListViewPersonAdapter;
import com.liwuso.app.adapter.ListViewProductAdapter;
import com.liwuso.app.adapter.ListViewAimAdapter;
import com.liwuso.app.common.SortAdapter;
import com.liwuso.app.common.SortItem;
import com.liwuso.app.common.StringUtils;
import com.liwuso.app.common.UIHelper;
import com.liwuso.app.widget.PullToRefreshListView;
import com.liwuso.app.widget.ScrollLayout;
import com.liwuso.bean.Age;
import com.liwuso.bean.AgeList;
import com.liwuso.bean.Aim;
import com.liwuso.bean.AimList;
import com.liwuso.bean.Catalog;
import com.liwuso.bean.CatalogList;
import com.liwuso.bean.MixedPerson;
import com.liwuso.bean.MixedPersonList;
import com.liwuso.bean.Notice;
import com.liwuso.bean.Person;
import com.liwuso.bean.PersonList;
import com.liwuso.bean.Product;
import com.liwuso.bean.ProductList;
import com.liwuso.bean.SearchItem;
import com.liwuso.bean.SearchItemListWapper;
import com.liwuso.bean.SearchItemWapper;
import com.liwuso.utility.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

public class Main extends BaseActivity implements OnItemSelectedListener {

	private boolean shouldExit = false;
	private Person currentPerson = null;
	private Age currentAge = null;
	private Aim currentAim = null;

	private TextView txtSoAgePersonName;
	private TextView txtSoAimPersoname;
	private TextView txtSoAimAgeName;
	private TextView txtSoProductPersonName;
	private TextView txtSoProductAgeName;
	private TextView txtSoProductAimName;

	private RelativeLayout mainHeaderBar;
	private int[] slResourceArray = { R.id.main_scrolllayout_so,
			R.id.main_scrolllayout_search, R.id.main_scrolllayout_favorite,
			R.id.main_scrolllayout_more };

	private ScrollLayout personScrollLayout;

	private ScrollLayout[] slArray = new ScrollLayout[4];
	private int currentSlIndex = 0;
	private int preTaobaolIndex = 0;

	private View lvProduct_footer;
	private View lvSearch_footer;
	private View lvFavorite_footer;

	private ProgressBar lvProduct_foot_progress;
	private ProgressBar lvSearch_foot_progress;
	private ProgressBar lvFavorite_foot_progress;

	// private ProgressBar mHeadProgress;
	private ProgressBar lvFemale_foot_progress;
	private ProgressBar lvMale_foot_progress;

	private PullToRefreshListView lvPerson;
	private PullToRefreshListView lvFemale;
	private PullToRefreshListView lvMale;
	private PullToRefreshListView lvAge;
	private PullToRefreshListView lvAim;
	private PullToRefreshListView lvProduct;
	private PullToRefreshListView lvFavorite;

	private Handler lvPersonHandler;
	private Handler lvFemaleHandler;
	private Handler lvMaleHandler;
	private Handler lvAgeHandler;
	private Handler lvPurposeHandler;
	private Handler lvProductHandler;
	private Handler lvSearchHandler;
	private Handler lvFavoriteProductHandler;

	private ListViewPersonAdapter lvPersonAdapter;
	private ListViewFemaleAdapter lvFemaleAdapter;
	private ListViewMaleAdapter lvMaleAdapter;
	private ListViewAgeAdapter lvAgeAdapter;
	private ListViewAimAdapter lvAimAdapter;
	private ListViewProductAdapter lvProductAdapter;
	private SearchAdapter searchAdapter;
	private ListViewFavoriteAdapter lvFavoriteAdapter;

	private List<MixedPerson> lvPersonData = new ArrayList<MixedPerson>();
	private List<Person> lvFemaleData = new ArrayList<Person>();
	private List<Person> lvMaleData = new ArrayList<Person>();
	private List<Age> lvAgeData = new ArrayList<Age>();
	private List<Aim> lvAimData = new ArrayList<Aim>();
	private List<Product> lvProductData = new ArrayList<Product>();
	private List<SearchItemWapper> lvSearchData = new ArrayList<SearchItemWapper>();
	private List<Product> lvFavoriteProductData = new ArrayList<Product>();

	private int lvPersonSumData;
	private int lvFemaleSumData;
	private int lvMaleSumData;
	private int lvAgeSumData;
	private int lvAimSumData;
	private int lvProductSumData;
	private int lvSearchSumData;
	private int lvFavoriteSumData;

	private TextView lvFemale_foot_more;

	LinearLayout frame_product_header;
	LinearLayout frame_favorite_header;

	// So person
	LinearLayout frame_layout_female;
	LinearLayout frame_layout_male;

	private Button framebtn_All;
	private Button framebtn_Female;
	private Button framebtn_Male;

	private String[] productSortFields = { "mark", "price", "like" };
	private int currentProductSortIndex = 0;

	private int[] fbResourceArray = { R.id.bottom_btn_so,
			R.id.bottom_btn_search, R.id.bottom_btn_favorite,
			R.id.bottom_btn_more };
	private Button[] footBtnArray = new Button[4];

	private LinearLayout searchCategoryBar;
	private List<Button> searchTabButtons;

	private int[] moreBtnResourceArray = { R.id.btn_more_about,
			R.id.btn_more_question, R.id.btn_more_agreement,
			R.id.btn_more_contact, R.id.btn_more_advice,
			R.id.btn_more_check_vertion };
	private Button[] moreBtnArray = new Button[6];

	// Top nav bar
	private Button btnTopNavPre;
	private Spinner spinner;
	private Button btnMoreAdviceSubmit;

	// Search
	private Button btnSearch;
	private EditText txtSearch;
	private PullToRefreshListView lvSearch;

	private int currentSearchCatalogId = 0;

	//
	private LinearLayout footer;
	//
	private LinearLayout taoBaoContainer;

	private WebView taoBaoWebView;
	private ProgressBar taoBaoProgress;
	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Utils.context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		appContext = (AppContext) getApplication();
		this.initMainView();
		this.initFrameButtons();
		this.initFrameListView();
		checkNetwork();
		PushAgent.getInstance(this).onAppStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initMainView() {
		// Top
		mainHeaderBar = (RelativeLayout) findViewById(R.id.main_header_bar);
		btnTopNavPre = (Button) findViewById(R.id.btnTopNavPre);

		btnTopNavPre.setOnClickListener(frameTopNavPreBtnClick());

		spinner = (Spinner) findViewById(R.id.search_spinner);
		ArrayList<SortItem> items = new ArrayList<SortItem>();
		items.add(new SortItem("按综合排序", 0, R.drawable.ic_favorite));
		items.add(new SortItem("按价格排序", 1, R.drawable.ic_favorite));
		items.add(new SortItem("按人气排序", 2, R.drawable.ic_favorite));
		SortAdapter myAdapter = new SortAdapter(this,
				android.R.layout.simple_spinner_item, items);
		spinner.setAdapter(myAdapter);
		spinner.setOnItemSelectedListener(this);
		// TODO: lvPerson_foot_progress = (ProgressBar)
		// findViewById(R.id.main_head_progress);
		// Body
		for (int i = 0; i < slArray.length; i++) {
			ScrollLayout scrollLayout = (ScrollLayout) findViewById(slResourceArray[i]);
			slArray[i] = scrollLayout;
		}
		// Footer
		footer = (LinearLayout) findViewById(R.id.main_linearlayout_footer);
		for (int i = 0; i < footBtnArray.length; i++) {
			Button footBtn = (Button) findViewById(fbResourceArray[i]);
			footBtn.setOnClickListener(selectFootBar(i));
			footBtnArray[i] = footBtn;
		}

	}

	private void initFrameButtons() {
		// So
		personScrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout_person);
		frame_layout_female = (LinearLayout) findViewById(R.id.frame_layout_female);

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
		txtSoAimPersoname = (TextView) findViewById(R.id.txt_so_purpose_personname);
		txtSoAimAgeName = (TextView) findViewById(R.id.txt_so_purpose_agename);

		// Search
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(frameSearchBtnClick());
		txtSearch = (EditText) findViewById(R.id.editSearchText);
		searchCategoryBar = (LinearLayout) findViewById(R.id.search_category_bar);
		this.initSearchTabbar();

		// Favorite

		// More
		for (int i = 0; i < moreBtnArray.length; i++) {
			Button moreButton = (Button) findViewById(moreBtnResourceArray[i]);
			moreBtnArray[i] = moreButton;
			moreButton.setOnClickListener(frameMoreBtnClick(i));
		}

		btnMoreAdviceSubmit = (Button) findViewById(R.id.btn_more_advice_submit);
		btnMoreAdviceSubmit.setOnClickListener(frameMoreAdviceBtnClick());

		// Taobao
		this.initTaobaoView();
	}

	private View.OnClickListener framePersonBtnClick(final Button btn,
			final int catalog) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				framebtn_All.setEnabled(framebtn_All != btn);
				framebtn_Female.setEnabled(framebtn_Female != btn);
				framebtn_Male.setEnabled(framebtn_Male != btn);
				if (btn == framebtn_All) {
					personScrollLayout.snapToScreen(0);
				} else if (btn == framebtn_Female) {
					personScrollLayout.snapToScreen(1);
				} else if (btn == framebtn_Male) {
					personScrollLayout.snapToScreen(2);
				}
			}
		};
	}

	private void initFrameListView() {
		this.initPersonListView();
		this.initFemaleListView();
		this.initMaleListView();
		this.initAgeListView();
		this.initAimView();
		this.initProductListView();
		this.initSearchListView();
		this.initFavoriteListView();
		this.initFrameListViewData();
	}

	private void initPersonListView() {
		lvPersonAdapter = new ListViewPersonAdapter(this, lvPersonData);
		lvPerson = (PullToRefreshListView) findViewById(R.id.frame_listview_person);

		AddHeaderToListView(lvPerson);
		lvPerson.setAdapter(lvPersonAdapter);
		lvPerson.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvPerson.onScrollStateChanged(view, scrollState);

				if (lvPersonData.isEmpty())
					return;

				int pageIndex = 1;
				loadLvData(0, pageIndex, lvPersonHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.LISTVIEW_DATATYPE_PERSON);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvPerson.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvPerson.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvData(0, 0, lvPersonHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.LISTVIEW_DATATYPE_PERSON);
			}
		});
	}

	private void initFemaleListView() {
		lvFemaleAdapter = new ListViewFemaleAdapter(this, lvFemaleData);
		lvFemale = (PullToRefreshListView) findViewById(R.id.frame_listview_person_female);
		AddHeaderToListView(lvFemale);
		lvFemale.setAdapter(lvFemaleAdapter);
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
		AddHeaderToListView(lvMale);
		lvMale.setAdapter(lvMaleAdapter);
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
				loadLvData(1, 0, lvMaleHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.LISTVIEW_DATATYPE_MALE);
			}
		});
	}

	private void initAgeListView() {
		lvAgeAdapter = new ListViewAgeAdapter(this, lvAgeData);
		lvAge = (PullToRefreshListView) findViewById(R.id.frame_listview_age);
		AddHeaderToListView(lvAge);
		lvAge.setAdapter(lvAgeAdapter);

		lvAge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentAge = lvAgeData.get(position - 1);
				slArray[currentSlIndex].currentVisibleScreen++;
				slArray[currentSlIndex]
						.snapToScreen(slArray[currentSlIndex].currentVisibleScreen);
				setSoNavInfo();
				loadLvData(1, 0, lvPurposeHandler,
						UIHelper.LISTVIEW_ACTION_INIT,
						UIHelper.LISTVIEW_DATATYPE_PURPOSE);
				setTopState();
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

	private void initAimView() {
		lvAimAdapter = new ListViewAimAdapter(this, lvAimData);
		lvAim = (PullToRefreshListView) findViewById(R.id.frame_listview_purpose);
		AddHeaderToListView(lvAim);
		lvAim.setAdapter(lvAimAdapter);
		lvAim.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvAim.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码�?
				if (lvAimData.isEmpty())
					return;
				// 判断是否滚动到底�?
				int pageIndex = 1;
				loadLvData(1, pageIndex, lvPurposeHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.LISTVIEW_DATATYPE_PURPOSE);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvAim.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvAim.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
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
		LinearLayout frame_product_header = (LinearLayout) getLayoutInflater()
				.inflate(R.layout.frame_product_header, null);

		txtSoProductPersonName = (TextView) frame_product_header
				.findViewById(R.id.txt_so_product_personname);
		txtSoProductAgeName = (TextView) frame_product_header
				.findViewById(R.id.txt_so_product_agename);
		txtSoProductAimName = (TextView) frame_product_header
				.findViewById(R.id.txt_so_product_purposename);

		lvProduct.addHeaderView(frame_product_header);
		lvProduct_footer = getLayoutInflater().inflate(
				R.layout.listview_footer, null);
		lvProduct_foot_progress = (ProgressBar) lvProduct_footer
				.findViewById(R.id.listview_foot_progress);
		lvProduct.addFooterView(lvProduct_footer);
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
				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvProduct_footer) == view
							.getLastVisiblePosition()) {
						scrollEnd = true;
					}
				} catch (Exception e) {
					scrollEnd = false;
				}

				int lvDataState = StringUtils.toInt(lvProduct.getTag());
				// if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE
				// && lvProductSumData != lvProduct.totalCount) {
				if (scrollEnd) {
					if (lvProductSumData != lvProduct.totalCount) {
						lvProduct.setTag(UIHelper.LISTVIEW_DATA_LOADING);
						lvProduct_foot_progress.setVisibility(View.VISIBLE);
						// 当前pageIndex, pagesize is 10 here.
						int pageIndex = lvProductSumData / 10 + 1;
						loadLvData(currentPerson.Sex, pageIndex,
								lvProductHandler,
								UIHelper.LISTVIEW_ACTION_SCROLL,
								UIHelper.LISTVIEW_DATATYPE_PRODUCT);
					} else
						lvProduct_foot_progress.setVisibility(View.GONE);
				}
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

	private void initFavoriteListView() {
		lvFavoriteAdapter = new ListViewFavoriteAdapter(this,
				lvFavoriteProductData);
		lvFavorite = (PullToRefreshListView) findViewById(R.id.frame_listview_favorite_product);
		frame_favorite_header = (LinearLayout) getLayoutInflater().inflate(
				R.layout.favorite_product_header, null);
		lvFavorite.addHeaderView(frame_favorite_header);
		lvFavorite_footer = getLayoutInflater().inflate(
				R.layout.listview_footer, null);
		lvFavorite_foot_progress = (ProgressBar) lvFavorite_footer
				.findViewById(R.id.listview_foot_progress);
		// lvFavorite.addFooterView(lvFavorite_footer);
		lvFavorite.setAdapter(lvFavoriteAdapter);

		lvFavorite
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO:
					}
				});
		lvFavorite.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvFavorite.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码�?
				if (lvFavoriteProductData.isEmpty())
					return;

				// 判断是否滚动到底�?
				// int pageIndex = 1;
				// loadLvData(1, pageIndex, lvFavoriteProductHandler,
				// UIHelper.LISTVIEW_ACTION_SCROLL,
				// UIHelper.LISTVIEW_DATATYPE_FAVORITE);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvFavorite.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvFavorite
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						loadLvData(1, 0, lvFavoriteProductHandler,
								UIHelper.LISTVIEW_ACTION_REFRESH,
								UIHelper.LISTVIEW_DATATYPE_FAVORITE);
					}
				});
	}

	private void initFrameListViewData() {
		lvPersonHandler = this.getHandler(lvPerson, lvPersonAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvFemaleHandler = this.getHandler(lvFemale, lvFemaleAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvMaleHandler = this.getHandler(lvMale, lvMaleAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvAgeHandler = this.getHandler(lvAge, lvAgeAdapter, lvFemale_foot_more,
				lvFemale_foot_progress, AppContext.PAGE_SIZE);
		lvPurposeHandler = this.getHandler(lvAim, lvAimAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvProductHandler = this.getHandler(lvProduct, lvProductAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvSearchHandler = this.getHandler(lvSearch, searchAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvFavoriteProductHandler = this.getHandler(lvFavorite,
				lvFavoriteAdapter, lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		// Load Person data
		if (lvFemaleData.isEmpty()) {
			loadLvData(0, 0, lvPersonHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_PERSON);

			loadLvData(0, 0, lvFemaleHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_FEMALE);
			loadLvData(1, 0, lvMaleHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_MALE);
		}
	}

	private Handler getHandler(final PullToRefreshListView lv,
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
					// exeption
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					// Should be add message here
					// more.setText(R.string.load_error);
					((AppException) msg.obj).makeToast(Main.this);
				}
				if (adapter.getCount() == 0) {
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					// more.setText(R.string.load_empty);
				}
				// progress.setVisibility(ProgressBar.GONE);
				// mHeadProgress.setVisibility(ProgressBar.GONE);
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

	private void loadLvData(final int catalogid, final int pageIndex,
			final Handler handler, final int action, final int dataType) {
		// mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					switch (dataType) {
					case UIHelper.LISTVIEW_DATATYPE_PERSON:
						MixedPersonList mixedPersonList = appContext
								.getMixedPersonList(isRefresh);
						msg.what = mixedPersonList.getPageSize();
						msg.obj = mixedPersonList;
						break;
					case UIHelper.LISTVIEW_DATATYPE_FEMALE:
					case UIHelper.LISTVIEW_DATATYPE_MALE:
						PersonList personlist = appContext.getPersonList(
								catalogid, isRefresh);
						msg.what = personlist.getPageSize();
						msg.obj = personlist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_AGE:
						AgeList agelist = appContext.getAgeList(catalogid,
								currentPerson.getId(), pageIndex, isRefresh);
						msg.what = agelist.getPageSize();
						msg.obj = agelist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_PURPOSE:
						AimList purposelist = appContext.getAimList(catalogid,
								currentPerson.getId(), currentAge.getId(),
								isRefresh);
						msg.what = purposelist.getPageSize();
						msg.obj = purposelist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_PRODUCT:
						ProductList productlist = appContext.getProductList(
								catalogid, currentPerson.getId(),
								currentAge.getId(), currentAim.getId(),
								productSortFields[currentProductSortIndex],
								pageIndex, isRefresh);
						lvProduct.totalCount = productlist.totalCount;
						msg.what = productlist.getProductCount();
						msg.obj = productlist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_SEARCH:
						SearchItemListWapper searchItemListWapper = appContext
								.getSearchItemList(catalogid, pageIndex,
										isRefresh);
						lvSearch.totalCount = searchItemListWapper.totalCount;
						msg.what = searchItemListWapper.searchItemCount;
						msg.obj = searchItemListWapper;
						break;
					case UIHelper.LISTVIEW_DATATYPE_FAVORITE:
						ProductList favoriteproductlist = appContext
								.getFavoriteList(Utils.readFavoriteFile(),
										pageIndex, isRefresh);
						msg.what = Utils.getFavoriteCount();
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
				handler.sendMessage(msg);
			}
		}.start();
	}

	private Notice handleLvData(int what, Object obj, int objtype,
			int actiontype) {
		Notice notice = null;
		switch (objtype) {
		case UIHelper.LISTVIEW_DATATYPE_PERSON:
			handleLvDataPerson(what, obj, objtype, actiontype);
			break;
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
			handleLvDataAim(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_PRODUCT:
			handleLvDataProduct(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_SEARCH:
			handleLvSearchData(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_FAVORITE:
			handleLvDataFavorite(what, obj, objtype, actiontype);
			break;
		}

		return notice;
	}

	private void handleLvDataPerson(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;
			MixedPersonList mixedpersonlistlist = (MixedPersonList) obj;
			lvFemaleSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvFemaleData.size() > 0) {
					for (MixedPerson mixedperson1 : mixedpersonlistlist
							.getMixedPersonList()) {
						boolean b = false;
						for (MixedPerson mixedperson2 : lvPersonData) {
							if (mixedperson1.getId() == mixedperson2.getId()) {
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
			lvPersonData.clear();
			lvPersonData.addAll(mixedpersonlistlist.getMixedPersonList());

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			MixedPersonList list = (MixedPersonList) obj;
			lvPersonSumData += what;
			if (lvPersonData.size() > 0) {
				for (MixedPerson mixedperson1 : list.getMixedPersonList()) {
					boolean b = false;
					for (MixedPerson mixedperson2 : lvPersonData) {
						if (mixedperson1.getId() == mixedperson2.getId()) {
							b = true;
							break;
						}
					}
					if (!b)
						lvPersonData.add(mixedperson1);
				}
			} else {
				lvPersonData.addAll(list.getMixedPersonList());
			}
			break;
		}
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
			break;
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

			lvProductSumData = lvProductData.size();
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

	private void handleLvDataAim(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			AimList purposelist = (AimList) obj;
			lvAimSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvAimData.size() > 0) {
					for (Aim purpose1 : purposelist.getPurposeList()) {
						boolean b = false;
						for (Aim purpose2 : lvAimData) {
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
			lvAimData.clear();
			lvAimData.addAll(purposelist.getPurposeList());

		case UIHelper.LISTVIEW_ACTION_SCROLL:
			AimList list = (AimList) obj;
			lvAimSumData += what;
			if (lvAimData.size() > 0) {
				for (Aim purpose1 : list.getPurposeList()) {
					boolean b = false;
					for (Aim purpose2 : lvAimData) {
						if (purpose1.getId() == purpose2.getId()) {
							b = true;
							break;
						}
					}
					if (!b)
						lvAimData.add(purpose1);
				}
			} else {
				lvAimData.addAll(list.getPurposeList());
			}
			break;
		}
	}

	private void handleLvSearchData(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;
			SearchItemListWapper searchitemlist = (SearchItemListWapper) obj;
			lvSearchSumData = searchitemlist.searchItemCount;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvSearchData.size() > 0) {
					for (SearchItemWapper searchitem1 : searchitemlist
							.getSearchItemList()) {
						boolean b = false;
						for (SearchItemWapper searchitem2 : lvSearchData) {
							if (searchitem1.getId() == searchitem2.getId()) {
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
			lvSearchData.clear();
			lvSearchData.addAll(searchitemlist.getSearchItemList());
			break;
		case UIHelper.LISTVIEW_ACTION_SCROLL:
			SearchItemListWapper list = (SearchItemListWapper) obj;
			// lvSearchSumData += list.searchItemCount;
			if (lvSearchData.size() > 0) {

				for (SearchItemWapper searchItemWapper1 : list
						.getSearchItemList()) {
					boolean b = false;
					for (SearchItemWapper searchItemWapper2 : lvSearchData) {
						if (searchItemWapper1.getId() == searchItemWapper2
								.getId()) {
							b = true;
							break;
						}
					}
					if (!b)
						lvSearchData.add(searchItemWapper1);
				}
			} else {
				lvSearchData.addAll(list.getSearchItemList());
			}
			lvSearchSumData = lvSearchData.size() * 3;
			break;
		}
	}

	private void handleLvDataFavorite(int what, Object obj, int objtype,
			int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数�?只有刷新动作才会使用�?
			ProductList productlist = (ProductList) obj;
			lvFavoriteSumData = what;
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
			break;
		case UIHelper.LISTVIEW_ACTION_SCROLL:
			ProductList list = (ProductList) obj;
			lvFavoriteSumData += what;
			if (lvProductData.size() > 0) {

				// for (Product purpose1 : list.getProductList()) {
				// boolean b = false;
				// for (Product purpose2 : lvFavoriteProductData) {
				// if (purpose1.getId() == purpose2.getId()) {
				// b = true;
				// break;
				// }
				// }
				// if (!b)
				// lvFavoriteProductData.add(purpose1);
				// }
			} else {
				// lvFavoriteProductData.addAll(list.getProductList());
			}

			break;
		}
	}

	public void clickPersonBtn(View view) {
		if (view.getTag() instanceof Person) {
			Person person = (Person) view.getTag();
			currentPerson = person;
			lvAgeData.clear();
			lvAgeAdapter.notifyDataSetChanged();

			loadLvData(currentPerson.Sex, 0, lvAgeHandler,
					UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_AGE);
			slArray[currentSlIndex].currentVisibleScreen++;
			slArray[currentSlIndex]
					.snapToScreen(slArray[currentSlIndex].currentVisibleScreen);
			setTopState();
		} else if (view.getTag() instanceof Age) {
			currentAge = (Age) view.getTag();
			lvAimData.clear();
			lvAimAdapter.notifyDataSetChanged();
			loadLvData(1, 0, lvPurposeHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_PURPOSE);
			slArray[currentSlIndex].currentVisibleScreen++;
			slArray[currentSlIndex]
					.snapToScreen(slArray[currentSlIndex].currentVisibleScreen);
			setTopState();
		} else if (view.getTag() instanceof Aim) {
			currentAim = (Aim) view.getTag();
			lvProductData.clear();
			lvProductAdapter.notifyDataSetChanged();
			final WaitDialog waitDialog = new WaitDialog(
					R.layout.dialog_loading);
			waitDialog.show(getSupportFragmentManager(), "");
			Handler sleepHandler = new Handler();
			sleepHandler.postDelayed(new Runnable() {
				public void run() {
					waitDialog.dismiss();
					slArray[currentSlIndex].currentVisibleScreen++;
					slArray[currentSlIndex]
							.snapToScreen(slArray[currentSlIndex].currentVisibleScreen);
					setTopState();
					loadLvData(1, 0, lvProductHandler,
							UIHelper.LISTVIEW_ACTION_INIT,
							UIHelper.LISTVIEW_DATATYPE_PRODUCT);

				}
			}, 3000);
		}
	}

	public void clickProductDetails(View view) {
		if (view.getTag() instanceof Product) {
			Product product = (Product) view.getTag();
			loadTaobao(product.Url);
		}
	}

	// So navigation bar
	private void setSoNavInfo() {
		if (currentPerson != null) {
			txtSoAgePersonName.setText(currentPerson.AliasName);
			txtSoAimPersoname.setText(currentPerson.AliasName);
			txtSoProductPersonName.setText(currentPerson.AliasName);
		}
		if (currentAge != null) {
			txtSoAimAgeName.setText(currentAge.Name);
			txtSoProductAgeName.setText(currentAge.Name);
		}
		if (currentAim != null) {
			txtSoProductAimName.setText(currentAim.Name);
		}
	}

	// Top
	private void setTopNavBarVisibility(boolean visibility) {
		if (visibility)
			mainHeaderBar.setVisibility(View.VISIBLE);
		else
			mainHeaderBar.setVisibility(View.GONE);
	}

	private void setTopState() {
		boolean spinnerVisibily = false;
		boolean adviceSubmitVisibily = false;
		String strTitle = "";
		switch (currentSlIndex) {
		case 0:
			switch (slArray[currentSlIndex].currentVisibleScreen) {
			case 0:
				strTitle = getString(R.string.nav_send_person);
				break;
			case 1:
				strTitle = String.format(getString(R.string.nav_send_age),
						currentPerson.AliasName);
				break;
			case 2:
				strTitle = getString(R.string.nav_send_aim);
				break;
			case 3:
				strTitle = getString(R.string.nav_send_product);
				spinnerVisibily = true;
				break;
			}
			setSoNavInfo();
			break;
		case 1:
			strTitle = "";
			break;
		case 2:
			strTitle = String.format(getString(R.string.nav_favorite),
					Utils.getFavoriteCount());
			break;
		case 3:
			switch (slArray[currentSlIndex].currentVisibleScreen) {
			case 0:
				strTitle = getString(R.string.nav_more);
				break;
			default:
				strTitle = getResources().getStringArray(
						R.array.more_info_title)[slArray[currentSlIndex].currentVisibleScreen - 1];
				if (slArray[currentSlIndex].currentVisibleScreen == 5)
					adviceSubmitVisibily = true;
				break;
			}
			break;
		case 4:
			strTitle = getString(R.string.nav_taobao);
			break;
		}
		setTopNavBarVisibility(currentSlIndex != 1);
		if (currentSlIndex != 4
				&& slArray[currentSlIndex].currentVisibleScreen > 0)
			setTopBtnPreVisible(true);
		if (currentSlIndex == 2)
			setTopBtnPreVisible(false);
		if (currentSlIndex == 4)
			setTopBtnPreVisible(true);
		TextView titleView = (TextView) this.findViewById(R.id.navbar_title);
		titleView.setText(strTitle);
		spinner.setVisibility(spinnerVisibily ? View.VISIBLE : View.GONE);
		btnMoreAdviceSubmit.setVisibility(adviceSubmitVisibily ? View.VISIBLE
				: View.GONE);
	}

	private void backUpplerLevel() {
		if (currentSlIndex == 0
				&& slArray[currentSlIndex].currentVisibleScreen == 0) {
			if (shouldExit) {
				AppManager.getAppManager().AppExit(this);
			} else {
				shouldExit = true;
				Toast.makeText(this, getString(R.string.alert_exit),
						Toast.LENGTH_SHORT).show();
			}
			return;
		}

		switch (currentSlIndex) {
		case 0:
			slArray[currentSlIndex].currentVisibleScreen--;
			break;
		case 1:
		case 2:
			currentSlIndex = 0;
			break;
		case 3:
			if (slArray[currentSlIndex].currentVisibleScreen > 0)
				slArray[currentSlIndex].currentVisibleScreen = 0;
			else
				currentSlIndex = 0;
			break;
		case 4:
			currentSlIndex = preTaobaolIndex;
			break;
		}
		slArray[currentSlIndex]
				.snapToScreen(slArray[currentSlIndex].currentVisibleScreen);
		selectScrollLayout(currentSlIndex);
	}

	private View.OnClickListener frameTopNavPreBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				backUpplerLevel();
			}
		};
	}

	private void setTopBtnPreVisible(boolean visibility) {
		if (visibility)
			btnTopNavPre.setVisibility(View.VISIBLE);
		else
			btnTopNavPre.setVisibility(View.GONE);
	}

	// Search
	private void initSearchTabbar() {
		LoadCatalogTask task = new LoadCatalogTask();
		task.execute();
	}

	private void initSearchListView() {
		lvSearch = (PullToRefreshListView) findViewById(R.id.frame_search_listview_product);
		searchAdapter = new SearchAdapter(this, lvSearchData);
		lvSearch_footer = getLayoutInflater().inflate(
				R.layout.listview__search_footer, null);
		lvSearch_foot_progress = (ProgressBar) lvSearch_footer
				.findViewById(R.id.listview_foot_progress);
		lvSearch.addFooterView(lvSearch_footer);
		lvSearch.setAdapter(searchAdapter);

		lvSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvSearch.onScrollStateChanged(view, scrollState);
				if (lvSearchData.isEmpty())
					return;
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvSearch_footer) == view
							.getLastVisiblePosition()) {
						scrollEnd = true;
					}
				} catch (Exception e) {
					scrollEnd = false;
				}

				if (scrollEnd) {
					if (lvSearchSumData != lvSearch.totalCount
							&& lvSearchSumData < lvSearch.totalCount) {
						lvSearch.setTag(UIHelper.LISTVIEW_DATA_LOADING);
						lvSearch_foot_progress.setVisibility(View.VISIBLE);
						// 当前pageIndex, pagesize is 15 here.
						int pageIndex = lvSearchSumData / 15 + 1;
						loadLvData(currentSearchCatalogId, pageIndex,
								lvSearchHandler,
								UIHelper.LISTVIEW_ACTION_SCROLL,
								UIHelper.LISTVIEW_DATATYPE_SEARCH);
					} else
						lvSearch_foot_progress.setVisibility(View.GONE);
				}

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvSearch.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});

		lvSearch.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvData(currentSearchCatalogId, 0, lvSearchHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.LISTVIEW_DATATYPE_SEARCH);
			}
		});

	}

	private void loadSearch(Catalog catalog) {
		currentSearchCatalogId = catalog == null ? 0 : catalog.getId();
		lvSearchData.clear();
		lvSearchSumData = 0;
		searchAdapter.notifyDataSetChanged();
		loadLvData(currentSearchCatalogId, 0, lvSearchHandler,
				UIHelper.LISTVIEW_ACTION_INIT,
				UIHelper.LISTVIEW_DATATYPE_SEARCH);
	}

	private View.OnClickListener frameSearchBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				String url = String.format(
						"http://s8.m.taobao.com/munion/search.htm?q=%s",
						txtSearch.getText());
				loadTaobao(url);
			}
		};
	}

	private View.OnClickListener frameSearchTabBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				for (Button btn : searchTabButtons) {
					btn.setEnabled(btn != v);
				}
				Catalog catalog = (Catalog) v.getTag();
				loadSearch(catalog);
			}
		};
	}

	public void clickSearchItem(View view) {
		if (view.getTag() instanceof SearchItem) {
			SearchItem searchitem = (SearchItem) view.getTag();
			loadTaobao(searchitem.Url);
		}
	}

	private class LoadCatalogTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			try {
				CatalogList cataloglist = appContext.getSearchCatalog();

				searchTabButtons = new ArrayList<Button>();
				for (Catalog catalog : cataloglist.getCatalogList()) {
					Button tempButton = (Button) getLayoutInflater().inflate(
							R.anim.search_tab_button, searchCategoryBar, false);
					tempButton.setText(catalog.Name);
					tempButton.setTag(catalog);

					tempButton.setOnClickListener(frameSearchTabBtnClick());
					searchTabButtons.add(tempButton);
					searchCategoryBar.addView(tempButton);
				}

			} catch (Exception e) {

			}
			if (searchCategoryBar.getChildCount() > 0)
				searchCategoryBar.getChildAt(0).setEnabled(false);
			return null;

		}

		@Override
		protected void onPostExecute(String result) {

		}
	}

	// Favorite
	private void loadFavorite() {
		if (Utils.getFavoriteCount() == 0) {
			slArray[2].currentVisibleScreen = 0;
			slArray[2].setToScreen(0);
		} else {
			slArray[2].currentVisibleScreen = 1;
			slArray[2].setToScreen(1);
			if (lvFavoriteProductData.isEmpty())
				loadLvData(1, 0, lvFavoriteProductHandler,
						UIHelper.LISTVIEW_ACTION_INIT,
						UIHelper.LISTVIEW_DATATYPE_FAVORITE);
		}
	}

	public void findNew(View view) {
		currentSlIndex = 0;
		slArray[currentSlIndex].currentVisibleScreen = 0;
		slArray[currentSlIndex].setToScreen(0);
		selectScrollLayout(0);
	}

	public void deleteProductFromFavorite(View view) {
		final int location = (Integer) view.getTag();
		final int product_id = lvFavoriteProductData.get(location).getId();
		AlertDialog.Builder adb = new AlertDialog.Builder(Main.this);
		adb.setTitle("删除?");
		adb.setMessage("确定要删除该商品吗? ");
		adb.setNegativeButton("取消", null);
		adb.setPositiveButton("删除", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Utils.deleteFavorite(product_id);
				lvFavoriteProductData.remove(location);
				lvFavoriteAdapter.notifyDataSetChanged();
				setTopState();
			}
		});
		adb.show();
	}

	public void addFavoriteProduct(View view) {
		if (view.getTag() instanceof Product) {
			Product product = (Product) view.getTag();
			Utils.addFavorite(product.getId());
			View v = (View) view.getParent();
			v.findViewById(R.id.btn_favorite).setVisibility(View.VISIBLE);
		}
	}

	public void deleteFavoriteProduct(View view) {
		if (view.getTag() instanceof Product) {
			Product product = (Product) view.getTag();
			Utils.deleteFavorite(product.getId());
			View v = (View) view.getParent();
			v.findViewById(R.id.btn_favorite).setVisibility(View.GONE);
		}
	}

	// More
	private View.OnClickListener frameMoreBtnClick(final int item_index) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				if (item_index < 4) {
					TextView myTextView = (TextView) findViewById(R.id.txtMoreInfo);
					myTextView.setText(Html.fromHtml(getResources()
							.getStringArray(R.array.more_info)[item_index]));
					slArray[currentSlIndex].snapToScreen(1);
				} else if (item_index == 4) {
					// Advice
					slArray[3].snapToScreen(2);
				} else if (item_index == 5) {
					// Check version
					CustomDialog m = new CustomDialog(
							(getString(R.string.dialog_version)));
					m.show(getSupportFragmentManager(), "");
					return;
				}

				slArray[currentSlIndex].currentVisibleScreen = item_index + 1;
				setTopState();
			}
		};
	}

	private View.OnClickListener frameMoreAdviceBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {

				String email = ((EditText) findViewById(R.id.editEmail))
						.getText().toString().trim();
				String content = ((EditText) findViewById(R.id.editAdvice))
						.getText().toString().trim();
				if (content.length() < 1) {
					showAlertDialog(getString(R.string.dialog_content));
					return;
				}
				if (email.length() < 1) {
					showAlertDialog(getString(R.string.dialog_email));
					return;
				}

				appContext.addAdvice(email, content);
				final CustomDialog m = new CustomDialog(
						(getString(R.string.dialog_submit)));
				m.show(getSupportFragmentManager(), "");
				Handler sleepHandler = new Handler();
				sleepHandler.postDelayed(new Runnable() {
					public void run() {
						m.dismiss();
						((EditText) findViewById(R.id.editEmail)).setText("");
						((EditText) findViewById(R.id.editAdvice)).setText("");
					}
				}, 3000);

			}
		};
	}

	// TaoBao
	private void initTaobaoView() {
		taoBaoProgress = (ProgressBar) findViewById(R.id.taobao_progress);
		taoBaoContainer = (LinearLayout) findViewById(R.id.taoBaoContainer);

		taoBaoWebView = (WebView) findViewById(R.id.taobao_webview);
		taoBaoWebView.setVerticalScrollBarEnabled(true);
		taoBaoWebView.setInitialScale(1);
		taoBaoWebView.getSettings().setJavaScriptEnabled(true);
		taoBaoWebView.getSettings().setLoadWithOverviewMode(true);
		taoBaoWebView.getSettings().setUseWideViewPort(true);
		taoBaoWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		taoBaoWebView.setScrollbarFadingEnabled(false);
		taoBaoWebView.setWebViewClient(new WebViewClient() {
			// Load opened URL in the application instead of standard browser
			// application
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		taoBaoWebView.setWebChromeClient(new WebChromeClient() {
			// Set progress bar during loading
			public void onProgressChanged(WebView view, int progress) {
				if (progress < 100 && !taoBaoProgress.isShown()) {
					taoBaoProgress.setVisibility(View.VISIBLE);
				} else if (progress == 100) {
					taoBaoProgress.setVisibility(View.GONE);
				}
			}
		});
	}

	private void loadTaobao(String url) {
		try {
			url = URLDecoder.decode(url, "UTF-8");
			taoBaoWebView.loadUrl(url);
			selectScrollLayout(4);
		} catch (Exception e) {
		}
	}

	// Footer
	private void selectScrollLayout(final int itemIndex) {
		// To show taobao view
		taoBaoContainer
				.setVisibility(itemIndex == 4 ? View.VISIBLE : View.GONE);
		taoBaoProgress.setVisibility(View.GONE);

		footer.setVisibility(itemIndex == 4 ? View.GONE : View.VISIBLE);
		preTaobaolIndex = currentSlIndex;
		currentSlIndex = itemIndex;
		for (int i = 0; i < slArray.length; i++) {
			// footer bars
			if (i < footBtnArray.length)
				footBtnArray[i].setEnabled(i != currentSlIndex);
			// scroll layout
			slArray[i].setVisibility(i == itemIndex ? View.VISIBLE : View.GONE);
		}

		setTopState();

		switch (itemIndex) {
		case 0:
			break;
		case 1:
			loadSearch(null);
			break;
		case 2:
			loadFavorite();
			break;
		default:
			break;
		}

		// Set nav pre button
		if (currentSlIndex == 4)
			btnTopNavPre.setVisibility(View.VISIBLE);
		else if (slArray[currentSlIndex].currentVisibleScreen > 0)
			btnTopNavPre.setVisibility(View.VISIBLE);
		else
			btnTopNavPre.setVisibility(View.GONE);

	}

	private View.OnClickListener selectFootBar(final int itemIndex) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				selectScrollLayout(itemIndex);
			}
		};
	}

	// Application
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backUpplerLevel();
		}
		return true;
	}

	private void checkNetwork() {
		if (!appContext.isNetworkConnected()) {
			Toast.makeText(this, getString(R.string.no_internet_access),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		SortItem sortItem = (SortItem) parent.getItemAtPosition(pos);
		switch (sortItem.getTypeId()) {
		case 0:
			currentProductSortIndex = 0;
			break;
		case 1:
			currentProductSortIndex = 1;
			break;
		case 2:
			currentProductSortIndex = 2;
			break;

		}
		loadLvData(1, 0, lvProductHandler, UIHelper.LISTVIEW_ACTION_INIT,
				UIHelper.LISTVIEW_DATATYPE_PRODUCT);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}

	private void AddHeaderToListView(PullToRefreshListView listview) {
		LinearLayout listview_header = (LinearLayout) getLayoutInflater()
				.inflate(R.layout.listview_header, null);
		listview.addHeaderView(listview_header);
	}

	private void showAlertDialog(String text) {
		CustomDialog m = new CustomDialog((text));
		m.show(getSupportFragmentManager(), "");
	}

}
