package com.coolerfall.download;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Download delivery: used to delivery callback to call back in main thread.
 *
 * @author Vincent Cheung (coolingfall@gmail.com)
 */
final class DownloadDelivery {
	private final Executor mDownloadPoster;

	public DownloadDelivery(final Handler handler) {
		mDownloadPoster = new Executor() {
			@Override
			public void execute(@NonNull Runnable command) {
				handler.post(command);
			}
		};
	}

	/**
	 * Post download start event.
	 *
	 * @param request    download request
	 * @param totalBytes total bytes
	 */
	protected void postStart(final DownloadRequest request, final long totalBytes) {
		mDownloadPoster.execute(new Runnable() {
			@Override
			public void run() {
				if (request.downloadCallback() != null) {
					request.downloadCallback().onStart(request.downloadId(), totalBytes);
				}
			}
		});
	}

	/**
	 * Post download retry event.
	 *
	 * @param request download request
	 */
	protected void postRetry(final DownloadRequest request) {
		mDownloadPoster.execute(new Runnable() {
			@Override
			public void run() {
				if (request.downloadCallback() != null) {
					request.downloadCallback().onRetry(request.downloadId());
				}
			}
		});
	}

	/**
	 * Post download progress event.
	 *
	 * @param request      download request
	 * @param bytesWritten the bytes have written to file
	 * @param totalBytes   the total bytes of currnet file in downloading
	 */
	protected void postProgress(final DownloadRequest request,
		final long bytesWritten, final long totalBytes) {
		mDownloadPoster.execute(new Runnable() {
			@Override
			public void run() {
				if (request.downloadCallback() != null) {
					request.downloadCallback().onProgress(
						request.downloadId(), bytesWritten, totalBytes);
				}
			}
		});
	}

	/**
	 * Post download success event.
	 *
	 * @param request download request
	 */
	protected void postSuccess(final DownloadRequest request) {
		mDownloadPoster.execute(new Runnable() {
			@Override
			public void run() {
				if (request.downloadCallback() != null) {
					request.downloadCallback().onSuccess(
						request.downloadId(), request.destinationFilePath());
				}
			}
		});
	}

	/**
	 * Post download failure event.
	 *
	 * @param request    download request
	 * @param statusCode status code
	 * @param errMsg     error message
	 */
	protected void postFailure(final DownloadRequest request, final int statusCode,
		final String errMsg) {
		mDownloadPoster.execute(new Runnable() {
			@Override
			public void run() {
				if (request.downloadCallback() != null) {
					request.downloadCallback().onFailure(
						request.downloadId(), statusCode, errMsg);
				}
			}
		});
	}
}
