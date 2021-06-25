package exportkit.xd.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import exportkit.xd.R;
import exportkit.xd.model.Upload;
import exportkit.xd.model.dog;

public class buydog_edititem_activity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView img;
    private RelativeLayout back;
    private EditText tittle;
    private EditText mota;
    private EditText loai;
    private EditText age;
    private EditText cannang;
    private EditText price;
    private EditText address;
    private dog dog1;
    private DatabaseReference mDatabase;
    private Chip chipnam;
    private Chip chipnu;
    private Switch soldswitch;
    private RelativeLayout btneditok;
    private Button btnloadimg;
    Context context;
    private StorageReference mStorageref;
    private DatabaseReference mDatabaseref;
    private Uri imageuri;
    private FirebaseUser user;
    private DatabaseReference dogDatabae;


    int iddog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buydog_edititem);
        getIddog();

        img=findViewById(R.id.editimageView);
        back=findViewById(R.id.frame_17_ek2);
        Init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void Init() {
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("dog").child("dog"+String.valueOf(iddog+1)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                tittle= findViewById(R.id.edittitle);
                mota=findViewById(R.id.editmota);
                loai=findViewById(R.id.editloai);
                age=findViewById(R.id.editage);
                cannang=findViewById(R.id.editcannang);
                price=findViewById(R.id.editprice);
                address=findViewById(R.id.editaddress);
                chipnam=findViewById(R.id.chip3);
                chipnu=findViewById(R.id.chip4);
                soldswitch=findViewById(R.id.switchsold);
                btneditok= findViewById(R.id.btneditok);
                btnloadimg =findViewById(R.id.buttonloadimg);

                user = FirebaseAuth.getInstance().getCurrentUser();

                mStorageref = FirebaseStorage.getInstance().getReference("Upload Photos");
                mDatabaseref = FirebaseDatabase.getInstance().getReference("Upload Photos");

                dogDatabae= FirebaseDatabase.getInstance().getReference().child("dog");

                String gioitinh;

                if (!task.isSuccessful()) {
                    Toast.makeText(buydog_edititem_activity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                }
                else {
                    dog1 = (dog) task.getResult().getValue(dog.class);
                    Log.d("Debug",String.valueOf(dog1));
                    tittle.setText(dog1.getTitle().toString());
                    mota.setText(dog1.getMota().toString());
                    age.setText(dog1.getAge().toString());

                    gioitinh = dog1.getGioitinh();

                    price.setText(dog1.getGia()+" VNĐ");
                    loai.setText(dog1.getLoai());
                    if(gioitinh.equals("Male"))
                    {
                        chipnam.setChecked(true);
                    }
                    else
                    {
                        chipnu.setChecked(true);
                    }
                    if (dog1.isTrang_thai())
                        soldswitch.setChecked(false);

                    cannang.setText(String.valueOf(dog1.getCan_nang()));
                    address.setText(dog1.getDiachi());
                    Picasso.get().load(Uri.parse(dog1.getHinhanh())).into(img);

                    tittle.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setTitle(tittle.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    mota.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setMota(mota.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    age.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setAge(age.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    cannang.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setCan_nang(Float.parseFloat(cannang.getText().toString()));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    price.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }



                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dog1.setGia(price.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    btneditok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            uploadfile();
                        }
                    });

                    chipnam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (chipnam.isChecked())
                            {
                                dog1.setGioitinh("Male");
                            }
                        }
                    });

                    chipnu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (chipnu.isChecked())
                            {
                                dog1.setGioitinh("Female");
                            }
                        }
                    });

                    soldswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (soldswitch.isChecked())
                            {
                                dog1.setTrang_thai(true);
                            }
                            else
                            {
                                dog1.setTrang_thai(false);
                            }
                        }
                    });

                    btnloadimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openfilechooser();
                        }
                    });
                }
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
            StorageReference filereference  = mStorageref.child(System.currentTimeMillis()+
                    "."+getFileExtension(imageuri));

            filereference.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(buydog_edititem_activity.this, "Edit Post Successfully", Toast.LENGTH_SHORT).show();
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
            adddog(Uri.parse(dog1.getHinhanh()));
    }

    private void getIddog() {
        iddog = getIntent().getIntExtra("iddog",-1);
        Log.d("id : ",iddog+"");

    }

    private void adddog(Uri uri){
        String txt_title = tittle.getText().toString();
        String txt_mota = mota.getText().toString();
        String txt_loai = loai.getText().toString();
        String txt_gia = price.getText().toString();
        String txt_cannang = cannang.getText().toString();
        String txt_diachi = address.getText().toString();
        String txt_age = age.getText().toString();
        String txt_gioitinh="";

        if (TextUtils.isEmpty(txt_title)||TextUtils.isEmpty(txt_mota)||
                TextUtils.isEmpty(txt_loai)||TextUtils.isEmpty(txt_gia)||
                TextUtils.isEmpty(txt_cannang)||TextUtils.isEmpty(txt_diachi)||
                TextUtils.isEmpty(txt_age)){
            Toast.makeText(buydog_edititem_activity.this,"Empty credentails!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (chipnam.isChecked()) {
                txt_gioitinh = "Male";
            } else {
                txt_gioitinh = "Female";
            }
//            Toast.makeText(buydog_additem_activity.this, txt_gioitinh+String.valueOf(Collections.max(dogs)), Toast.LENGTH_SHORT).show();
            dog1.setHinhanh(uri.toString());
            dogDatabae.child("dog"+String.valueOf(dog1.getId()+1)).setValue(dog1);
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