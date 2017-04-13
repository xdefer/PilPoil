package com.example.arbitre.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.Context;

public class Fichier {
	
	public static void enregistrerDonnees(String data, Context c){
		BufferedWriter writer = null;
		try {
			File dir = c.getDir("ToutMesFichiers",Context.MODE_PRIVATE);
			File newfile = new File(dir.getAbsolutePath() + File.separator + "arbitreDatas.txt");
			newfile.createNewFile();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
			writer.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
	}
	
	public static String lireFichier(String nomFichier, Context c) {
		String monText="";
		File sdLien = c.getDir("ToutMesFichiers",Context.MODE_PRIVATE);
		File monFichier = new File(sdLien + File.separator +nomFichier);
		if (!monFichier.exists()) {
			try {
				monFichier.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();

				throw new RuntimeException("Impossible de créer le fichier !");
			}
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(monFichier));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			monText = builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	return monText;
	}
}
