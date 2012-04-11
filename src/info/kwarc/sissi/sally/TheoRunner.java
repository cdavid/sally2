/**
 * 
 */
package info.kwarc.sissi.sally;

import info.kwarc.sissi.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author cdavid
 * 
 */
public class TheoRunner implements Runnable {

	private String ffPath = "";
	private String theoPath = "";
	private String type = "";
	private Process externalProcess = null;

	/**
	 * @param type
	 * @param ffPath
	 * @param theoPath
	 */
	public TheoRunner(String type, String ffPath, String theoPath) {
		Util.d("TheoRunner constructor");
		this.type = type;
		this.ffPath = ffPath;
		this.theoPath = theoPath;
	}

	public void stop() {
		Util.d("TheoRunner stop");

	}

	private void executeCmd(String args) {
		Util.d("TheoRunner executeCmd");
		try {
			this.externalProcess = Runtime.getRuntime().exec(args);
			// using this:
			// http://stackoverflow.com/questions/1732455/redirect-process-output-to-stdout
			StreamOutput err = new StreamOutput(this.externalProcess.getErrorStream());
			StreamOutput out = new StreamOutput(this.externalProcess.getInputStream());
			err.start();
			out.start();

			externalProcess.waitFor();
		} catch (IOException e) {
			Util.e(e);
		} catch (InterruptedException e) {
			Util.e(e);
		}
	}

	@Override
	public void run() {
		Util.d("TheoRunner run");
		if (this.type.equalsIgnoreCase("desktop")) {
			String params = this.ffPath + " -app " + this.theoPath; //"-jsconsole"
			executeCmd(params);
		}
	}

}

class StreamOutput extends Thread {

	InputStream is;
	public StreamOutput(InputStream is) {
		this.is = is;
	}
	
	@Override
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String read = null;
		try {
			while ((read = br.readLine()) != null) {
				Util.d(read);
			}
		} catch (IOException e) {
			Util.e(e);
		}
	}	
}
