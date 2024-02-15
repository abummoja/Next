package next.browser.activities;

import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import next.browser.*;

public class DownloadsActivity extends AppCompatActivity
{

	private File mCurrentNode = null;
	private File mLastNode = null;
	private File mRootNode = null;
	private ListView lst;
	private ArrayList<File> mFiles = new ArrayList<File>();
	private CustomAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloads_ui);

		lst = findViewById(R.id.downList);
		mAdapter = new CustomAdapter(this, R.layout.list_row, mFiles);
	    lst.setAdapter(mAdapter);

		Collections.sort(mFiles);
		lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id)
				{
					File f = (File) parent.getItemAtPosition(position);
					if (position == 1)
					{
						if (mCurrentNode.compareTo(mRootNode) != 0)
						{
							mCurrentNode = f.getParentFile();
							refreshFileList();
						}
					}
					else if (f.isDirectory())
					{
						mCurrentNode = f;
						refreshFileList();
					}
					if(f.isFile()){
						String s = f.getAbsolutePath().toString();
						File file = new File(s);
						MimeTypeMap map = MimeTypeMap.getSingleton();
						String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
						String type = map.getMimeTypeFromExtension(ext);

						if (type == null)
							type = "*/*";

						Intent intent = new Intent(Intent.ACTION_VIEW);
						Uri data = Uri.fromFile(file);

						intent.setDataAndType(data, type);

						startActivity(intent);
					}
					else
					{
						Toast.makeText(DownloadsActivity.this, "You selected: " + f.getName() + "!", Toast.LENGTH_SHORT).show();
					}
				}
			});

	    if (savedInstanceState != null)
		{
	    	mRootNode = (File)savedInstanceState.getSerializable("root_node");
	    	mLastNode = (File)savedInstanceState.getSerializable("last_node");
	    	mCurrentNode = (File)savedInstanceState.getSerializable("current_node");
	    }
	    refreshFileList();
    }

    private void refreshFileList()
	{
		if (mRootNode == null) mRootNode = new File(Environment.getExternalStorageDirectory().toString());
		if (mCurrentNode == null) mCurrentNode = mRootNode; 
		mLastNode = mCurrentNode;
		File[] files = mCurrentNode.listFiles();
		mFiles.clear();
		mFiles.add(mRootNode);
		mFiles.add(mLastNode);
		if (files != null)
		{
			for (int i = 0; i < files.length; i++) mFiles.add(files[i]);
		}
		mAdapter.notifyDataSetChanged();
	}

    @Override
	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putSerializable("root_node", mRootNode);
		outState.putSerializable("current_node", mCurrentNode);
		outState.putSerializable("last_node", mLastNode);
		super.onSaveInstanceState(outState);
	} 

}
