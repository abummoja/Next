package next.browser.activities;

import android.content.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import next.browser.*;

public class CustomAdapter extends ArrayAdapter<File>
{

	private ArrayList<File> items;
	private Context c = null;

	/**
	 * Standard Data Adapter Construction
	 */ 
	public CustomAdapter(Context context, int textViewResourceId, ArrayList<File> items)
	{
		super(context, textViewResourceId, items);
		this.items = items;
		this.c = context;
	}

	/**
	 * Code invoked when container notifies data set of change.
	 */ 
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		View v = convertView;

		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_row, null);
		}

		TextView filename = null;
		ImageView fileicon = null;

		File f = items.get(position);
		if (f != null)
		{
			filename = (TextView) v.findViewById(R.id.filename);
			fileicon = (ImageView) v.findViewById(R.id.fileicon);
		}

		if (filename != null)
		{
			if (position == 0)
			{
				filename.setText(f.getAbsolutePath());
			}
			else if (position == 1)
			{
				filename.setText(f.getAbsolutePath());
			}
			else
			{
				filename.setText(f.getName());
			}
		}

		String s = f.getAbsolutePath().toString();
		File file = new File(s);
		MimeTypeMap map = MimeTypeMap.getSingleton();
		String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
		String type = map.getMimeTypeFromExtension(ext);

		if (fileicon != null)
		{
			if (position == 0)
			{
				fileicon.setImageResource(R.drawable.root);
			}
			else if (position == 1)
			{
				fileicon.setImageResource(R.drawable.up);
			}
			else if (f.isDirectory())
			{
				fileicon.setImageResource(R.drawable.folder);
			}
			else
			{
				switch (map.getFileExtensionFromUrl(s))
				{
					case "png":
						fileicon.setImageResource(R.drawable.file_png);
						break;
					case "js":
						fileicon.setImageResource(R.drawable.file_js);
						break;
					case "db":
						fileicon.setImageResource(R.drawable.file_db);
						break;
					case "m4a":
						fileicon.setImageResource(R.drawable.file_rec);
						break;
					case "jpg":
						fileicon.setImageResource(R.drawable.file_png);
						break;
					case "pdf":
						fileicon.setImageResource(R.drawable.file_pdf);
						break;
					case "doc":
						fileicon.setImageResource(R.drawable.file_doc);
						break;
					case "mp3":
						fileicon.setImageResource(R.drawable.file_audio);
						break;
					case "mp4":
						fileicon.setImageResource(R.drawable.file_video);
						break;
					case "php":
						fileicon.setImageResource(R.drawable.file_php);
						break;
					case "ts":
						fileicon.setImageResource(R.drawable.file_ts);
						break;
					case "prj":
						fileicon.setImageResource(R.drawable.file_prj);
						break;
					case "xprj":
						fileicon.setImageResource(R.drawable.file_prj);
						break;
					case "apk":
						fileicon.setImageResource(R.drawable.file_apk);
						break;
					case "html":
						fileicon.setImageResource(R.drawable.file_html);
						break;
					case "cache":
						fileicon.setImageResource(R.drawable.file_cache);
						break;
					case "data":
						fileicon.setImageResource(R.drawable.file_data);
						break;
					case "draft":
						fileicon.setImageResource(R.drawable.file_draft);
						break;
					case "kt":
						fileicon.setImageResource(R.drawable.file_kt);
						break;
					case "mhtml":
						fileicon.setImageResource(R.drawable.main_webpage);
						break;
					case "java":
						fileicon.setImageResource(R.drawable.file_java);
						break;
					case "c":
						fileicon.setImageResource(R.drawable.file_c);
						break;
					case "ppt":
						fileicon.setImageResource(R.drawable.file_ppt);
						break;
					case "pptx":
						fileicon.setImageResource(R.drawable.file_ppt);
						break;
					case "data":
						fileicon.setImageResource(R.drawable.file_data);
						break;
					case "py":
						fileicon.setImageResource(R.drawable.file_python);
						break;
					case "xls":
						fileicon.setImageResource(R.drawable.file_xls);
						break;
					case "cpp":
						fileicon.setImageResource(R.drawable.file_cpp);
						break;
					case "css":
						fileicon.setImageResource(R.drawable.file_css);
						break;
					case "xml":
						fileicon.setImageResource(R.drawable.file_html);
						break;
					case "txt":
						fileicon.setImageResource(R.drawable.file_txt);
						break;
					case "zip":
						fileicon.setImageResource(R.drawable.file_zip);
						break;
					case "":
						fileicon.setImageResource(R.drawable.file_unknown);
						break;
				}
			}
		}

		return v;
	}

}
