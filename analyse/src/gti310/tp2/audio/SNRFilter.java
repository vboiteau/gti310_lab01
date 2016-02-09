package gti310.tp2.audio;

import java.io.FileNotFoundException;

import gti310.tp2.io.FileSource;

public class SNRFilter implements AudioFilter {
	private audioFile original_file = new audioFile();
	private audioFile[] compare_files;
	
	public SNRFilter(String original, String[] to_analyse) throws FileNotFoundException{
		original_file.source_path = original;
		original_file.source_reader = new FileSource(original_file.source_path);
		read_header(original_file);
		compare_files = new audioFile[to_analyse.length];
		for(int i=0;i<to_analyse.length;i++){
			compare_files[i] = new audioFile();
			compare_files[i].source_path=to_analyse[i];
			compare_files[i].source_reader = new FileSource(to_analyse[i]);
			read_header(compare_files[i]);
		}
		if(get_signal_noise()){
			System.out.println("after noise and signal calculation");
		}
	}
	
	private boolean get_signal_noise(){
		
		return true;
	}
	
	private void read_header(audioFile aF){
		aF.source_reader.pop(4);
		System.out.println("Le fichier "+aF.source_path+" est:");
		aF.taille_fichier = byteArrayToInt(aF.source_reader.pop(4));
		System.out.println("taille du fichier: "+(aF.taille_fichier+8)+" octets.");
		aF.source_reader.pop(8);
		aF.taille_bloc = byteArrayToInt(aF.source_reader.pop(4));
		System.out.println("taille des blocs:"+ aF.taille_bloc);
		aF.source_reader.pop(2);
		aF.nbr_canaux = byteArrayToShort(aF.source_reader.pop(2));
		System.out.println("nombre de canaux: "+aF.nbr_canaux);
		aF.frequence_echantillonage = byteArrayToInt(aF.source_reader.pop(4));
		System.out.println("frequence d'echantillonage: "+aF.frequence_echantillonage);
		aF.octets_par_seconde = byteArrayToInt(aF.source_reader.pop(4));
		System.out.println("octets par seconde: "+aF.octets_par_seconde);
		aF.octets_par_bloc = byteArrayToShort(aF.source_reader.pop(2));
		System.out.println("octets par bloc: "+aF.octets_par_bloc);
		aF.bits_par_echantillon = byteArrayToShort(aF.source_reader.pop(2));
		System.out.println("bits par echantillon: "+aF.bits_par_echantillon);
		aF.source_reader.pop(8);
	}
	
	// code inspiré par le post stackoverflow
	// http://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java
	private int byteArrayToInt(byte[] entree){
		int sortie=0;
		for(int i=0;i<4;i++){
			int bond = (4-1-i);
			sortie += (entree[i] & 0x000000FF) << bond;
		}
		return sortie;
	}
	// fin du code inspiré
	
	private short byteArrayToShort(byte[] entree){
		short sortie=0;
		for(int i=0;i<2;i++){
			int bond = (2-1-i);
			sortie += (entree[i] & 0x000000FF) << bond;
		}
		return sortie;
	}
	
	@Override
	public void process() {
		// TODO Auto-generated method stub

	}
	
	class audioFile{
		public String source_path;
		public FileSource source_reader;
		public int Noise, taille_fichier, taille_bloc, frequence_echantillonage, octets_par_seconde;
		public short nbr_canaux, octets_par_bloc, bits_par_echantillon;
	}
}
