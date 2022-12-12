/**
 * 
 */
package com.autolib.helpdesk.common;

import java.io.File;
import java.io.IOException;

/**
 * @author Kannadasan
 *
 */
public class Testt {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File folder = new File("D:\\Kannadasan\\HelpDesk\\Contents\\Deals\\3");

		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}

}
