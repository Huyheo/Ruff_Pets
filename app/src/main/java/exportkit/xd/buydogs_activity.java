
	 
	/*
	 *	This content is generated from the API File Info.
	 *	(Alt+Shift+Ctrl+I).
	 *
	 *	@desc 		
	 *	@file 		home
	 *	@date 		1624372132986
	 *	@title 		Page 3
	 *	@author 	
	 *	@keywords 	
	 *	@generator 	Export Kit v1.3.figma
	 *
	 */
	

package exportkit.xd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

public class buydogs_activity extends Activity {

	private View serviecs;
	private View profile;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.buydogs);

		serviecs=findViewById(R.id.rectangle_2_ek1);
		profile=findViewById(R.id.rectangle_2_ek2);

		serviecs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(buydogs_activity.this , services_activity.class));
				finish();
			}
		});

		profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(buydogs_activity.this , profile_activity.class));
				finish();
			}
		});
	}
}
	
	