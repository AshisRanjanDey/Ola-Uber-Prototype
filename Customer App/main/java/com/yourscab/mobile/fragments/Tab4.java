package com.yourscab.mobile.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yourscab.mobile.R;
import com.yourscab.mobile.libs.CircularImageView;
import com.yourscab.mobile.subactivities.EditProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Tab4 extends Fragment {

    CircularImageView circularImageView;
    TextView phone_tv, name_tv;
    private static final int PICK_IMAGE = 333;

    public Tab4() {
    }

    public static Tab4 newInstance(String param1, String param2) {
        Tab4 fragment = new Tab4();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.tab4, container, false);
        phone_tv = (TextView) view.findViewById(R.id.phone);
        name_tv = (TextView) view.findViewById(R.id.username);
        circularImageView = (CircularImageView) view.findViewById(R.id.profile_image);

        // now load the values
        FirebaseDatabase.getInstance().getReference().child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String user_name = (String) dataSnapshot.child("name").getValue();
                        String image_url = (String) dataSnapshot.child("image_url").getValue();
                        String phone = (String) dataSnapshot.child("phone").getValue();

                        // now set the values here
                        phone_tv.setText(phone.replace("+91", ""));
                        name_tv.setText(user_name.toUpperCase());
                        Picasso.with(getContext()).load(Uri.parse(image_url)).placeholder(R.drawable.profile_img).into(circularImageView);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getContext(), "Check your internet connection...", Toast.LENGTH_SHORT).show();
                    }
                });

        // now set the click listener for the editing the profile
        view.findViewById(R.id.cardView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfile.class);
                intent.putExtra("name", name_tv.getText());
                intent.putExtra("phone", phone_tv.getText());
                startActivity(intent);
            }
        });


        // now enable the upload option for the profile image
        view.findViewById(R.id.iv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // codes for selecting the image from the gallery and choose any gallery app
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data1) {
        if (requestCode == PICK_IMAGE) {
            // it is the success

            Uri image_uri = data1.getData();
            Picasso.with(getContext()).load(image_uri).into(circularImageView);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            try {
                StorageReference storageRef = storage.getReference();
                StorageReference mountainImagesRef = storageRef.child("images/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profile.jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), image_uri);
                bitmap = cropToSquare(bitmap);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainImagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getContext(), "Unable to upload profile picture...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Map<String, Object> taskMap = new HashMap<String, Object>();
                        taskMap.put("image_url", "" + downloadUrl);
                        FirebaseDatabase.getInstance().getReference().child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .updateChildren(taskMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Successfully changed the profile picture...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "151" + e, Toast.LENGTH_SHORT).show();

            }
        }

    }


    public static Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

}



