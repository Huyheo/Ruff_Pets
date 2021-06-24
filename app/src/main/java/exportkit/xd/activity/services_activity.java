
	 
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
	

package exportkit.xd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;

import exportkit.xd.R;

    public class services_activity extends Activity {

	private View buydog;
	private View profile;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.services);

		buydog=findViewById(R.id.rectangle_2);
		profile=findViewById(R.id.rectangle_2_ek2);

		buydog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(services_activity.this , buydogs_activity.class));
				finish();
			}
		});

		profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(services_activity.this , profile_activity.class));
				finish();
			}
		});
	
	}
}
	
	