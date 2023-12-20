package util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil
{
	private static final String TAG = "FileUtil";

	private FileUtil()
	{
	}

	static public void append(byte[] aInput, String aOutputFileName)
	{
		Log.d(TAG, "Writing binary file...aOutputFileName=" + aOutputFileName);
		try
		{
			OutputStream output = null;
			try
			{
				output = new BufferedOutputStream(new FileOutputStream(aOutputFileName,true));
				output.write(aInput);
			} finally
			{
				output.close();
			}
		} catch (FileNotFoundException ex)
		{
			Log.e(TAG, "File not found.");
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}


	static public byte[] read(String aInputFileName)
	{
		Log.d(TAG, "Reading in binary file named : " + aInputFileName);
		File file = new File(aInputFileName);

		if (file.exists() == false)
		{
			Log.w(TAG, "WARNING: source file doesn't exists, return null...");
			return null;
		}
		if (file.length() == 0)
		{
			Log.w(TAG, "WARNING: source file is empty, return null...");
			return null;
		}

		Log.d(TAG, "File size: " + file.length());
		byte[] result = new byte[(int) file.length()];
		try
		{
			InputStream input = null;
			try
			{
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while (totalBytesRead < result.length)
				{
					int bytesRemaining = result.length - totalBytesRead;
					//input.read() returns -1, 0, or more :
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
					if (bytesRead > 0)
					{
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				// the above style is a bit tricky: it places bytes into the 'result' array; 'result' is an output parameter; the while loop usually has a single iteration only.
				Log.d(TAG, "Num bytes read: " + totalBytesRead);
			} finally
			{
				Log.d(TAG, "Closing input stream.");
				input.close();
			}
		} catch (FileNotFoundException ex)
		{
			Log.w(TAG, "File not found.");
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return result;
	}

	public static void writeToFile(String fileName, String content)
	{
		try {
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file);
			writer.append(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
		}
	}
}
