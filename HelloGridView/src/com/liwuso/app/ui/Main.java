package com.liwuso.app.ui;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.liwuso.app.AppContext;
import com.liwuso.app.AppException;
import com.liwuso.app.AppManager;
import com.liwuso.app.R;
import com.liwuso.app.adapter.GridViewSearchAdapter;
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
import com.liwuso.app.widget.PullToRefreshGridView;
import com.liwuso.app.widget.PullToRefreshListView;
import com.liwuso.app.widget.ScrollLayout;
import com.liwuso.utility.Utils;
import com.pys.liwuso.bean.Age;
import com.pys.liwuso.bean.AgeList;
import com.pys.liwuso.bean.AimList;
import com.pys.liwuso.bean.MixedPerson;
import com.pys.liwuso.bean.MixedPersonList;
import com.pys.liwuso.bean.Notice;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;
import com.pys.liwuso.bean.Product;
import com.pys.liwuso.bean.ProductList;
import com.pys.liwuso.bean.Aim;
import com.pys.liwuso.bean.SearchCatalog;
import com.pys.liwuso.bean.SearchItem;
import com.pys.liwuso.bean.SearchItemList;

public class Main extends BaseActivity implements OnItemSelectedListener {

	private boolean shouldExit = false;
	private Person currentPerson = null;
	private Age currentAge = null;
	private Aim currentAim = null;

	private TextView txtSoAgePersonName;
	private TextView txtSoPurposePersoname;
	private TextView txtSoPurposeAgeName;
	private TextView txtSoProductPersonName;
	private TextView txtSoProductAgeName;
	private TextView txtSoProductPurposeName;

	private RelativeLayout mainHeaderBar;
	private int[] slResourceArray = { R.id.main_scrolllayout_so,
			R.id.main_scrolllayout_search, R.id.main_scrolllayout_favorite,
			R.id.main_scrolllayout_more, R.id.main_scrolllayout_taobao };

	private ScrollLayout personScrollLayout;

	private ScrollLayout[] slArray = new ScrollLayout[5];
	private int currentSlIndex = 0;
	private int preTaobaolIndex = 0;

	private View lvProduct_footer;
	private View lvFavorite_footer;

	private ProgressBar lvProduct_foot_progress;
	private ProgressBar lvFavorite_foot_progress;

	// private ProgressBar mHeadProgress;
	private ProgressBar lvFemale_foot_progress;
	private ProgressBar lvMale_foot_progress;

	private PullToRefreshListView lvPerson;
	private PullToRefreshListView lvFemale;
	private PullToRefreshListView lvMale;
	private PullToRefreshListView lvAge;
	private PullToRefreshListView lvPurpose;
	private PullToRefreshListView lvProduct;
	private PullToRefreshListView lvFavorite;

	private Handler lvPersonHandler;
	private Handler lvFemaleHandler;
	private Handler lvMaleHandler;
	private Handler lvAgeHandler;
	private Handler lvPurposeHandler;
	private Handler lvProductHandler;
	private Handler gvSearchHandler;
	private Handler lvFavoriteProductHandler;

	private ListViewPersonAdapter lvPersonAdapter;
	private ListViewFemaleAdapter lvFemaleAdapter;
	private ListViewMaleAdapter lvMaleAdapter;
	private ListViewAgeAdapter lvAgeAdapter;
	private ListViewAimAdapter lvPurposeAdapter;
	private ListViewProductAdapter lvProductAdapter;
	private GridViewSearchAdapter gvSearchAdapter;
	private ListViewFavoriteAdapter lvFavoriteAdapter;

	private List<MixedPerson> lvPersonData = new ArrayList<MixedPerson>();
	private List<Person> lvFemaleData = new ArrayList<Person>();
	private List<Person> lvMaleData = new ArrayList<Person>();
	private List<Age> lvAgeData = new ArrayList<Age>();
	private List<Aim> lvPurposeData = new ArrayList<Aim>();
	private List<Product> lvProductData = new ArrayList<Product>();
	private List<SearchItem> gvSearchData = new ArrayList<SearchItem>();
	private List<Product> lvFavoriteProductData = new ArrayList<Product>();

	private int lvPersonSumData;
	private int lvFemaleSumData;
	private int lvMaleSumData;
	private int lvAgeSumData;
	private int lvPurposeSumData;
	private int lvProductSumData;
	private int gvSearchSumData;
	private int lvFavoriteProductSumData;

	private TextView lvFemale_foot_more;

	// So person
	LinearLayout frame_layout_female;
	LinearLayout frame_layout_male;

