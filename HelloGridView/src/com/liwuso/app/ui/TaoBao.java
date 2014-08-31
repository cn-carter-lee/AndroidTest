package com.liwuso.app.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.liwuso.app.R;
import com.liwuso.utility.Utils;

public class TaoBao extends BaseActivity {

	private Button btnTopNavPre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taobao_details);

		btnTopNavPre = (Button) findViewById(R.id.btnTopNavPre);

		btnTopNavPre.setOnClickListener(frameTopNavPreBtnClick());

		WebView webView = (WebView) findViewById(R.id.webview);
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
		webView.loadUrl(Utils.PRODUCT_URL);
	}

	private View.OnClickListener frameTopNavPreBtnClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
	}
}