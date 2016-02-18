/**
 * 
 */
package gti310.tp2.audio;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import gti310.tp2.io.FileSink;
import gti310.tp2.io.FileSource;

/**
 * @author vboiteau
 *
 */
public class Convert16to8Filter implements AudioFilter {
	FileSource file_reader;
	FileSink file_writer;
	short s_numChannels;
	int i_sampleRate;
	short s_bitsPerSample=8;
	int i_byteRate;
	short s_blockAlign;
	int i_numSamples;
	public Convert16to8Filter(String original_file_path, String converted_file_path) throws FileNotFoundException{
		file_reader=new FileSource(original_file_path);
		file_writer=new FileSink(converted_file_path);
	}
	
	/* (non-Javadoc)
	 * @see gti310.tp2.audio.AudioFilter#process()
	 */
	@Override
	public void process() throws UnsupportedEncodingException {
		process_header();
		for(int i=0;i<i_numSamples;i++){
			for(short j=0;j<s_numChannels;j++){
				processSample(i+j);
			}
		}
		file_reader.close();
		file_writer.close();
	}
	
	private void process_header() throws UnsupportedEncodingException{
		byte[] b_converted_header = new byte[44];
		byte[] b_descriptor = file_reader.pop(4);
		String s_descriptor = new String(b_descriptor, "US-ASCII");
		if(!s_descriptor.equals("RIFF")){
			System.out.println("bad file descriptor");
			System.exit(0);
		}
		System.arraycopy(b_descriptor,0,b_converted_header,0,4);
		file_reader.pop(4);
		byte[] b_format = file_reader.pop(4);
		String s_format = new String(b_format, "US-ASCII");
		if(!s_format.equals("WAVE")){
			System.out.println("bad format");
			System.exit(0);
		}
		System.arraycopy(b_format,0,b_converted_header,8,4);
		System.arraycopy(file_reader.pop(4),0,b_converted_header,12,4);
		byte[] b_chunk1Size = file_reader.pop(4);
		int i_chunk1Size = ByteBuffer.wrap(b_chunk1Size).order(ByteOrder.LITTLE_ENDIAN).getInt();
		System.arraycopy(b_chunk1Size,0,b_converted_header,16,4);
		System.arraycopy(file_reader.pop(2),0,b_converted_header,20,2);
		byte[] b_numChannels = file_reader.pop(2);
		System.arraycopy(b_numChannels,0,b_converted_header,22,2);
		s_numChannels = ByteBuffer.wrap(b_numChannels).order(ByteOrder.LITTLE_ENDIAN).getShort();
		byte[] b_sampleRate = file_reader.pop(4);
		System.arraycopy(b_sampleRate,0,b_converted_header,24,4);
		i_sampleRate= ByteBuffer.wrap(b_sampleRate).order(ByteOrder.LITTLE_ENDIAN).getInt();
		file_reader.pop(6);
		byte[] b_original_bitsPerSample = file_reader.pop(2);
		short s_original_bitsPerSample = ByteBuffer.wrap(b_original_bitsPerSample).order(ByteOrder.LITTLE_ENDIAN).getShort();
		if(s_original_bitsPerSample!=16){
			System.out.println("bits sample rate not valid!");
			System.exit(0);
		}
		i_byteRate = i_sampleRate * s_numChannels * (s_bitsPerSample/8);
		s_blockAlign = (short)(s_numChannels * (s_bitsPerSample/8));
		System.arraycopy(file_reader.pop(4), 0, b_converted_header, 36, 4);
		i_numSamples = (ByteBuffer.wrap(file_reader.pop(4)).order(ByteOrder.LITTLE_ENDIAN).getInt())/(s_numChannels*(s_original_bitsPerSample/8));
		int i_chunk2Size = i_numSamples * s_numChannels * (s_bitsPerSample/8);
		int i_chunkSize = 4+ (8 + i_chunk1Size) + (8 + i_chunk2Size);
		byte[] b_chunkSize = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i_chunkSize).array();
		System.arraycopy(b_chunkSize, 0, b_converted_header, 4, 4);
		byte[] b_chunk2Size = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i_chunk2Size).array();
		System.arraycopy(b_chunk2Size, 0, b_converted_header, 40, 4);
		byte[] b_byteRate = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i_byteRate).array();
		System.arraycopy(b_byteRate, 0, b_converted_header, 28, 4);
		byte[] b_blockAlign = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s_blockAlign).array();
		System.arraycopy(b_blockAlign, 0, b_converted_header, 32, 2);
		byte[] b_bitsPerSample = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s_bitsPerSample).array();
		System.arraycopy(b_bitsPerSample, 0, b_converted_header, 34, 2);
		file_writer.push(b_converted_header);
	}
	
	private void processSample(int pos){
		pos+=44;
		byte[] ba_sample=file_reader.pop(2);
		short s_sample=ByteBuffer.wrap(ba_sample).order(ByteOrder.LITTLE_ENDIAN).getShort();
		byte b_sample = (byte)(Math.floor(s_sample/(Math.pow(2.0, 8.0)))+128);
		byte[] ba_wsample=new byte[1];
		ba_wsample[0]=b_sample;
		file_writer.push(ba_wsample);
	}
}
