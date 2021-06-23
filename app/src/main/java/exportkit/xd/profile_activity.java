
	 
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
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;


import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

	public class profile_activity extends Activity {
	private static final int PICK_IMAGE_REQUEST = 1;
	private View editprofile;
	private View buydog;
	private View serviecs;
	private RelativeLayout logout;
	private TextView username;
	private ImageView img;
	private Uri imageuri;
	private StorageReference mStorageref;
	private DatabaseReference mDatabaseref;
	private FirebaseUser user;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);


		editprofile =findViewById(R.id._rectangle_25);
		serviecs=findViewById(R.id.rectangle_2_ek1);
		buydog=findViewById(R.id.rectangle_2);
		logout=findViewById(R.id.frame_36);
		username=findViewById(R.id.username);
		img=findViewById(R.id.ellipse_1);
		mStorageref = FirebaseStorage.getInstance().getReference("Upload Photos");
		mDatabaseref = FirebaseDatabase.getInstance().getReference("Upload Photos");

		user = FirebaseAuth.getInstance().getCurrentUser();
		username.setText(user.getEmail());
		Picasso.get().load(user.getPhotoUrl()).into(img);

		editprofile.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {
				startActivity(new Intent(profile_activity.this , editprofile_activity.class));
			}
		});

		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openfilechooser();
			}
		});

		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FirebaseAuth.getInstance().signOut();
				Toast.makeText(profile_activity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(profile_activity.this , home_activity.class));
				finish();
			}
		});

		serviecs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(profile_activity.this , services_activity.class));
				finish();
			}
		});

		buydog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(profile_activity.this , buydogs_activity.class));
				finish();
			}
		});

		}
	private String getFileExtension(Uri uri){
		ContentResolver cr = getContentResolver();
		MimeTypeMap mime = MimeTypeMap.getSingleton();
		return mime.getExtensionFromMimeType(cr.getType(uri));
	}

	private void uploadfile() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Uploading");
		progressDialog.show();

		if (imageuri !=null){
			StorageReference  filereference  = mStorageref.child(System.currentTimeMillis()+
					"."+getFileExtension(imageuri));

			filereference.putFile(imageuri)
					.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							Toast.makeText(profile_activity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
							taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
								@Override
								public void onSuccess(Uri uri) {
									Uri downloadUrl = uri;
									Upload upload = new Upload(user.getEmail().trim(),downloadUrl.toString());
									progressDialog.show();
									String  uploadId = mDatabaseref.push().getKey();
									mDatabaseref.child(uploadId).setValue(upload);
									progressDialog.setCanceledOnTouchOutside(false);
									progressDialog.dismiss();
									uploadimg(uri);
								}
							});
						}
					})
					.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {

						}
					})
					.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
							double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
							progressDialog.setCanceledOnTouchOutside(false);
							progressDialog.setMessage("Uploaded  " +(int)progress+"%");
						}
					});

		}else
			Toast.makeText(this, "Please Select a Image", Toast.LENGTH_SHORT).show();
	}

	private void uploadimg(Uri uri){

		UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
				.setPhotoUri(uri)
				.build();

		user.updateProfile(profileUpdates)
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							Toast.makeText(profile_activity.this, "Update photo profile successfully! ", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

		private void openfilechooser() {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent,PICK_IMAGE_REQUEST);
//			uploadfile();

		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null &&
					data.getData()!=null){
				imageuri = data.getData();
				Picasso.get().load(imageuri).into(img);
				uploadfile();

			}
		}
}
	
	