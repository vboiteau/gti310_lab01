package gti310.tp2.audio;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
		long start_time = System.nanoTime();
		if(read_bloc_by_blocs()){
			sort_by_snr();
			print_snr();
		}
		long end_time = System.nanoTime();
		System.out.println("reading data chunk + sorting snr + printing chart timer = "+((double)((end_time-start_time)/1000000)));
	}
	
	private boolean read_bloc_by_blocs (){
		byte[] current_signal_sample = new byte[1];
		for(int cpt=0; cpt<original_file.cksize; cpt++){
			current_signal_sample = original_file.source_reader.pop(1);				
			for(int i=0;i<compare_files.length;i++){
				audioFile aF = compare_files[i];
				byte[] current_noise_sample=aF.source_reader.pop(1);
				if(current_noise_sample != null){
					byte current_noise_value=(byte)(current_signal_sample[0] - current_noise_sample[0]);
					aF.original_signal += Math.pow(current_signal_sample[0], 2);
					aF.noise += Math.pow(current_noise_value,2);				
				}
			}
		}
		for(int i=0;i<compare_files.length;i++){
			audioFile aF = compare_files[i];
			aF.SNR = 10*(Math.log10(aF.original_signal/aF.noise)); 
		}
		return true;
	}
	
	private void read_header(audioFile aF){
		aF.source_reader.pop(4);
		// System.out.println("Le fichier "+aF.source_path+" est:");
		aF.taille_fichier = byteArrayToInt(aF.source_reader.pop(4));
		// System.out.println("taille du fichier: "+(aF.taille_fichier+8+36)+" octets.");
		aF.source_reader.pop(8);
		aF.taille_bloc = byteArrayToInt(aF.source_reader.pop(4));
		// System.out.println("taille des blocs:"+ aF.taille_bloc);
		aF.source_reader.pop(2);
		aF.nbr_canaux = byteArrayToShort(aF.source_reader.pop(2));
		// System.out.println("nombre de canaux: "+aF.nbr_canaux);
		aF.frequence_echantillonage = byteArrayToInt(aF.source_reader.pop(4));
		// System.out.println("frequence d'echantillonage: "+aF.frequence_echantillonage);
		aF.octets_par_seconde = byteArrayToInt(aF.source_reader.pop(4));
		// System.out.println("octets par seconde: "+aF.octets_par_seconde);
		aF.octets_par_bloc = byteArrayToShort(aF.source_reader.pop(2));
		// System.out.println("octets par bloc: "+aF.octets_par_bloc);
		aF.bits_par_echantillon = byteArrayToShort(aF.source_reader.pop(2));
		// System.out.println("bits par echantillon: "+aF.bits_par_echantillon);
		aF.ckid = byteArrayToInt(original_file.source_reader.pop(4));
		// System.out.println("chunk id: "+aF.ckid);
		aF.cksize = byteArrayToInt(original_file.source_reader.pop(4));
		// System.out.println("chunk size: "+aF.cksize);
	}
	
	private void sort_by_snr(){
		for(int i=1;i<compare_files.length;i++){
			audioFile snr_to_compare = compare_files[i];
			int j=i-1;
			while(j >= 0 && compare_files[j].SNR > snr_to_compare.SNR){
				compare_files[j+1] = compare_files[j];
				j--;
			}
			compare_files[j+1] = snr_to_compare;
		}
	}
	
	private void print_snr(){
		System.out.println("Ici sont les résultat du RSB du meillleur au pire:");
		for(int i=0; i<compare_files.length;i++){
			System.out.println((i+1)+" - "+compare_files[i].source_path+" / "+compare_files[i].SNR);
		}
	}
	
	// code inspiré par le post stackoverflow
	// http://stackoverflow.com/questions/1026761/how-to-convert-a-byte-array-to-its-numeric-value-java
	private short byteArrayToShort(byte[] entree){
		ByteBuffer array_entree = ByteBuffer.wrap(entree);
		array_entree.order( ByteOrder.LITTLE_ENDIAN);
		return array_entree.getShort();
	}
	
	private int byteArrayToInt(byte[] entree){
		ByteBuffer array_entree = ByteBuffer.wrap(entree);
		array_entree.order( ByteOrder.LITTLE_ENDIAN);
		return array_entree.getInt();
	}
	
	// fin du code inspiré
	
	@Override
	public void process() {
		// TODO Auto-generated method stub

	}
	
	class audioFile{
		public String source_path;
		public FileSource source_reader;
		public double SNR;
		public int ckid, cksize, taille_fichier, taille_bloc, frequence_echantillonage, octets_par_seconde;
		public short nbr_canaux, octets_par_bloc, bits_par_echantillon;
		public long noise, original_signal;
	}
}
