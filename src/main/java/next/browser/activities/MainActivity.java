package next.browser.activities;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.annotation.SuppressLint;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import next.browser.*;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.graphics.*;
import next.browser.db.*;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	Button prv, nxt, srch, mnu;
	EditText eturl;
	WebView wv;
	ActionBar ab;
	LinearLayout abar, parenT;
	DatabaseHelper dbh;
	int sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		ab = getSupportActionBar();
		ab.hide();

		dbh = new DatabaseHelper(this);
		prv = findViewById(R.id.backB);
		nxt = findViewById(R.id.nextB);
		srch = findViewById(R.id.searchBtn);
		mnu = findViewById(R.id.menuBtn);
		eturl = findViewById(R.id.searchEdit);
		wv = findViewById(R.id.webviu);
		abar = findViewById(R.id.actionBar);
		parenT = findViewById(R.id.mainLayout);

		prv.setOnClickListener(this);
		nxt.setOnClickListener(this);
		srch.setOnClickListener(this);
		mnu.setOnClickListener(this);
		
		wv.setDrawingCacheEnabled(true);

		WebSettings ws = wv.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setDomStorageEnabled(true);
		ws.setAppCacheEnabled(true);
		ws.setAllowContentAccess(true);
		ws.setAllowFileAccess(true);
		ws.setBuiltInZoomControls(true);
		ws.setDisplayZoomControls(false);

		registerForContextMenu(wv);

		wv.loadUrl("https://www.google.com");
		wv.setWebChromeClient(new WebChromeClient(){
				public boolean onJsAlert(WebView view, String url, String message, JsResult result)
				{
					mDialog(view.getTitle(), message);
					return true;
				}
			});
		wv.setWebViewClient(new WebViewClient(){
				public void onPageFinished(WebView view, String url)
				{
					eturl.setText(url);
					String title = view.getTitle();
					String name = wv.getUrl();
					dbh.insertNew(name, url, title);
				}
				public void onPageStarted(WebView view, String url)
				{
					eturl.setText("Loading...");
				}
			});
    }
	public void mDialog(String title, String message)
	{
		final AlertDialog.Builder dlg = new AlertDialog.Builder(this);
		dlg.setTitle(title);
		dlg.setMessage(message);
		dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					p1.dismiss();
				}
			});
		dlg.setNegativeButton("cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					p1.dismiss();
				}
			});
		dlg.create().show();
	}
	public void mToast(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
	@Override
	public void onClick(View p1)
	{
		switch (p1.getId())
		{
			case R.id.searchBtn:
				String url = eturl.getText().toString();
				wv.loadUrl(url);
				break;
			case R.id.nextB:
				if (wv.canGoForward())
				{
					wv.goForward();
				}
				else
				{
					String nm = wv.getTitle();
					mToast(nm + "is last page");
				}
				break;
			case R.id.backB:
				if (wv.canGoBack())
				{
					wv.goBack();
				}
				else
				{
					mToast("no more pages!");
				}
				break;
			case R.id.menuBtn:
				PopupMenu popup = new PopupMenu(MainActivity.this, abar);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_settings, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						public boolean onMenuItemClick(MenuItem item)
						{
							switch (item.getItemId())
							{
								case R.id.menu_font:
								case R.id.menu_downloads:
									Intent i = new Intent(MainActivity.this, DownloadsActivity.class);
									startActivity(i);
									break;
								case R.id.menu_history:
									Intent in = new Intent(MainActivity.this, HistoryActivity.class);
									startActivity(in);
									break;
								case R.id.menu_settings:
									Intent inte = new Intent(MainActivity.this, SettingsActivity.class);
									startActivity(inte);
									break;
								case R.id.menu_source:
									Intent si = new Intent(MainActivity.this, SourceActivity.class);
									String sourcer = eturl.getText().toString().trim();
									si.putExtra("sourcer", sourcer);
									startActivity(si);
									finish();
									break;
								case R.id.menu_info:
									try
									{
										String title = wv.getTitle();
										String cert = wv.getCertificate().toString();
										String source = eturl.getText().toString();
										mDialog(title, "Certificate: " + cert + "\n" + "Source:" + source + "\n" + "Secure");
									}
									catch (Exception e)
									{
										mDialog(wv.getTitle(), e.toString());
										mToast("failed to generate info \u0040 !");
									}
									break;
								case R.id.menu_save:
									mToast(wv.getTitle() + ".khtml " + "Is downloading...");

									break;
							}
							return true;
						}
					});

                popup.show();//showing popup me
				break;
		}
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{

        if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
			if (wv.canGoBack())
			{
				wv.goBack();
			}
			else
			{
				finish();
			}

			return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_MENU))
		{
            PopupMenu popup = new PopupMenu(MainActivity.this, parenT);
			//Inflating the Popup using xml file
			popup.getMenuInflater().inflate(R.menu.menu_settings, popup.getMenu());
			//registering popup with OnMenuItemClickListener
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item)
					{
						switch (item.getItemId())
						{
							case R.id.menu_downloads:
								Intent i = new Intent(MainActivity.this, DownloadsActivity.class);
								startActivity(i);
								break;
							case R.id.menu_history:
								Intent in = new Intent(MainActivity.this, HistoryActivity.class);
								startActivity(in);
								break;
							case R.id.menu_settings:
								Intent inte = new Intent(MainActivity.this, SettingsActivity.class);
								startActivity(inte);
								break;
							case R.id.menu_info:
								try
								{
									String title = wv.getTitle();
									String cert = wv.getCertificate().toString();
									String source = eturl.getText().toString();
									mDialog(title, "Certificate: " + cert + "\n" + "Source:" + source + "\n" + "Secure");
								}
								catch (Exception e)
								{
									mDialog(wv.getTitle(), e.toString());
									mToast("failed to generate info \u0040 !");
								}
								break;
							case R.id.menu_save:
								mToast(wv.getTitle() + "is Downloading!");
								break;
						}
						return true;
					}
				});

			popup.show();
            return true;
        }
        return false;
    }
	@Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
	{
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);

        final WebView.HitTestResult webViewHitTestResult = wv.getHitTestResult();

		final WallpaperManager wallman = WallpaperManager.getInstance(this);

        if (webViewHitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
			webViewHitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE)
		{

            contextMenu.setHeaderTitle(webViewHitTestResult.getExtra() + wv.getTitle());
            contextMenu.setHeaderIcon(R.drawable.download);

			contextMenu.add(0, 1, 0, "Set as wallpaper");
            contextMenu.add(0, 2, 0, "Download")
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem menuItem)
					{
						switch (menuItem.getTitle().toString())
						{
							case "Download":
								String DownloadImageURL = webViewHitTestResult.getExtra();

								if (URLUtil.isValidUrl(DownloadImageURL))
								{

									DownloadManager.Request mRequest = new DownloadManager.Request(Uri.parse(DownloadImageURL));
									mRequest.allowScanningByMediaScanner();
									mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
									DownloadManager mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
									mDownloadManager.enqueue(mRequest);

									Toast.makeText(MainActivity.this, "Image Downloaded Successfully...", Toast.LENGTH_LONG).show();
								}
								else
								{
									Toast.makeText(MainActivity.this, "Sorry.. Something Went Wrong...", Toast.LENGTH_LONG).show();
								}
								break;
								case "Set as wallpaper":
									try{
									Bitmap bmp = wv.getDrawingCache();
									wallman.setBitmap(bmp);
									}catch(Exception e){
										
									}
								break;
						}
						return false;
					}
				});
        }
		else if (webViewHitTestResult.getType() == WebView.HitTestResult.ANCHOR_TYPE ||
				 webViewHitTestResult.getType() == WebView.HitTestResult.SRC_ANCHOR_TYPE)
		{

            contextMenu.setHeaderTitle(webViewHitTestResult.getExtra() + wv.getTitle());
            contextMenu.setHeaderIcon(R.drawable.link_dnld);

            contextMenu.add(0, 1, 0, "Visit");
			contextMenu.add(0, 2, 0, "Save link");
			contextMenu.add(0, 3, 0, "Share ");
			contextMenu.add(0, 4, 0, "Copy link")
				.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

					@SuppressWarnings("deprecation")
					@Override
					public boolean onMenuItemClick(MenuItem p1)
					{
						switch (p1.getTitle().toString())
						{
							case "Visit":
								wv.loadUrl(webViewHitTestResult.getExtra().toString());
								break;
							case "Save link":

								break;
							case "Share":

								break;
							case "Copy link":
								String ctext;
								ctext = webViewHitTestResult.getExtra().toString();
								if (ctext.length() != 0)
								{
									if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB)
									{

										android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
										clipboard.setText(ctext);
										Toast.makeText(getApplicationContext(), "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

									}
									else
									{
										android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
										android.content.ClipData clip = android.content.ClipData.newPlainText("Clip", ctext);
										Toast.makeText(getApplicationContext(), "Link copied", Toast.LENGTH_SHORT).show();
										clipboard.setPrimaryClip(clip);
									}}
								else
								{
									Toast.makeText(getApplicationContext(), "Nothing to Copy", Toast.LENGTH_SHORT).show();

								}
								break;
						}
						return false;
					}
				});

		}
    }

}
