package com.zhy.http.okhttp.builder;

import android.util.Log;
import com.zhy.http.okhttp.request.PostFormRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder>
    implements HasParamsable {
  private List<FileInput> files = new ArrayList<>();
  private boolean isMultipart = false;

  @Override public RequestCall build() {
    PostFormRequest mPostFormRequest = new PostFormRequest(url, tag, params, headers, files, id);
    if(params!=null){
      Log.e("feitianzhu_post","post请求参数"+params.toString());
    }
    mPostFormRequest.setMultipart(isMultipart);
    return mPostFormRequest.build();
  }

  public PostFormBuilder addFiles(String key, Map<String, File> files) {
    for (String filename : files.keySet()) {
      this.files.add(new FileInput(key, filename, files.get(filename)));
    }
    return this;
  }

  public PostFormBuilder addFile(String name, String filename, File file) {
    files.add(new FileInput(name, filename, file));
    return this;
  }

  public PostFormBuilder setMultipart(boolean is) {
    isMultipart = is;
    return this;
  }

  public static class FileInput {
    public String key;
    public String filename;
    public File file;

    public FileInput(String name, String filename, File file) {
      this.key = name;
      this.filename = filename;
      this.file = file;
    }

    @Override public String toString() {
      return "FileInput{"
          + "key='"
          + key
          + '\''
          + ", filename='"
          + filename
          + '\''
          + ", file="
          + file
          + '}';
    }
  }

  @Override public PostFormBuilder params(Map<String, String> params) {
    this.params = params;
    return this;
  }

  @Override public PostFormBuilder addParams(String key, String val) {
    if (this.params == null) {
      params = new LinkedHashMap<>();
    }
    params.put(key, val);
    return this;
  }
}
