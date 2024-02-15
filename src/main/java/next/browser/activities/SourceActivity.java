package next.browser.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import next.browser.R;
import android.widget.*;
import java.net.*;
import java.util.*;
import java.io.*;
import android.support.v7.app.*;
import android.content.*;

public class SourceActivity extends AppCompatActivity
{

	TextView ttl, srce;
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_ui);


		ttl = findViewById(R.id.sourceTitle);
		srce = findViewById(R.id.sourceCode);

		String srcr = getIntent().getStringExtra("sourcer").toString();

		ttl.setText(srcr);

		try
		{
            URL site = new URL(srcr);
            URLConnection uc = site.openConnection();
            Scanner scanner = new Scanner(new InputStreamReader(uc.getInputStream()));
            int count = 0;
            while (scanner.hasNext())
			{
                System.out.println(scanner.next());
                count++;
            }
            System.out.println("Number of tokens: " + count);
            scanner.close();
        }
		catch (Exception ex)
		{
            System.out.println("Error: " + ex.getMessage());
        }

    }

}
