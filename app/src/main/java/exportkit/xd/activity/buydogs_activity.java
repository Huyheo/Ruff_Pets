
	 
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import exportkit.xd.R;
import exportkit.xd.adapter.DogListAdapter;
import exportkit.xd.model.dog;

	public class buydogs_activity extends Activity {

	private ArrayList<dog> dogs;
	private View serviecs;
	private View profile;
	private FloatingActionButton addBtn;
	private DogListAdapter dogListAdapter;
	private RecyclerView listdog;
	private DatabaseReference dogDatabae;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.buydogs);

		serviecs=findViewById(R.id.rectangle_2_ek1);
		profile=findViewById(R.id.rectangle_2_ek2);
		addBtn=findViewById(R.id.addItem);
		listdog=findViewById(R.id.list_dog);
		dogDatabae= FirebaseDatabase.getInstance().getReference().child("dog");
		dogs = new ArrayList<>();

		listdog.setLayoutManager(new LinearLayoutManager(this));
		dogListAdapter = new DogListAdapter(dogs);

		listdog.setAdapter(dogListAdapter);

		dogDatabae.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				dogs.clear();
				for (DataSnapshot snapshot : dataSnapshot.getChildren()){
					dog dog = snapshot.getValue(dog.class);
					dogs.add(dog);
				}
				dogListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

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

		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(buydogs_activity.this , buydog_detail_activity.class));
			}
		});
	}
}
	
	