package com.liwuso.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liwuso.app.AppContext;
import com.liwuso.app.R;

public class More extends FragmentActivity {

	private AppContext appContext;

	private LinearLayout infoLayout;
	private RelativeLayout adviceLayout;
	private Button btnTopNavPre;
	private Button btnMoreAdviceSubmit;
	private TextView txtTitleView;
	private TextView txtContentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		appContext = (AppContext) getApplication();
		initView();

		Intent intent = getIntent();
		int item_index = intent.getIntExtra("itemIndex", 0);
		String title = getResources().getStringArray(R.array.more_info_title)[item_index];

		txtTitleView.setText(title);
		if (item_index < 3) {
			String content = getResources().getStringArray(R.array.more_info)[item_index];
			txtContentView.setText(Html.fromHtml(content));
			showInfo(true);
		} else if (item_index == 3) {
			showInfo(false);
		}
	}

	private void initView() {
		txtTitleView = (TextView) findViewById(R.id.txtTitle);
		txtContentView = (TextView) findViewById(R.id.txtMoreInfo);
		infoLayout = (LinearLayout) findViewById(R.id.more_info);
		adviceLayout = (RelativeLayout) findViewById(R.id.more_advice);
		btnTopNavPre = (Button) findViewById(R.id.btnTopNavPre);
		btnTopNavPre.setOnClickListener(btnPreClick());
		btnMoreAdviceSubmit = (Button) findViewById(R.id.btn_more_advice_submit);
		btnMoreAdviceSubmit.setOnClickListener(frameMoreAdviceBtnClick());
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

	private View.OnClickListener btnPreClick() {
		return new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
	}

	private void showAlertDialog(String text) {
		CustomDialog m = new CustomDialog((text));
		m.show(getSupportFragmentManager(), "");
	}

	private void showInfo(Boolean status) {
		infoLayout.setVisibility(status == true ? View.VISIBLE : View.GONE);
		adviceLayout.setVisibility(status == true ? View.GONE : View.VISIBLE);
	}

}
