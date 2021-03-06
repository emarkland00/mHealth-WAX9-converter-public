/******************************************************************************************
 * 
 * Copyright (c) 2016 EveryFit, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * Authors:
 *  - Markland, Errol
 * 
 ******************************************************************************************/
package com.qmedic.data.converter.wax9;

import java.io.File;
import java.io.IOException;

public class Main {
	
	// ALL OPTIONAL ARGS
	private static Boolean splitFiles = null;
	
	public static void main(String[] args) {
		// Command line example: java -jar WAX9Parser.jar sample-data/exampleData.bin ../output SPLIT
		if (args.length < 2){
			System.out.println("java -jar WAX9Parser.jar [INPUT WAX9 FILE] [OUTPUT CSV DIRECTORYPATH] [SPLIT/NO_SPLIT]");
			return;
		}
		
		String inputFilename = args[0];
		if (!validateInputFile(inputFilename)) return;
		
		String outputDirectory = args[1];
		if (!validateOutputDirectory(outputDirectory)) return;
		
		if (!processOptionalArgs(args)) return;
		
		try {
			WAX9File file = new WAX9File(inputFilename, outputDirectory);
			file.enableSplitFile(splitFiles);
			
			file.processFile();
		} catch (IOException e) {
			System.out.println("Problem occured while reading WAX9 File " + inputFilename);
			return;
		}
	}
	
	private static boolean validateInputFile(String inputFilename) {
		File inFile = new File(inputFilename);
		if(!inFile.exists()) {
			System.out.format("Error: Input file %s doesn't exist", inputFilename);
			return false;
		}
		
		return true;
	}
	
	private static boolean validateOutputDirectory(String outputDirectory) {
		File outDirectory = new File(outputDirectory);
		if(!outDirectory.exists()) {
			System.out.format("Error: Output directory %s doesn't exist", outputDirectory);
			return false;
		}
		
		return true;
	}
	
	private static boolean processOptionalArgs(String[] args) {
		try {
			for (int i = 2; i < args.length; i++) {
				switch (args[i].toLowerCase()) {
					case "split":
						if (splitFiles != null && splitFiles == true) throw new IllegalArgumentException("Already specified split option.");
						splitFiles = true;
						break;
					case "no_split":
						if (splitFiles != null && splitFiles == false) throw new IllegalArgumentException("Already specified split option.");
						splitFiles = false;
						break;
				}
			}
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		
		// provide default values, if needed
		if (splitFiles == null) {
			splitFiles = false;
		}
		
		return true;
	}
}