	private Button framebtn_All;
	private Button framebtn_Female;
	private Button framebtn_Male;

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

	// Search
	private Button btnSearch;
	private EditText txtSearch;
	private PullToRefreshGridView gvSearch;

	private Button btnMoreAdviceSubmit;
	private Button btnMoreAdviceQuersion;

	private WebView webView;
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
	}

	@Override
	protected void onResume() {
		super.onResume();
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
		txtSoPurposePersoname = (TextView) findViewById(R.id.txt_so_purpose_personname);
		txtSoPurposeAgeName = (TextView) findViewById(R.id.txt_so_purpose_agename);
		txtSoProductPersonName = (TextView) findViewById(R.id.txt_so_product_personname);
		txtSoProductAgeName = (TextView) findViewById(R.id.txt_so_product_agename);
		txtSoProductPurposeName = (TextView) findViewById(R.id.txt_so_product_purposename);

		// Search
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(frameSearchBtnClick());
		txtSearch = (EditText) findViewById(R.id.editSearchText);
		searchCategoryBar = (LinearLayout) findViewById(R.id.search_category_bar);

		try {
			List<SearchCatalog> listSearchCatalog = appContext
					.getSearchCatalog();
			searchTabButtons = new ArrayList<Button>();

			for (SearchCatalog catalog : listSearchCatalog) {

				Button tempButton = (Button) getLayoutInflater().inflate(
						R.anim.search_tab_button, searchCategoryBar, false);
				tempButton.setText(catalog.Name);
				tempButton.setTag(catalog);

				tempButton.setOnClickListener(frameSearchTabBtnClick());
				searchTabButtons.add(tempButton);
				// searchTabButtons[i] = tempButton;

				searchCategoryBar.addView(tempButton);

			}

		} catch (Exception e) {

		}
		if (searchCategoryBar.getChildCount() > 0)
			searchCategoryBar.getChildAt(0).setEnabled(false);

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

		// Taobao
		webView = (WebView) findViewById(R.id.webview);
		webView.setWebViewClient(new WebViewClient() {
			// Load opened URL in the application instead of standard browser
			// application
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			// Set progress bar during loading
			public void onProgressChanged(WebView view, int progress) {
				// BrowserActivity.this.setProgress(progress * 100);
			}
		});

		// Enable some feature like Javascript and pinch zoom
		WebSettings websettings = webView.getSettings();
		websettings.setJavaScriptEnabled(true); // Warning! You can have XSS
												// vulnerabilities!
		websettings.setBuiltInZoomControls(true);

	}

	private View.OnClickListener framePersonBtnClick(final Button btn,
			final int catalog) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				framebtn_All.setEnabled(framebtn_All != btn);
				framebtn_Female.setEnabled(framebtn_Female != btn);
				framebtn_Male.setEnabled(framebtn_Male != btn);
				if (btn == framebtn_All) {

					personScrollLayout.scrollToScreen(0);

				} else if (btn == framebtn_Female) {

					personScrollLayout.scrollToScreen(1);
				} else if (btn == framebtn_Male) {

					personScrollLayout.scrollToScreen(2);
				}
			}
		};
	}

	private void initFrameListView() {
		this.initPersonListView();
		this.initFemaleListView();
		this.initMaleListView();
		this.initAgeListView();
		this.initPurposeView();
		this.initProductListView();
		this.initSearchGridView();
		this.initFavoriteListView();
		this.initFrameListViewData();
	}

	private void initPersonListView() {
		lvPersonAdapter = new ListViewPersonAdapter(this, lvPersonData);
		lvPerson = (PullToRefreshListView) findViewById(R.id.frame_listview_person);
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

	private void initPurposeView() {
		lvPurposeAdapter = new ListViewAimAdapter(this, lvPurposeData);
		lvPurpose = (PullToRefreshListView) findViewById(R.id.frame_listview_purpose);
		lvPurpose.setAdapter(lvPurposeAdapter);
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
		lvPurposeHandler = this.getHandler(lvPurpose, lvPurposeAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		lvProductHandler = this.getHandler(lvProduct, lvProductAdapter,
				lvFemale_foot_more, lvFemale_foot_progress,
				AppContext.PAGE_SIZE);

		gvSearchHandler = this.getGvHandler(gvSearch, gvSearchAdapter,
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

	private Handler getGvHandler(final PullToRefreshGridView lv,
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

	private void loadLvData(final int sexId, final int pageIndex,
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
						PersonList personlist = appContext.getPersonList(sexId,
								isRefresh);
						msg.what = personlist.getPageSize();
						msg.obj = personlist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_AGE:
						AgeList agelist = appContext.getAgeList(sexId,
								currentPerson.getId(), pageIndex, isRefresh);
						msg.what = agelist.getPageSize();
						msg.obj = agelist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_PURPOSE:
						AimList purposelist = appContext.getAimList(sexId,
								currentPerson.getId(), currentAge.getId(),
								isRefresh);
						msg.what = purposelist.getPageSize();
						msg.obj = purposelist;
						break;
					case UIHelper.LISTVIEW_DATATYPE_PRODUCT:
						ProductList productlist = appContext.getProductList(
								sexId, currentPerson.getId(),
								currentAge.getId(), currentAim.getId(),
								pageIndex, isRefresh);
						lvProduct.totalCount = productlist.totalCount;
						msg.what = productlist.getProductCount();
						msg.obj = productlist;
						break;
					case UIHelper.GRIDVIEW_DATATYPE_SEARCH:
						SearchItemList searchItemList = appContext
								.getSearchItemList(0, 0, isRefresh);
						gvSearch.totalCount = searchItemList.totalCount;
						msg.what = searchItemList.getProductCount();
						msg.obj = searchItemList;
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
			handleLvDataPurpose(what, obj, objtype, actiontype);
			break;
		case UIHelper.LISTVIEW_DATATYPE_PRODUCT:
			handleLvDataProduct(what, obj, objtype, actiontype);
			break;
		case UIHelper.GRIDVIEW_DATATYPE_SEARCH:
			handleGvData(what, obj, objtype, actiontype);
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
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
			// ///////////////////////////////////////////////////////////////////////////////////
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
			AimList purposelist = (AimList) obj;
			lvPurposeSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (lvPurposeData.size() > 0) {
					for (Aim purpose1 : purposelist.getPurposeList()) {
						boolean b = false;
						for (Aim purpose2 : lvPurposeData) {
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
			AimList list = (AimList) obj;
			lvPurposeSumData += what;
			if (lvPurposeData.size() > 0) {
				for (Aim purpose1 : list.getPurposeList()) {
					boolean b = false;
					for (Aim purpose2 : lvPurposeData) {
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

	private void handleGvData(int what, Object obj, int objtype, int actiontype) {
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;
			SearchItemList searchitemlist = (SearchItemList) obj;
			gvSearchSumData = what;
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				if (gvSearchData.size() > 0) {
					for (SearchItem searchitem1 : searchitemlist
							.getSearchItemList()) {
						boolean b = false;
						for (SearchItem searchitem2 : gvSearchData) {
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
			gvSearchData.clear();
			gvSearchData.addAll(searchitemlist.getSearchItemList());
			break;
		case UIHelper.LISTVIEW_ACTION_SCROLL:
			SearchItemList list = (SearchItemList) obj;
			gvSearchSumData += what;
			if (gvSearchData.size() > 0) {

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

	private void handleLvDataFavorite(int what, Object obj, int objtype,
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
			break;
		case UIHelper.LISTVIEW_ACTION_SCROLL:
			ProductList list = (ProductList) obj;
			lvFavoriteProductSumData += what;
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
			loadLvData(currentPerson.Sex, 0, lvAgeHandler,
					UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_AGE);
			slArray[currentSlIndex].currentVisibleScreen++;
			slArray[currentSlIndex]
					.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
			setTopState();
		} else if (view.getTag() instanceof Age) {
			currentAge = (Age) view.getTag();
			loadLvData(1, 0, lvPurposeHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.LISTVIEW_DATATYPE_PURPOSE);
			slArray[currentSlIndex].currentVisibleScreen++;
			slArray[currentSlIndex]
					.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
			setTopState();
		} else if (view.getTag() instanceof Aim) {
			currentAim = (Aim) view.getTag();
			final WaitDialog waitDialog = new WaitDialog();
			waitDialog.show(getSupportFragmentManager(), "");
			Handler sleepHandler = new Handler();

			sleepHandler.postDelayed(new Runnable() {
				public void run() {
					waitDialog.dismiss();
					slArray[currentSlIndex].currentVisibleScreen++;
					slArray[currentSlIndex]
							.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
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
			txtSoAgePersonName.setText(currentPerson.Name);
			txtSoPurposePersoname.setText(currentPerson.Name);
			txtSoProductPersonName.setText(currentPerson.Name);
		}
		if (currentAge != null) {
			txtSoPurposeAgeName.setText(currentAge.Name);
			txtSoProductAgeName.setText(currentAge.Name);
		}
		if (currentAim != null) {
			txtSoProductPurposeName.setText(currentAim.Name);
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
		String strTitle = "";
		switch (currentSlIndex) {
		case 0:
			switch (slArray[currentSlIndex].currentVisibleScreen) {
			case 0:
				strTitle = getString(R.string.nav_send_person);
				break;
			case 1:
				strTitle = String.format(getString(R.string.nav_send_age),
						currentPerson.Name);

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
				break;
			}
			break;
		case 4:
			strTitle = getString(R.string.nav_taobao);
			break;
		}
		setTopNavBarVisibility(currentSlIndex != 1);
		if (slArray[currentSlIndex].currentVisibleScreen > 0)
			setTopBtnPreVisible(true);
		if (currentSlIndex == 2)
			setTopBtnPreVisible(false);
		if (currentSlIndex == 4)
			setTopBtnPreVisible(true);
		TextView titleView = (TextView) this.findViewById(R.id.navbar_title);
		titleView.setText(strTitle);
		spinner.setVisibility(spinnerVisibily ? View.VISIBLE : View.GONE);
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
				.scrollToScreen(slArray[currentSlIndex].currentVisibleScreen);
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
	private void initSearchGridView() {
		gvSearchAdapter = new GridViewSearchAdapter(this, gvSearchData);
		gvSearch = (PullToRefreshGridView) findViewById(R.id.frame_search_gridview_product);
		gvSearch.setAdapter(gvSearchAdapter);

		gvSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				gvSearch.onScrollStateChanged(view, scrollState);
				if (gvSearchData.isEmpty())
					return;

				int pageIndex = 1;
				loadLvData(0, pageIndex, gvSearchHandler,
						UIHelper.LISTVIEW_ACTION_SCROLL,
						UIHelper.GRIDVIEW_DATATYPE_SEARCH);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				gvSearch.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});

		gvSearch.setOnRefreshListener(new PullToRefreshGridView.OnRefreshListener() {
			public void onRefresh() {
				loadLvData(0, 0, gvSearchHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH,
						UIHelper.GRIDVIEW_DATATYPE_SEARCH);
			}
		});

		// this.loadSearchProduct();
	}

	private void loadSearch(SearchCatalog catalog) {
		if (gvSearchData.isEmpty()) {
			loadLvData(catalog == null ? 0 : catalog.getId(), 0,
					gvSearchHandler, UIHelper.LISTVIEW_ACTION_INIT,
					UIHelper.GRIDVIEW_DATATYPE_SEARCH);
		}
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
				SearchCatalog catalog = (SearchCatalog) v.getTag();
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
			v.findViewById(R.id.btn_no_favorite).setVisibility(View.GONE);
			v.findViewById(R.id.btn_favorite).setVisibility(View.VISIBLE);
		}
	}

	public void deleteFavoriteProduct(View view) {
		if (view.getTag() instanceof Product) {
			Product product = (Product) view.getTag();
			Utils.deleteFavorite(product.getId());
			View v = (View) view.getParent();
			v.findViewById(R.id.btn_no_favorite).setVisibility(View.VISIBLE);
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
				setTopState();
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

	// Taobao
	private void loadTaobao(String url) {
		try {
			url = URLDecoder.decode(url, "UTF-8");
			webView.loadUrl(url);
			selectScrollLayout(4);
		} catch (Exception e) {
		}
	}

	// Footer
	private void selectScrollLayout(final int itemIndex) {
		if (itemIndex == 4)
			preTaobaolIndex = currentSlIndex;
		currentSlIndex = itemIndex;
		for (int i = 0; i < slArray.length; i++) {
			// footer bars
			if (i < footBtnArray.length)
				footBtnArray[i].setEnabled(i != currentSlIndex);
			// scroll layout
			if (i == itemIndex)
				slArray[i].setVisibility(View.VISIBLE);
			else
				slArray[i].setVisibility(View.GONE);
		}
		switch (itemIndex) {
		case 0:
			break;
		case 1:
			loadSearch(null);
			break;
		case 2:
			loadFavorite();
			break;
		case 3:
			break;
		case 4:
			break;
		}

		// Set nav pre button
		if (slArray[currentSlIndex].currentVisibleScreen > 0
				|| currentSlIndex == 4)
			btnTopNavPre.setVisibility(View.VISIBLE);
		else
			btnTopNavPre.setVisibility(View.GONE);
		setTopState();
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
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}

}
