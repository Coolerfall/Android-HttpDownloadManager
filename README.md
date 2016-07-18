Android Http Download Manager
===========================

An useful and effective http download manager for Android. This download manager is designed according to the idea and implementation of Volley.

Usage
=====
* If you don't set the destination file path, the download manager will use `Environment.DIRECTORY_DOWNLOADS` in SDCard as default directory:
```java
DownloadManager manager = downloadManager =
			new DownloadManager.Builder().context(this)
				.downloader(OkHttpDownloader.create())
				.threadPoolSize(2)
				.build();
String destPath = Environment.getExternalStorageDirectory() + File.separator + "test.apk";
DownloadRequest request = new DownloadRequest.Builder()
				.retryTime(3)
				.allowedNetworkTypes(DownloadRequest.NETWORK_WIFI)
				.progressInterval(1000)
				.priority(Priority.HIGH)
				.url("http://something.to.download")
				.destinationFilePath(destPath)
				.downloadCallback(new DownloadCallback() {
					@Override public void onStart(int downloadId, long totalBytes) {
						
					}

					@Override public void onRetry(int downloadId) {
						
					}

					@Override
					public void onProgress(int downloadId, long bytesWritten, long totalBytes) {
						
					}

					@Override public void onSuccess(int downloadId, String filePath) {
						
					}

					@Override public void onFailure(int downloadId, int statusCode, String errMsg) {
						
					}
				})
				.build();
				
int downloadId = manager.add(request);
```

* If you don't want to set the filename but want to set the download directory, then you can use `destinationDirectory(String directory)`, but this method will be ignored if `destinationFilePath((String filePath)` was used.
* You can also set retry time with method `retryTime(int retryTime)` if necessary, default retry time is 1.
* This manager support downloading in different network type with method `allowedNetworkTypes(int types)`, the types can be `DownloadRequest.NETWORK_MOBILE` and `DownloadRequest.NETWORK_WIFI`. This method need *android.permission.ACCESS_NETWORK_STATE* permission.
* The thread pool size of download manager is 3 by default. If you need a larger pool, then you can try the method `threadPoolSize(int poolSize)` in `DownloadManager#Builder`.
* You need *android.permission.WRITE_EXTERNAL_STORAGE* permission if you don't use public directory in SDCard as download destination file path. Don't forget to add *android.permission.INTERNET* permission.
* This download manager support breakpoint downloading, so you can restart the downloading after pause.
* If you don't want DownloadDispathcer invoke `onProgress(int downloadId, long bytesWritten, long totalBytes)` frequently, then you can use `progressInterval(int millisec)`.
* If you want one download request get high priority, then you can use `priority(Priority priority)`.
* The download manager provides two kinds of `Downloader`(`URLDownloader` and `OkHttpDownloader`), and the it will detect which downloader to use. You can also implement your own `Downloader` just like what `URLDownloader` and `OkHttpDownloader` do.

Download
========
	allprojects {
		repositories {
			maven { url 'https://oss.sonatype.org/content/repositories/snapshots' }
		}
	}

	compile 'com.coolerfall:android-http-download-manager:1.6.0-SNAPSHOT'

Credits
=======
  * [Volley][1] - Google networking library for android.

License
=======

    Copyright (C) 2014-2016 Vincent Cheung

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 


[1]: https://android.googlesource.com/platform/frameworks/volley
[2]: https://search.maven.org/remote_content?g=com.coolerfall&a=android-http-download-manager&v=LATEST
