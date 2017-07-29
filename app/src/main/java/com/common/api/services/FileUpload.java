package com.common.api.services;



import com.common.api.ProgressRequestBody;
import com.common.api.responsemodel.FileUploadResponse;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by ln-003 on 28/2/17.
 */
public interface FileUpload {

    @Multipart
    @POST("chatupload")
    Observable<FileUploadResponse> fileUpload(@Part MultipartBody.Part file);

    @Multipart
    @POST("chatupload")
    Observable<FileUploadResponse> fileVideoUpload(@Part MultipartBody.Part file, @Part MultipartBody.Part image);

    @Multipart
    @POST("upload")
    Observable<FileUploadResponse> fileVideoUpload(@Part ProgressRequestBody file, @Part ProgressRequestBody image);
}
