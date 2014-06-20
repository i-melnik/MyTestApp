//package com.melnik.odesktest.util;
//
//import java.io.File;
//import java.io.FileDescriptor;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.ParcelFileDescriptor;
//import android.os.RemoteException;
//import android.provider.MediaStore;
//import android.util.Log;
//
//import com.melnik.odesktest.R;
//
//public class AlbumImageUtil
//{
//
//	private static final String TAG = "AlbumImageUtil";
//
//	private static int sArtId = -2;
//	private static byte[] mCachedArt;
//	private static Bitmap mCachedBit = null;
//	private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
//	private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
//	private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
//	private static final String sExternalMediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString();
//	private static final HashMap<Integer, Drawable> sArtCache = new HashMap<Integer, Drawable>();
//	private static int sArtCacheId = -1;
//
//	// Get album art for specified album. You should not pass in the album id
//	// for the "unknown" album here (use -1 instead)
//	public static Bitmap getArtwork(Context context, int album_id)
//	{
//
//		if (album_id < 0)
//		{
//			// This is something that is not in the database, so get the album
//			// art directly
//			// from the file.
//			Bitmap bm = getArtworkFromFile(context, null, -1);
//			if (bm != null)
//			{
//				return bm;
//			}
//			return getDefaultArtwork(context);
//		}
//
//		ContentResolver res = context.getContentResolver();
//		Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
//		if (uri != null)
//		{
//			InputStream in = null;
//			try
//			{
//				in = res.openInputStream(uri);
//				return BitmapFactory.decodeStream(in, null, sBitmapOptions);
//			}
//			catch (FileNotFoundException ex)
//			{
//				// The album art thumbnail does not actually exist. Maybe the
//				// user deleted it, or
//				// maybe it never existed to begin with.
//				Bitmap bm = getArtworkFromFile(context, null, album_id);
//				if (bm != null)
//				{
//					// Put the newly found artwork in the database.
//					// Note that this shouldn't be done for the "unknown" album,
//					// but if this method is called correctly, that won't
//					// happen.
//
//					// first write it somewhere
//					String file = Environment.getExternalStorageDirectory() + "/albumthumbs/"
//							+ String.valueOf(System.currentTimeMillis());
//					if (ensureFileExists(file))
//					{
//						try
//						{
//							OutputStream outstream = new FileOutputStream(file);
//							if (bm.getConfig() == null)
//							{
//								bm = bm.copy(Bitmap.Config.RGB_565, false);
//								if (bm == null)
//								{
//									return getDefaultArtwork(context);
//								}
//							}
//							boolean success = bm.compress(Bitmap.CompressFormat.JPEG, 75, outstream);
//							outstream.close();
//							if (success)
//							{
//								ContentValues values = new ContentValues();
//								values.put("album_id", album_id);
//								values.put("_data", file);
//								Uri newuri = res.insert(sArtworkUri, values);
//								if (newuri == null)
//								{
//									// Failed to insert in to the database. The
//									// most likely
//									// cause of this is that the item already
//									// existed in the
//									// database, and the most likely cause of
//									// that is that
//									// the album was scanned before, but the
//									// user deleted the
//									// album art from the sd card.
//									// We can ignore that case here, since the
//									// media provider
//									// will regenerate the album art for those
//									// entries when
//									// it detects this.
//									success = false;
//								}
//							}
//							if (!success)
//							{
//								File f = new File(file);
//								f.delete();
//							}
//						}
//						catch (FileNotFoundException e)
//						{
//							Log.e(TAG, "error creating file", e);
//						}
//						catch (IOException e)
//						{
//							Log.e(TAG, "error creating file", e);
//						}
//					}
//				}
//				else
//				{
//					bm = getDefaultArtwork(context);
//				}
//				return bm;
//			}
//			finally
//			{
//				try
//				{
//					if (in != null)
//					{
//						in.close();
//					}
//				}
//				catch (IOException ex)
//				{
//				}
//			}
//		}
//
//		return null;
//	}
//
//	private static Bitmap getArtworkFromFile(Context context, Uri uri, int albumid)
//	{
//		Bitmap bm = null;
//		byte[] art = null;
//		String path = null;
//
//		if (sArtId == albumid)
//		{
//			// Log.i("@@@@@@ ", "reusing cached data", new Exception());
//			if (mCachedBit != null)
//			{
//				return mCachedBit;
//			}
//			art = mCachedArt;
//		}
//		else
//		{
//			// try reading embedded artwork
//			if (uri == null)
//			{
//				try
//				{
//					int curalbum = sService.getAlbumId();
//					if (curalbum == albumid || albumid < 0)
//					{
//						path = sService.getPath();
//						if (path != null)
//						{
//							uri = Uri.parse(path);
//						}
//					}
//				}
//				catch (RemoteException ex)
//				{
//				}
//			}
//			if (uri == null)
//			{
//				if (albumid >= 0)
//				{
//					Cursor c = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] {
//							MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM }, MediaStore.Audio.Media.ALBUM_ID + "=?",
//							new String[] { String.valueOf(albumid) }, null);
//					if (c != null)
//					{
//						c.moveToFirst();
//						if (!c.isAfterLast())
//						{
//							int trackid = c.getInt(0);
//							uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, trackid);
//						}
//						if (c.getString(1).equals(MediaFile.UNKNOWN_STRING))
//						{
//							albumid = -1;
//						}
//						c.close();
//					}
//				}
//			}
//			if (uri != null)
//			{
//				MediaScanner scanner = new MediaScanner(context);
//				ParcelFileDescriptor pfd = null;
//				try
//				{
//					pfd = context.getContentResolver().openFileDescriptor(uri, "r");
//					if (pfd != null)
//					{
//						FileDescriptor fd = pfd.getFileDescriptor();
//						art = scanner.extractAlbumArt(fd);
//					}
//				}
//				catch (IOException ex)
//				{
//				}
//				catch (SecurityException ex)
//				{
//				}
//				finally
//				{
//					try
//					{
//						if (pfd != null)
//						{
//							pfd.close();
//						}
//					}
//					catch (IOException ex)
//					{
//					}
//				}
//			}
//		}
//		// if no embedded art exists, look for AlbumArt.jpg in same directory as
//		// the media file
//		if (art == null && path != null)
//		{
//			if (path.startsWith(sExternalMediaUri))
//			{
//				// get the real path
//				Cursor c = query(context, Uri.parse(path), new String[] { MediaStore.Audio.Media.DATA }, null, null, null);
//				if (c != null)
//				{
//					c.moveToFirst();
//					if (!c.isAfterLast())
//					{
//						path = c.getString(0);
//					}
//					c.close();
//				}
//			}
//			int lastSlash = path.lastIndexOf('/');
//			if (lastSlash > 0)
//			{
//				String artPath = path.substring(0, lastSlash + 1) + "AlbumArt.jpg";
//				File file = new File(artPath);
//				if (file.exists())
//				{
//					art = new byte[(int) file.length()];
//					FileInputStream stream = null;
//					try
//					{
//						stream = new FileInputStream(file);
//						stream.read(art);
//					}
//					catch (IOException ex)
//					{
//						art = null;
//					}
//					finally
//					{
//						try
//						{
//							if (stream != null)
//							{
//								stream.close();
//							}
//						}
//						catch (IOException ex)
//						{
//						}
//					}
//				}
//				else
//				{
//					// TODO: try getting album art from the web
//				}
//			}
//		}
//
//		if (art != null)
//		{
//			try
//			{
//				// get the size of the bitmap
//				BitmapFactory.Options opts = new BitmapFactory.Options();
//				opts.inJustDecodeBounds = true;
//				opts.inSampleSize = 1;
//				BitmapFactory.decodeByteArray(art, 0, art.length, opts);
//
//				// request a reasonably sized output image
//				// TODO: don't hardcode the size
//				while (opts.outHeight > 320 || opts.outWidth > 320)
//				{
//					opts.outHeight /= 2;
//					opts.outWidth /= 2;
//					opts.inSampleSize *= 2;
//				}
//
//				// get the image for real now
//				opts.inJustDecodeBounds = false;
//				bm = BitmapFactory.decodeByteArray(art, 0, art.length, opts);
//				if (albumid != -1)
//				{
//					sArtId = albumid;
//				}
//				mCachedArt = art;
//				mCachedBit = bm;
//			}
//			catch (Exception e)
//			{
//			}
//		}
//		return bm;
//	}
//
//	private static boolean ensureFileExists(String path)
//	{
//		File file = new File(path);
//		if (file.exists())
//		{
//			return true;
//		}
//		else
//		{
//			// we will not attempt to create the first directory in the path
//			// (for example, do not create /sdcard if the SD card is not
//			// mounted)
//			int secondSlash = path.indexOf('/', 1);
//			if (secondSlash < 1)
//				return false;
//			String directoryPath = path.substring(0, secondSlash);
//			File directory = new File(directoryPath);
//			if (!directory.exists())
//				return false;
//			file.getParentFile().mkdirs();
//			try
//			{
//				return file.createNewFile();
//			}
//			catch (IOException ioe)
//			{
//				Log.e(TAG, "File creation failed", ioe);
//			}
//			return false;
//		}
//	}
//
//	private static Bitmap getDefaultArtwork(Context context)
//	{
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.albumart_mp_unknown), null, opts);
//	}
// }
