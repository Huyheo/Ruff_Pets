package exportkit.xd.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import exportkit.xd.R;
import exportkit.xd.model.Upload;
import exportkit.xd.model.dog;

public class buydog_additem_activity extends Activity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView img;
    private RelativeLayout back;
    private EditText title;
    private EditText mota;
    private EditText gia;
    private EditText loai;
    private EditText diachi;
    private EditText cannang;
    private EditText age;
    private Button loadimg;
    private RelativeLayout btnOK;
    private Chip gioitinh;
    private StorageReference mStorageref;
    private DatabaseReference mDatabaseref;
    private Uri imageuri;
    private FirebaseUser user;
    private DatabaseReference dogDatabae;
    private ArrayList<Integer> dogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buydog_additem);

        img=findViewById(R.id.imageView);
        back=findViewById(R.id.frame_17_ek2);
        title=findViewById(R.id.title);
        mota=findViewById(R.id.mota);
        loai=findViewById(R.id.loai);
        gia=findViewById(R.id.gia);
        diachi=findViewById(R.id.diachi);
        cannang=findViewById(R.id.cannang);
        age=findViewById(R.id.age);
        loadimg=findViewById(R.id.button2);
        btnOK=findViewById(R.id.button_ek4);
        gioitinh=findViewById(R.id.chip3);

        user = FirebaseAuth.getInstance().getCurrentUser();

        mStorageref = FirebaseStorage.getInstance().getReference("Upload Photos");
        mDatabaseref = FirebaseDatabase.getInstance().getReference("Upload Photos");

        dogDatabae= FirebaseDatabase.getInstance().getReference().child("dog");
        dogs = new ArrayList<>();

        dogDatabae.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dogs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    dog dog = snapshot.getValue(dog.class);
                    dogs.add(dog.getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadfile();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-demo-90f36.appspot.com/o/wiggle_logo.png?alt=media&token=75f58293-713f-400a-a374-7a532f1dbc7b")).into(img);
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
            StorageReference filereference  = mStorageref.child(System.currentTimeMillis()+
                    "."+getFileExtension(imageuri));

            filereference.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(buydog_additem_activity.this, "Add Post Successfully", Toast.LENGTH_SHORT).show();
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
//                                    uploadimg(uri);
                                    adddog(uri);
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

    private void adddog(Uri uri){
        String txt_title = title.getText().toString();
        String txt_mota = mota.getText().toString();
        String txt_loai = loai.getText().toString();
        String txt_gia = gia.getText().toString();
        String txt_cannang = cannang.getText().toString();
        String txt_diachi = diachi.getText().toString();
        String txt_age = age.getText().toString();
        String txt_gioitinh="";

        if (TextUtils.isEmpty(txt_title)||TextUtils.isEmpty(txt_mota)||
                TextUtils.isEmpty(txt_loai)||TextUtils.isEmpty(txt_gia)||
                TextUtils.isEmpty(txt_cannang)||TextUtils.isEmpty(txt_diachi)||
                TextUtils.isEmpty(txt_age)){
            Toast.makeText(buydog_additem_activity.this,"Empty credentails!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (gioitinh.isChecked()) {
                txt_gioitinh = "Male";
            } else {
                txt_gioitinh = "Female";
            }
//            Toast.makeText(buydog_additem_activity.this, txt_gioitinh+String.valueOf(Collections.max(dogs)), Toast.LENGTH_SHORT).show();
            dog dog = new dog(Collections.max(dogs) + 1, user.getUid(),
                    txt_title, txt_mota, txt_gia, txt_diachi, txt_loai,
                    txt_age, Float.parseFloat(txt_cannang), txt_gioitinh, true, uri.toString());
            dogDatabae.child("dog" + String.valueOf(Collections.max(dogs) + 2)).setValue(dog);
        }
        finish();
    }

    private void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null &&
                data.getData()!=null){
            imageuri=data.getData();
            Picasso.get().load(imageuri).into(img);
        }
    }
}